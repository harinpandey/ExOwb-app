# EXOWN — MASTER BLUEPRINT & PRODUCT DOCUMENTATION

**Tagline:** Exchange. Own. Repeat.  
**Product Type:** Campus-Focused Student Marketplace & Services Ecosystem  
**Target Audience:** University students, hostel residents, campus communities, student freelancers, and campus administrators.

---

## 1. Executive Summary & Vision

ExOwn is a trusted digital marketplace and services ecosystem designed specifically around student life. Instead of fragmenting campus commerce across informal WhatsApp groups, Telegram channels, and generic classifieds, ExOwn connects students within their university communities to:

1. **Buy and Sell** used textbooks, electronics, cycles, and hostel furniture.
2. **Rent** gear (cameras, laptops, bicycles) on daily or monthly terms.
3. **Exchange & Barter** items directly for other items (zero cash required).
4. **Discover Campus Housing & PGs** without brokerage fees.
5. **Find Compatible Roommates** based on budget, move-in dates, and lifestyle habits.
6. **Access Student Services** (laptop OS/repair, graphic design, bike maintenance).
7. **Negotiate & Transact** via in-app chat with structured offers and counter-offers.
8. **Build Student Reputation** with verified student ID checkmarks and campus deals.

---

## 2. Core User Journey

> **Discover → View → Trust → Connect → Offer → Transact → Complete → Review**

---

## 3. Product Architecture

```text
                         EXOWN ECOSYSTEM
                               │
            ┌──────────────────┴──────────────────┐
            │                                     │
      Android Native                         Web Platform
     Jetpack Compose / Kotlin                  Next.js
            │                                     │
            └──────────────────┬──────────────────┘
                               │
                          HTTPS / API
                               │
                        ExOwn Backend
                     Next.js API Services
                               │
             ┌─────────────────┼─────────────────┐
             │                 │                 │
          Firebase          Prisma           Cloudinary
       Authentication         ORM              Media
             │                 │
            FCM            PostgreSQL
       Notifications          Neon
```

### Core Architecture Invariants:
1. **Unified Source of Truth:** Mobile and Web share the same backend, database records, and authentication mappings.
2. **Least Privilege & Security:** Mobile clients never connect directly to the database or embed server secrets (e.g., Firebase Admin private keys, Neon connection strings). All protected actions route through authenticated HTTPS APIs.
3. **Campus Identity & Trust:** Every user session is anchored to a student identity, university campus, and hostel/PG location.

---

## 4. Feature Matrix (Current Status vs. Roadmap)

| Feature | Status | Description |
|---|---|---|
| **Campus Marketplace Feed** | ✅ Implemented | Browse active items by campus, category, urgency, and distance. |
| **Search & Advanced Filters** | ✅ Implemented | Keyword search, price sorting, condition filter, and category pills. |
| **Product Detail Screen** | ✅ Implemented | Photo view, savings percentage, seller rating, and safety tips. |
| **Sell & Create Listing Flow** | ✅ Implemented | Fast form with title, category, price, condition, urgency, and photo. |
| **Barter & Exchange Club** | ✅ Implemented | Tag items as exchange-eligible with specific trade preferences. |
| **Campus Rentals** | ✅ Implemented | Daily and monthly rental rates for cycles, laptops, and study gear. |
| **Campus Housing & PGs** | ✅ Implemented | Student rooms, PGs, and flats with amenities and direct contact. |
| **Roommate Matcher** | ✅ Implemented | Profiles with budget, move-in date, major, and lifestyle tags. |
| **Student Peer Services** | ✅ Implemented | Peer-to-peer campus services (repairs, design, tutoring). |
| **In-App Chat & Offers** | ✅ Implemented | Chat stream with pinned listing, quick chips, and counter-offers. |
| **Circular Economy Impact** | ✅ Implemented | Profile metrics: Items Rehomed, Money Saved (₹), CO₂ Offset (kg). |
| **Firebase Auth & Credential Manager** | ✅ Implemented | Google Sign-In with Credential Manager and student onboarding. |
| **Custom Adaptive App Icon** | ✅ Implemented | Interlocking exchange arrows in ExOwn blue (`#2563EB`) & emerald. |
| **FCM Push Notifications** | 🟡 In Development | Device alerts for new messages, offers, and price drops. |
| **Payment Gateway Integration** | 🔵 Future Scope | Campus escrow, advance booking, and secure payouts. |
| **AI Listing & Search Assistant** | 🔵 Future Scope | Natural language query parsing and auto-generated listing details. |

---

## 5. Technology Stack

- **Android Client:** Kotlin, Jetpack Compose, Material 3, AndroidX Lifecycle, Coil, Room, Credential Manager.
- **Authentication:** Firebase Authentication, Google Identity Services, Google ID Token Credential.
- **Backend Services:** Next.js API Routes, Prisma ORM, Neon PostgreSQL.
- **Media & Assets:** Cloudinary, Unsplash CDN.
- **Design Tokens:**
  - `ExOwnBlue`: `#2563EB`
  - `ExOwnNavy`: `#0F172A`
  - `ExOwnEmerald`: `#059669`
  - `ExOwnBackground`: `#F8FAFC`
