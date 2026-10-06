# EXOWN — AUTHENTICATION & PROFILE ARCHITECTURE

> **Document Path:** `docs/ARCHITECTURE_AUTH.md`  
> **Systems:** Android Client (Jetpack Compose) ↔ Firebase Authentication ↔ ExOwn Backend (Next.js / Prisma / PostgreSQL)

---

## 1. System Overview & Separation of Concerns

ExOwn adopts a decoupled identity and data architecture to preserve security, scalability, and clean data ownership:

```text
┌────────────────────────┐         ┌────────────────────────┐         ┌────────────────────────┐
│     Android Client     │         │     Firebase Auth      │         │     ExOwn Backend      │
│  (Credential Manager)  │         │ (Identity & ID Tokens) │         │ (Profile & Relational) │
└───────────┬────────────┘         └───────────┬────────────┘         └───────────┬────────────┘
            │                                  │                                  │
            │ 1. Google Credential             │                                  │
            │─────────────────────────────────>│                                  │
            │                                  │                                  │
            │ 2. Firebase ID Token (JWT)       │                                  │
            │<─────────────────────────────────│                                  │
            │                                                                     │
            │ 3. HTTPS: POST /api/auth/sync (Bearer <Firebase_ID_Token>)          │
            │────────────────────────────────────────────────────────────────────>│
            │                                                                     │
            │                                  │ 4. Verify Token (Firebase Admin) │
            │                                  │<─────────────────────────────────│
            │                                  │                                  │
            │                                  │ 5. Claims (uid, email, verified) │
            │                                  │─────────────────────────────────>│
            │                                                                     │
            │                                  │ 6. Upsert User in PostgreSQL     │
            │                                  │    via Prisma ORM                │
            │                                                                     │
            │ 7. Return Authoritative ExOwn User Profile JSON                     │
            │<────────────────────────────────────────────────────────────────────│
            │                                                                     │
            │ 8. Cache in DataStore / Room                                        │
            │    Emit to StateFlow in ViewModel                                   │
            ▼                                                                     ▼
```

### Responsibility Matrix:
- **Firebase Authentication:** Handles authentication protocols, Google Identity federation, brute force protection, token minting, and automatic token refresh.
- **ExOwn Backend (Next.js API Routes):** Acts as the gatekeeper. Verifies Firebase ID Tokens using the Firebase Admin SDK, provisions/updates user records in PostgreSQL via Prisma, validates student university domains, and manages domain relationships (listings, orders, reviews, chat).
- **Android Client:** Coordinates Google Sign-In via Jetpack `CredentialManager`, exchanges credentials with Firebase Auth, attaches JWTs to outbound API requests via OkHttp/Retrofit interceptors, and securely caches user profiles locally for instant offline-first rendering.

---

## 2. End-to-End Authentication & Verification Flow

### Step 1: Credential Manager Google Sign-In (Client)
The user initiates login. The app calls Jetpack `CredentialManager`:
```kotlin
val googleIdOption = GetGoogleIdOption.Builder()
    .setFilterByAuthorizedAccounts(false)
    .setServerClientId(context.getString(R.string.default_web_client_id))
    .setAutoSelectEnabled(true)
    .build()

val request = GetCredentialRequest.Builder()
    .addCredentialOption(googleIdOption)
    .build()

val result = credentialManager.getCredential(context = context, request = request)
val googleIdToken = (result.credential as CustomCredential).data
    .getString("com.google.android.libraries.identity.googleid.BUNDLE_KEY_ID_TOKEN")
```

### Step 2: Firebase Credential Exchange (Client)
The Google ID token is exchanged for a Firebase Auth session:
```kotlin
val credential = GoogleAuthProvider.getCredential(googleIdToken, null)
val authResult = Firebase.auth.signInWithCredential(credential).await()
val firebaseUser = authResult.user ?: throw AuthException("Firebase User is null")
```

### Step 3: Firebase ID Token Retrieval (Client)
The client retrieves the short-lived Firebase ID Token:
```kotlin
val idTokenResult = firebaseUser.getIdToken(false).await()
val rawIdToken = idTokenResult.token ?: throw AuthException("Missing ID Token")
```

### Step 4: Backend Verification & Profile Sync (`POST /api/auth/sync`)
The client sends the token in the HTTP `Authorization` header:
```http
POST /api/auth/sync HTTP/1.1
Host: api.exown.in
Authorization: Bearer eyJhbGciOiJSUzI1NiIsImtpZCI...
Content-Type: application/json

{
  "clientVersion": "1.4.0",
  "deviceInfo": "Android 14 (Pixel 7)"
}
```

### Step 5: Backend Token Verification (Firebase Admin SDK)
The backend intercepts the request, extracts the Bearer token, and executes cryptographic verification:
```typescript
import admin from '@/lib/firebase-admin';
import { prisma } from '@/lib/prisma';

export async function POST(req: Request) {
  const authHeader = req.headers.get('authorization');
  if (!authHeader?.startsWith('Bearer ')) {
    return Response.json({ error: 'Unauthorized: Missing Bearer Token' }, { status: 401 });
  }

  const idToken = authHeader.split('Bearer ')[1];
  
  // Verify token signature, expiration, and issuer
  const decodedToken = await admin.auth().verifyIdToken(idToken);
  const { uid, email, name, picture } = decodedToken;

  if (!email) {
    return Response.json({ error: 'Bad Request: Email required' }, { status: 400 });
  }

  // Check institutional email status (.ac.in / .edu / student domains)
  const isStudentDomain = email.endsWith('.edu') || email.endsWith('.ac.in');

  // Upsert user in PostgreSQL
  const user = await prisma.user.upsert({
    where: { firebaseUid: uid },
    update: {
      email,
      name: name ?? undefined,
      avatarUrl: picture ?? undefined,
      lastActiveAt: new Date(),
    },
    create: {
      firebaseUid: uid,
      email,
      name: name ?? 'Classmate',
      avatarUrl: picture ?? '',
      isVerifiedStudent: isStudentDomain,
      campus: 'Main University Campus',
    },
    include: {
      trustProfile: true,
    }
  });

  return Response.json({ success: true, profile: user });
}
```

---

## 3. Session Management & Token Lifecycle

### 3.1 Token Expiration & Automatic Refresh
1. Firebase ID Tokens expire after **1 hour** (3600 seconds).
2. The Firebase Android SDK internally caches and automatically refreshes tokens in the background prior to expiry.
3. Outbound API requests use an OkHttp `Interceptor` to retrieve the current valid token:

```kotlin
class AuthInterceptor @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val currentUser = firebaseAuth.currentUser
        val requestBuilder = chain.request().newBuilder()

        if (currentUser != null) {
            // Tasks.await fetches cached token or refreshes synchronously if expired
            val token = Tasks.await(currentUser.getIdToken(false)).token
            if (!token.isNullOrBlank()) {
                requestBuilder.addHeader("Authorization", "Bearer $token")
            }
        }

        val response = chain.proceed(requestBuilder.build())

        // Handle 401 Unauthorized by forcing a token refresh once
        if (response.code == 401 && currentUser != null) {
            response.close()
            val freshToken = Tasks.await(currentUser.getIdToken(true)).token
            val retryRequest = chain.request().newBuilder()
                .header("Authorization", "Bearer $freshToken")
                .build()
            return chain.proceed(retryRequest)
        }

        return response
    }
}
```

### 3.2 Sign Out & Invalidation
When the user taps "Log Out":
1. Client calls `Firebase.auth.signOut()`.
2. Client wipes locally cached user session from DataStore and Room.
3. Client fires `POST /api/auth/logout` to inform backend of session termination.
4. ViewModel resets state to unauthenticated `AuthState.LoggedOut`.

---

## 4. Local Storage of User Profile on Android

ExOwn uses a two-tier local caching strategy for user profile persistence:

```text
                        ┌───────────────────────────────┐
                        │        ExOwn User State       │
                        └───────────────┬───────────────┘
                                        │
                 ┌──────────────────────┴──────────────────────┐
                 ▼                                             ▼
     ┌───────────────────────┐                     ┌───────────────────────┐
     │   Jetpack DataStore   │                     │      Room SQLite      │
     │  (Preferences/Proto)  │                     │   (Relational Cache)  │
     ├───────────────────────┤                     ├───────────────────────┤
     │ - Auth Token Flag     │                     │ - UserProfileEntity   │
     │ - Current Campus ID   │                     │ - Saved Listings      │
     │ - Notification Prefs  │                     │ - Chat Message Stream │
     │ - Active Hostel Room  │                     │ - Draft Listings      │
     └───────────────────────┘                     └───────────────────────┘
```

### 4.1 Room UserProfile Entity
```kotlin
@Entity(tableName = "user_profile")
data class UserProfileEntity(
    @PrimaryKey val id: String,
    val firebaseUid: String,
    val email: String,
    val name: String,
    val avatarUrl: String,
    val campus: String,
    val hostelBlock: String,
    val roomNumber: String,
    val isVerifiedStudent: Boolean,
    val itemsRehomed: Int,
    val moneySaved: Double,
    val co2OffsetKg: Double,
    val updatedAt: Long = System.currentTimeMillis()
)
```

### 4.2 Local Storage Principles
1. **Instant App Launch:** Upon opening the app, the cached `UserProfileEntity` is loaded from Room in < 15ms and emitted into `StateFlow<UserProfile?>`, providing immediate UI presentation with zero flicker or blank loading screens.
2. **Background Sync:** The app silently calls `GET /api/user/me` in the background to sync profile changes, updated badges, and balance metrics.
3. **Sensitive Data Protection:** No server credentials, Firebase Admin keys, or database connection strings are ever stored on the device or in local storage.

---

## 5. Security & Zero-Trust Checklist

- [x] **No Direct Database Access:** Android client never connects to PostgreSQL or Neon directly. All requests go through backend REST APIs.
- [x] **Strict Signature Validation:** Backend verifies tokens using Firebase Admin SDK public keys rather than trusting client-sent user IDs.
- [x] **HTTPS Only:** Plaintext HTTP is blocked across all network security configurations.
- [x] **Role & Campus Authorization:** The backend verifies that the requester's `uid` matches the resource owner on all mutation endpoints (edit listing, delete listing, post offer).
