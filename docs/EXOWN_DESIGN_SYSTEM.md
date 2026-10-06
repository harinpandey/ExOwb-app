# EXOWN — DESIGN SYSTEM SPECIFICATION

> **Single Source of Truth for Android UI Engineering**  
> **Platform:** Android Native (Jetpack Compose + Kotlin + Material 3)  
> **Aesthetic Philosophy:** Clean • Confident • Functional • Campus-Specific • Restrained

---

## 1. Color Palette

ExOwn uses a disciplined, dark-first color scheme designed for visual focus, high text legibility, and zero decorative noise.

| Token | Hex Value | Role & Usage |
|---|---|---|
| **Background Base** | `#07090D` | Root scaffold canvas across all screens |
| **Surface Level 1** | `#10141B` | Primary cards, TopAppBar, BottomNavigationBar |
| **Surface Level 2** | `#161C25` | Input fields, active chips, dialogs, bottom sheets |
| **Hairline Border** | `#1F2633` | Subtle 1dp structural borders and dividers |
| **Primary Action** | `#2A72E8` | Primary CTA buttons, active state indicators, key price highlights |
| **Text Primary** | `#FFFFFF` | Headlines, listing titles, primary numerals |
| **Text Secondary** | `#94A3B8` | Subtitles, specifications, secondary labels |
| **Text Muted** | `#64748B` | Timestamps, placeholder hints, tertiary captions |
| **Exchange & Verified** | `#10B981` | Barter badge, verified student checkmark, circular economy stats |
| **Rental Highlight** | `#F59E0B` | Rental period rates (`/day`, `/month`), caution badges |
| **Urgent & Danger** | `#EF4444` | Genuine moving-out deals, error states, destructive actions |

*Rule: No random purple/pink gradients, no neon halos, and no glassmorphism.*

---

## 2. Type Scale & Hierarchy

ExOwn typography uses a clear typographic hierarchy structured around four core levels: **Display**, **Headline**, **Body**, and **Caption**.

| Level | Size | Line Height | Font Weight | Color | Usage |
|---|---|---|---|---|---|
| **Display** | `24sp` | `30sp` | Black (900) | `#FFFFFF` | Hero titles, login branding, metric hero numbers |
| **Headline** | `18sp–20sp` | `24sp–26sp` | Bold (700) | `#FFFFFF` | Screen titles (`Home`, `Explore`, `Sell`), modal headers |
| **Body** | `13sp–14sp` | `18sp–20sp` | Regular / SemiBold | `#FFFFFF` / `#94A3B8` | Listing titles (SemiBold, max 2 lines), item descriptions (Regular) |
| **Caption** | `9sp–11sp` | `12sp–14sp` | Medium / Bold | `#94A3B8` / `#FFFFFF` | Location tags, timestamps, condition pills, badge labels |

---

## 3. Spacing Scale (8 / 12 / 16 / 24 / 32)

Layouts follow a strict spacing grid using 4/8dp standard increments:

- **`8dp`**: Internal padding for compact chips, small badges, and tight icon-label rows.
- **`12dp`**: Card internal padding, vertical gaps in listing summaries, and list item spacing.
- **`16dp`**: Standard screen edge margin, default padding between cards in lists and grids.
- **`24dp`**: Separation between distinct content sections and bottom sheet headers.
- **`32dp`**: Major visual section boundaries, empty state margins, and auth hero spacing.

*Touch Target Requirement: All interactive elements maintain a minimum touch target size of `48.dp x 48.dp`.*

---

## 4. Reusable Component Specifications

### 4.1 `ExOwnButton`
The standardized button component supporting multiple variants, loading states, and minimum 48dp touch targets.
- **Variants:** Primary (`#2A72E8`), Secondary (`#161C25`), Outline, Danger (`#EF4444`).
- **Touch Target:** Minimum 48dp height and width.

### 4.2 `ListingCard`
The core marketplace product presentation component where user-generated product photography is the visual hero.
- **Image Canvas:** 4:3 aspect ratio, background `#161C25`.
- **Badges:** Type pill, Urgent tag (`#EF4444`), Condition tag, and 34dp circular save button.
- **Body:** Title (clamped to 2 lines), Price (`₹`, `#2A72E8`), Verification badge, and location.

### 4.3 `VerificationBadge`
A compact trust indicator communicating verified college identity without promotional exaggeration (`#10B981`).

### 4.4 `CategoryChip`
Horizontal category navigation and filtering component (`#161C25` surface, `#1F2633` border, `#2A72E8` active state).
