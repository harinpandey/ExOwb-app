package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val LightColorScheme = lightColorScheme(
    primary = ExOwnBlue,
    onPrimary = Color.White,
    primaryContainer = ExOwnBlueContainer,
    onPrimaryContainer = ExOwnOnBlueContainer,
    secondary = ExOwnNavy,
    onSecondary = Color.White,
    secondaryContainer = ExOwnNavyLight,
    onSecondaryContainer = Color.White,
    tertiary = ExOwnEmerald,
    onTertiary = Color.White,
    tertiaryContainer = ExOwnEmeraldContainer,
    onTertiaryContainer = ExOwnOnEmeraldContainer,
    background = ExOwnBackground,
    onBackground = ExOwnNavy,
    surface = ExOwnSurface,
    onSurface = ExOwnNavy,
    surfaceVariant = Color(0xFFF1F5F9),
    onSurfaceVariant = ExOwnSlate,
    outline = ExOwnBorder,
    outlineVariant = Color(0xFFF1F5F9)
)

private val DarkColorScheme = darkColorScheme(
    primary = ExOwnBlueLight,
    onPrimary = Color.White,
    primaryContainer = Color(0xFF1E3A8A),
    onPrimaryContainer = Color(0xFFDBEAFE),
    secondary = Color(0xFFE2E8F0),
    onSecondary = Color(0xFF0F172A),
    secondaryContainer = Color(0xFF1E293B),
    onSecondaryContainer = Color.White,
    tertiary = ExOwnEmeraldLight,
    onTertiary = Color.White,
    tertiaryContainer = Color(0xFF064E3B),
    onTertiaryContainer = Color(0xFFA7F3D0),
    background = Color(0xFF0C1017),
    onBackground = Color(0xFFF8FAFC),
    surface = Color(0xFF10141B),
    onSurface = Color(0xFFF8FAFC),
    surfaceVariant = Color(0xFF1E293B),
    onSurfaceVariant = Color(0xFF94A3B8),
    outline = Color(0xFF334155),
    outlineVariant = Color(0xFF1E293B)
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false, // Keep ExOwn branding consistent
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
