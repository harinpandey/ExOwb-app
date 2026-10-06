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

*Rule: Never make all text bold. Use weight and scale to guide eye movement.*

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

- **Variants:**
  - `PRIMARY`: Background `#2A72E8`, Text `#FFFFFF`, shape `RoundedCornerShape(10.dp)`.
  - `SECONDARY`: Background `#161C25`, Border 1dp `#1F2633`, Text `#FFFFFF`.
  - `OUTLINE`: Background Transparent, Border 1dp `#2A72E8`, Text `#2A72E8`.
  - `DANGER`: Background `#EF4444`, Text `#FFFFFF`.
- **Loading State:** CircularProgressIndicator (20dp, stroke 2dp) replacing text content while retaining button width.
- **Touch Target:** Height `48dp`, minimum width `48dp`.

```kotlin
@Composable
fun ExOwnButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isLoading: Boolean = false,
    variant: ExOwnButtonVariant = ExOwnButtonVariant.PRIMARY,
    leadingIcon: ImageVector? = null
)
```

---

### 4.2 `ListingCard`
The core marketplace product presentation component where user-generated product photography is the visual hero.

- **Image Canvas:** 4:3 aspect ratio, background `#161C25`, `ContentScale.Crop`.
- **Type Badge (Top-Left):** Semi-translucent pill for `SELL`, `RENT`, `EXCHANGE`.
- **Urgent Tag (Top-Left):** `#EF4444` solid pill with bold "URGENT" label when `isUrgent == true`.
- **Wishlist Button (Top-Right):** 34dp circular dark scrim with `Favorite` heart toggle.
- **Condition Tag (Bottom-Left):** `#000000` with 75% alpha, 4dp rounded corner, uppercase condition label.
- **Body Section:**
  1. Title: 13sp SemiBold, max 2 lines with ellipsis.
  2. Price Row: `₹` currency symbol, 17sp Black in `#2A72E8`, optional strikethrough original price, optional `/day` or `/month` rental unit.
  3. Verification: `VerificationBadge` aligned to price row.
  4. Location & Time: Hostel/campus location pin (`BH-1`, `Lawgate`) and relative timestamp (`2h ago`).
- **Surface & Border:** Container `#10141B`, Border 1dp `#1F2633`, Corner radius `12.dp`. Elevation `0.dp`.

---

### 4.3 `VerificationBadge`
A compact trust indicator communicating verified college identity without promotional exaggeration.

- **Icon:** `Icons.Default.Verified` in `#10B981` (13dp size).
- **Typography:** 10sp Bold in `#10B981`.
- **Layout:** Row with 3dp spacing, vertical alignment center.

```kotlin
@Composable
fun VerificationBadge(
    modifier: Modifier = Modifier,
    label: String = "Verified"
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Icon(
            imageVector = Icons.Default.Verified,
            contentDescription = "Verified student",
            tint = ExOwnEmerald,
            modifier = Modifier.size(13.dp)
        )
        Spacer(modifier = Modifier.width(3.dp))
        Text(
            text = label,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            color = ExOwnEmerald
        )
    }
}
```

---

### 4.4 `CategoryChip`
Horizontal category navigation and filtering component.

- **Container:** Background `#161C25`, Shape `RoundedCornerShape(8.dp)`.
- **Border:** 1dp solid `#1F2633` (unselected) / `#2A72E8` (selected).
- **Icon:** 15dp tint `#2A72E8`.
- **Label:** 12sp Medium in `#FFFFFF` (unselected) / `#2A72E8` (selected).
- **Padding:** Horizontal 12dp, Vertical 8dp.

```kotlin
@Composable
fun CategoryChip(
    category: ListingCategory,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(8.dp),
        color = if (isSelected) ExOwnDarkSurface2 else ExOwnDarkSurface1,
        border = BorderStroke(1.dp, if (isSelected) ExOwnBlue else ExOwnDarkBorder),
        modifier = modifier.clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = category.icon,
                contentDescription = category.name,
                tint = if (isSelected) ExOwnBlue else ExOwnTextSecondary,
                modifier = Modifier.size(15.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = category.name,
                fontSize = 12.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                color = if (isSelected) ExOwnBlue else ExOwnTextPrimary
            )
        }
    }
}
```

---

## 5. Architectural Alignment

All components in this design system are implemented in:
- `com.example.ui.theme.Color.kt`
- `com.example.ui.theme.Theme.kt`
- `com.example.ui.components.ExOwnComponents.kt`
- `com.example.ui.components.ProductCard.kt`

Every screen in ExOwn references these tokens and components directly to guarantee visual consistency.
