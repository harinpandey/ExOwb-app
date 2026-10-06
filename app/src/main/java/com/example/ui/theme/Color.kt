package com.example.ui.theme

import androidx.compose.ui.graphics.Color

// Primary Electric Blue Brand Colors
val ElectricBlue = Color(0xFF2563EB)
val ElectricBlueBright = Color(0xFF3B82F6)
val ElectricBlueGlow = Color(0xFF60A5FA)
val ElectricBlueContainer = Color(0xFF1E3A8A)

// Accent & Status Colors
val ExOwnTeal = Color(0xFF0D9488)
val ExOwnEmerald = Color(0xFF10B981)
val ExOwnAmber = Color(0xFFF59E0B)
val ExOwnPurple = Color(0xFF8B5CF6)
val ExOwnRose = Color(0xFFF43F5E)

// Premium Dark Theme Surfaces
val DarkBg = Color(0xFF0A0E17)
val DarkSurface = Color(0xFF111827)
val DarkSurfaceElevated = Color(0xFF1F2937)
val DarkSurfaceCard = Color(0xFF161F30)
val DarkBorder = Color(0xFF26334D)
val DarkDivider = Color(0xFF1E293B)

// Text & Neutral Colors
val TextPrimary = Color(0xFFF8FAFC)
val TextSecondary = Color(0xFF94A3B8)
val TextMuted = Color(0xFF64748B)
val PureWhite = Color(0xFFFFFFFF)

// Legacy alias compatibility
val ExOwnBlue = ElectricBlue
val ExOwnBlueLight = ElectricBlueBright
val ExOwnBlueContainer = ElectricBlueContainer
val Slate900 = DarkBg
val Slate800 = DarkSurface
val Slate700 = DarkSurfaceElevated
val Slate600 = TextMuted
val Slate500 = TextSecondary
val Slate400 = TextSecondary
val Slate300 = Color(0xFFCBD5E1)
val Slate200 = DarkBorder
val Slate100 = DarkSurfaceElevated
val Slate50 = DarkBg

val ExOwnDarkColorScheme = androidx.compose.material3.darkColorScheme(
    primary = ElectricBlueBright,
    onPrimary = PureWhite,
    primaryContainer = ElectricBlueContainer,
    onPrimaryContainer = Color(0xFFDBEAFE),
    secondary = ExOwnTeal,
    onSecondary = PureWhite,
    secondaryContainer = Color(0xFF134E4A),
    onSecondaryContainer = Color(0xFFCCFBF1),
    tertiary = ExOwnAmber,
    onTertiary = PureWhite,
    background = DarkBg,
    onBackground = TextPrimary,
    surface = DarkSurface,
    onSurface = TextPrimary,
    surfaceVariant = DarkSurfaceElevated,
    onSurfaceVariant = TextSecondary,
    outline = DarkBorder,
    outlineVariant = DarkDivider
)

val ExOwnLightColorScheme = androidx.compose.material3.lightColorScheme(
    primary = ElectricBlue,
    onPrimary = PureWhite,
    primaryContainer = Color(0xFFDBEAFE),
    onPrimaryContainer = ElectricBlue,
    secondary = ExOwnTeal,
    onSecondary = PureWhite,
    background = Color(0xFFF8FAFC),
    onBackground = Color(0xFF0F172A),
    surface = PureWhite,
    onSurface = Color(0xFF0F172A),
    surfaceVariant = Color(0xFFF1F5F9),
    onSurfaceVariant = Color(0xFF475569),
    outline = Color(0xFFCBD5E1),
    outlineVariant = Color(0xFFE2E8F0)
)
