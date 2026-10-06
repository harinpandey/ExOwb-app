# EXOWN — PRODUCT & UI ENGINEERING CONSTITUTION

ExOwn is a real student marketplace product, not a demo, template, AI showcase, or generic SaaS dashboard.

The goal is to build a polished, believable commercial Android application that could realistically be released to university students.

---

## 1. DESIGN PRINCIPLES

1. **Prioritize usability over decoration.** Every element exists for a functional student commerce need.
2. **Prioritize information hierarchy over visual effects.** Typography size, weight, and spacing guide the eye, not heavy borders or neon blurs.
3. **Use restrained, intentional visual design.** Avoid the stereotypical "AI-generated" aesthetic.
4. **Avoid generic AI-generated UI patterns:**
   - ❌ NO purple-to-pink or rainbow gradients.
   - ❌ NO glassmorphism / excessive backdrop blur.
   - ❌ NO unnecessary neon glows or outer shadows.
   - ❌ NO decorative blobs, floating geometric shapes, or abstract 3D placeholders.
   - ❌ NO "card inside a card inside a card" nesting.
   - ❌ NO giant empty hero graphics inside functional task screens.
   - ❌ NO constant floating elements or bouncy animations.
5. **Every visual element must have a product reason.**
6. **Do not invent features that were not requested.**
7. **Do not redesign existing architecture without justification.**
8. **Reuse existing ExOwn concepts and terminology:**
   - Buy, Sell, Rent, Exchange, Campus Housing, Roommates, Peer Services.
9. **Preserve consistency across every screen.**
10. **Prefer small refinements over dramatic visual changes.**

---

## 2. COLOR SYSTEM

ExOwn uses an understated, dark-first visual palette designed for focus and readability:

| Role | Color Hex | Usage |
|---|---|---|
| **Base Background** | `#07090D` | Root canvas for screens |
| **Surface Level 1** | `#10141B` | Primary cards, top app bar, bottom nav bar |
| **Surface Level 2** | `#161C25` | Input fields, active chips, dialog surfaces, secondary containers |
| **Borders & Dividers** | `#1F2633` | Crisp, hairline structural borders |
| **Text Primary** | `#FFFFFF` / `#F8FAFC` | Headlines, listing titles, primary numbers |
| **Text Secondary** | `#94A3B8` | Subtitles, specifications, secondary labels |
| **Text Muted** | `#64748B` | Timestamps, tertiary captions, hints |
| **Primary Action** | `#2563EB` | Buttons, selected state indicators, primary price highlight |
| **Exchange & Verified** | `#10B981` | Barter badge, verified student shield, sustainability tag |
| **Urgent Deals** | `#EF4444` | Genuine moving-out deals and urgent notices |
| **Rentals & Highlights** | `#F59E0B` | Rental duration rates and caution indicators |

---

## 3. TYPOGRAPHY SYSTEM

Use clean modern sans-serif with strict functional hierarchy:

- **Screen Title:** 20sp, Bold / ExtraBold, `#FFFFFF`
- **Section Heading:** 16sp, Bold, `#FFFFFF`
- **Listing Title:** 14sp, SemiBold, `#FFFFFF`, max 2 lines
- **Price Display:** 18sp–22sp, ExtraBold, `#2563EB` or `#FFFFFF`
- **Metadata (Location/Time):** 11sp–12sp, Medium, `#94A3B8`
- **Badges & Tags:** 10sp, Bold, uppercase, letter-spacing +0.5sp

*Rule: Never make every piece of text bold.*

---

## 4. SPACING SYSTEM

Strict adherence to standard 4/8dp scale:
- `4dp`: inline spacing between icon and text.
- `8dp`: internal padding for chips, small badges, and tight rows.
- `12dp`: card internal padding, item vertical spacing.
- `16dp`: standard screen horizontal margins, standard gutter between cards.
- `20dp`: section separation.
- `24dp`–`32dp`: major layout breaks.

*Rule: Do not randomly change spacing between screens.*

---

## 5. MARKETPLACE UX GUIDELINES

1. **The product itself is the visual hero.** Listing photos receive visual emphasis over graphics.
2. **Every product view answers five questions immediately:**
   - **WHAT:** Clear title and model details.
   - **HOW MUCH:** Transparent ₹ price with rental/original price context.
   - **WHERE:** Real campus location (e.g. `BH-1 Cycle Stand`, `Lawgate Gate 2`).
   - **WHO:** Real student seller with verification status.
   - **CAN I TRUST THIS:** Student department, deal history, and verification badge.
3. **No fabricated data:** Never generate fake social proof (e.g., "120 people are viewing this right now").
4. **Indian Campus Context:** Real student pricing in ₹, semester cycles, hostel handovers, campus gates, and academic needs.

---

## 6. ENGINEERING & SECURITY CONSTRAINTS

1. **No Client-Side Secrets:** Never place database passwords, Firebase Admin private keys, or payment secret keys in the Android APK.
2. **Authenticated APIs:** The mobile app authenticates using Firebase Authentication via Jetpack Credential Manager and communicates securely.
3. **Fail-Fast Error Handling:** Catch cancellation exceptions gracefully, handle network errors with clear recovery actions.
4. **Single Source of Truth:** Mobile and Web share the unified ExOwn data models.
