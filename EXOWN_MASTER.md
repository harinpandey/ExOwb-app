# EXOWN — THE PRODUCT CONSTITUTION

> **"What do I need, who around me has it, and can I trust them?"**  
> ExOwn is the operating system for student-to-student commerce and services on campus.

---

## 1. The Core Philosophy

ExOwn is not a generic e-commerce app or classifieds board. It is the trusted digital layer of campus life. Every interaction is local, student-to-student, and grounded in campus reality.

### The Product Loop
$$\text{Campus} \longrightarrow \text{Discover} \longrightarrow \text{Trust} \longrightarrow \text{Connect} \longrightarrow \text{Deal} \longrightarrow \text{Complete} \longrightarrow \text{Reputation}$$

A student at LPU (Lovely Professional University) or any campus never sees an overwhelming, irrelevant national feed. They see:
> **Available around LPU · Phagwara**

---

## 2. The Four Connected Layers

```text
                           EXOWN
                             │
        ┌────────────────────┼────────────────────┐
        │                    │                    │
   MARKETPLACE             CAMPUS               TRUST
        │                    │                    │
   Buy / Sell / Rent       Housing              Verification
   Exchange (Barter)       Roommates            Trust Passport
   Student Services        Hostel Pickups       Safety & Audits
        │                    │                    │
        └────────────────────┼────────────────────┘
                             │
                             AI
                             │
             Search / Listing Camera / Safety
```

1. **Marketplace Layer:** Buy, Sell, Rent, and Exchange. Peer-to-peer re-commerce with zero middlemen and zero commission.
2. **Campus Layer:** Hostel-aware geography (BH-1, GH-3, Law Gate, Green Valley). Campus housing, roommate matching, and campus-specific deals.
3. **Trust Layer:** Verified institutional identity (`.ac.in`, `.edu`, college ID verification), transparent Trust Passport, and structured Deal Rooms.
4. **AI Layer:** Grounded, unobtrusive assistance: natural-language campus search, camera-first listing assistance, description polish, and scam signal detection. AI assists the student; it never replaces student authority or fabricates data.

---

## 3. Product Principles & Invariants

### What ExOwn ALWAYS Does
1. **Answers the 3 Critical Student Questions:** What do I need? Who near me has it? Can I trust them?
2. **Prioritizes Real Photos:** The product photograph is the visual hero. Clean, uncropped 4:3 cards with real student items.
3. **Campus-Centricity:** Campus is a first-class citizen. All filtering, delivery, pickup, and pricing is contextualized to the active university campus.
4. **Structured Deals:** Conversations culminate in structured Deal Rooms with agreed pickup points (e.g., LPU Main Gate, Uni-Mall, Hostel Lobby) and transparent status tracking.
5. **Authentic Reputation:** Trust is built on verified institutional identity, confirmed transactions, and genuine peer reviews—never on arbitrary algorithmic scores.

### What ExOwn NEVER Does
1. **No Fake Data or AI Slop:** Never invent fake reviews, artificial view counters ("14 people looking now"), or synthetic ratings.
2. **No Generic Marketplace Noise:** No neon glow cards, rainbow gradients, glassmorphism, or cartoon illustrations.
3. **No Direct Database Access from Mobile:** Mobile clients NEVER connect directly to PostgreSQL/Neon. All access flows through authenticated Next.js backend APIs.
4. **No Destructive Database Resets:** Schema and identity mappings are preserved with zero-loss migrations.
5. **No Speculative Moderation:** Moderation actions, payments, and account suspensions are strictly governed by deterministic rules and authorized personnel.

---

## 4. Core Features & Unique Product Concepts

### 4.1 ExOwn Trust Passport
Instead of an opaque percentage score, every student holds a transparent Trust Passport:
- **Identity:** Verified Student badge (institutional email / ID card proof).
- **Account:** Member since date and active campus affiliation.
- **Activity:** Total active and archived listings.
- **Deals:** Number of completed handovers on campus.
- **Reputation:** Confirmed peer ratings and feedback comments.
- **Safety:** Clean record confirmation with zero open dispute flags.

### 4.2 The Deal Room
Replaces disorganized, ghost-prone chat threads with a clear transaction state machine:
```text
[Offer Sent: ₹26,000] 
       ↓
[Seller Accepted ✓] 
       ↓
[Pickup Agreed: LPU Main Gate / BH-1 Reception] 
       ↓
[Handover & Completed]
```

### 4.3 Camera-First Selling
1. Student snaps photos of their textbooks, cycle, calculator, or hostel cooler.
2. Gemini AI assists with title, category, and draft description.
3. Student retains 100% control: verifies condition, sets price (or selects Exchange/Rent), and confirms hostel pickup point.
4. Instant publish to campus peers.

### 4.4 Exchange as a First-Class Citizen
Exchange (barter) is not a hidden checkbox. It is a core pillar alongside Buy and Rent:
> *"I have a Gaming Mouse → I want a Mechanical Keyboard + ₹500 cash adjustment."*

### 4.5 Student Lifecycle Awareness
- **Freshman Mode:** Focus on hostel kits, mattress protectors, study tables, induction cooktops, and first-year textbooks.
- **Campus Mode:** Regular semester recommerce, rentals, gadget upgrades, and roommate searches.
- **Exit / Relocation Mode:** Graduation clearance sales, bike handovers, and security deposit recoveries.

---

## 5. Mobile App Structure (5 Core Tabs)

```text
┌────────────────────────────────────────────────────────┐
│                        EXOWN                           │
│  Good morning, Hari · LPU Phagwara                     │
├────────────────────────────────────────────────────────┤
│                                                        │
│                    MAIN CONTENT                        │
│                                                        │
├────────────┬────────────┬──────────┬──────────┬────────┤
│    Home    │   Search   │   Sell   │  Inbox   │Profile │
└────────────┴────────────┴──────────┴──────────┴────────┘
```

1. **Home:** Personalized greeting, natural search prompt, campus selector, quick action chips (`Buy`, `Sell`, `Rent`, `Exchange`), and contextual feeds (Near your campus, Recently added, Popular this week, Student essentials).
2. **Search:** Category browsing, deep keyword filters, natural-language query resolution, price constraints, and instant condition toggles.
3. **Sell:** Camera-first photo upload, AI-assisted cataloging, pricing, rental/exchange terms, and hostel pickup selection.
4. **Inbox:** Active student chats, offer negotiation threads, and structured **Deal Rooms** with pickup verification.
5. **Profile:** **ExOwn Trust Passport**, my active listings, saved items, campus affiliation, sustainability impact stats (₹ saved, kg CO₂ offset), and settings.

---

## 6. Engineering Standard & Acceptance Criteria

Every feature implemented in ExOwn must satisfy the 9-point engineering checklist:
1. **UI:** Adheres strictly to the ExOwn Design System (`#07090D` base, `#10141B` surface, `#2A72E8` blue).
2. **API:** Contract-driven REST/JSON calls to the existing Next.js backend.
3. **Data:** Backed by the authoritative Prisma / PostgreSQL schema.
4. **Loading:** Subtle skeletons or standard progress indicators without screen jumping.
5. **Empty State:** Contextual, helpful, and actionable empty views with direct recovery buttons.
6. **Error State:** Clear recovery guidance without developer jargon or crashes.
7. **Security:** Token-gated endpoints; secrets exclusively managed on the server.
8. **Testing:** Verified via local JVM Robolectric tests and clean compilation.
9. **Accessibility:** Touch targets $\ge 48\times 48\,\text{dp}$, TalkBack labels, and AAA color contrast.
