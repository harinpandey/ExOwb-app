# EXOWN — MOBILE & INTEGRATION ARCHITECTURE

> **System Blueprint for the ExOwn Native Android Client**  
> **Platform:** Kotlin • Jetpack Compose • Material 3 • Android SDK (minSdk 26, targetSdk 35)

---

## 1. System Topology & Tier Separation

ExOwn enforces a strict separation between client presentation, API coordination, and persistent data tiers. The Android app is a **client of the platform**, never a replacement for backend business logic.

```text
┌────────────────────────────────────────────────────────┐
│               EXOWN ANDROID NATIVE APP                 │
│  Compose UI • ViewModel • Room Cache • DataStore       │
└───────────────────────────┬────────────────────────────┘
                            │ HTTPS / REST (Bearer JWT)
                            ▼
┌────────────────────────────────────────────────────────┐
│               EXOWN BACKEND (Next.js)                  │
│  API Routes • Auth Middleware • Business Rules         │
└───┬───────────────────┬───────────────────┬────────────┘
    │                   │                   │
    ▼                   ▼                   ▼
┌──────────────┐ ┌──────────────┐ ┌──────────────────────┐
│Firebase Auth │ │  Prisma ORM  │ │   Third-Party APIs   │
│ Identity &   │ │      &       │ │ Cloudinary (Media)   │
│ FCM Tokens   │ │  Neon (Post- │ │ Gemini (Server AI)   │
│              │ │   greSQL)    │ │ Razorpay (Payments)  │
└──────────────┘ └──────────────┘ └──────────────────────┘
```

---

## 2. Authentication & Identity Mapping

### Invariant: CUID ↔ Firebase UID Binding
- The authoritative database (`User` table in PostgreSQL) uses a CUID as its primary key (`User.id`).
- When a student signs in via Jetpack `CredentialManager` and Firebase Auth, the Android app acquires a signed Firebase ID Token.
- The token is transmitted in the `Authorization: Bearer <ID_TOKEN>` header to `POST /api/auth/sync`.
- The backend verifies the token using the **Firebase Admin SDK**, reads the cryptographically verified `uid` and student email, and resolves or upserts the corresponding ExOwn `User` record.
- **Zero-Trust Rule:** The Android client NEVER possesses Firebase Admin credentials, Neon connection strings, or third-party secret keys.

```text
[CredentialManager] ──> [Google ID Token] ──> [Firebase Auth] ──> [Firebase JWT]
                                                                        │
                                                                        ▼ (Bearer Token)
[ExOwn Backend: verifyIdToken()] ──> [Find/Upsert User in PostgreSQL by firebaseUid]
                                                                        │
                                                                        ▼
                                                   [Authoritative ExOwn User Profile]
```

---

## 3. Database Safety & Schema Preservation

To protect production campus data, the following engineering rules are absolute:
1. **No Direct Mobile Connections:** Mobile clients never connect to Neon directly.
2. **No Destructive Database Changes:** No casual `db push --force-reset`, no table wipes, and no schema replacement.
3. **Prisma as Data Gateway:** All relational mutations (listings, transactions, deals, roommate requests) execute through server-side Prisma transactions with row-level ownership checks.
4. **Idempotent Sync:** Mobile sync routines must be idempotent, reconciling offline cached data without creating duplicate records.

---

## 4. Local Caching & Offline Resilience

ExOwn utilizes a two-tier local storage strategy:
1. **Jetpack DataStore (Preferences):**
   - Active `campusId` and campus name (e.g., `LPU · Phagwara`).
   - Session tokens and authentication status.
   - User notification preferences and theme toggles.
2. **Room SQLite Database:**
   - `UserProfileEntity`: Cached Trust Passport, hostel block, room number, and impact metrics.
   - `ListingEntity`: Cached listings for instantaneous home feed rendering (< 15ms cold start).
   - `DealRoomEntity`: Active deal states, offers, and agreed campus pickup coordinates.
   - `DraftListingEntity`: Offline draft listings with local image URIs prior to Cloudinary upload.

---

## 5. Media Pipeline (Camera & Cloudinary)

To ensure rapid uploads on campus Wi-Fi / 4G:
1. **Capture / Selection:** Android Photo Picker (`ActivityResultContracts.PickMultipleVisualMedia`) or Camera.
2. **On-Device Compression:** Client resizes photos to max $1600\times 1200$ and compresses to WebP/JPEG ($\sim 300\,\text{KB}$).
3. **Signed Upload:** Client requests a signed upload preset from `POST /api/media/sign-upload`.
4. **Cloudinary Storage:** Photo is uploaded directly to Cloudinary using the secure signature.
5. **Listing Creation:** The resulting HTTPS Cloudinary URLs are submitted in the final `POST /api/listings` payload.

---

## 6. AI Grounding & Architecture

AI is scoped to enhance student productivity and safety:
- **Listing Assistant:** Uses server-side Gemini 3.8 to inspect photos and propose titles, categories, and tags.
- **Campus Natural Search:** Parses conversational queries (e.g., *"scientific calculator under ₹600 near BH-1"*) into structured Prisma query filters (`category`, `maxPrice`, `location`).
- **Scam & Content Guard:** Scans listing text and messages for external off-platform fraud links or prohibited items before publication.
- **Safety Boundary:** AI never executes account suspensions, payments, or destructive mutations without human/server authorization.

---

## 7. Mobile Navigation Architecture

The app is structured into 5 persistent top-level tabs managed by a sealed navigation state:

| Screen Token | Bottom Nav Label | Core Responsibility |
|---|---|---|
| `AppScreen.HOME` | **Home** | Personalized greeting, campus search, campus pill, quick actions (`Buy`, `Sell`, `Rent`, `Exchange`), and contextual feeds. |
| `AppScreen.EXPLORE` | **Search** | Deep search, category filtering, price range, condition, and natural-language query resolution. |
| `AppScreen.SELL` | **Sell** | Camera-first selling flow, AI catalog assistance, pricing, and hostel pickup point selection. |
| `AppScreen.INBOX` | **Inbox** | Student messaging threads, offer negotiations, and structured **Deal Rooms** with pickup verification. |
| `AppScreen.PROFILE` | **Profile** | **ExOwn Trust Passport**, active listings, saved items, sustainability impact metrics, and settings. |

Sub-screens (`ProductDetail`, `HousingDetail`, `CreateListing`) maintain native `BackHandler` support to seamlessly pop back to the top-level feed.
