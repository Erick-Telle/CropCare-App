package com.cropcare.core.ui.theme

import androidx.activity.compose.LocalActivity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = AccentEmerald,
    onPrimary = DarkBackground,
    primaryContainer = AccentEmeraldDim.copy(alpha = 0.2f),
    onPrimaryContainer = AccentEmerald,
    secondary = AccentTurquoise,
    onSecondary = DarkBackground,
    tertiary = AccentAmber,
    onTertiary = DarkBackground,
    background = DarkBackground,
    onBackground = OnDarkPrimary,
    surface = DarkSurface1,
    onSurface = OnDarkPrimary,
    surfaceVariant = DarkSurface2,
    onSurfaceVariant = OnDarkSecondary,
    surfaceContainerLow = DarkSurface1,
    surfaceContainer = DarkSurface2,
    surfaceContainerHigh = DarkSurface3,
    outline = CardBorderDark,
    outlineVariant = CardBorderDark.copy(alpha = 0.5f),
    error = StatusOverdue,
    onError = OnDarkPrimary
)

private val LightColorScheme = lightColorScheme(
    primary = LightPrimary,
    onPrimary = OnLightPrimary,
    primaryContainer = LightPrimaryContainer,
    onPrimaryContainer = LightPrimary,
    secondary = AccentTurquoise,
    onSecondary = OnLightPrimary,
    tertiary = AccentAmber,
    onTertiary = DarkBackground,
    background = LightBackground,
    onBackground = Color(0xFF1A2318),
    surface = LightSurface1,
    onSurface = Color(0xFF1A2318),
    surfaceVariant = LightSurface2,
    onSurfaceVariant = Color(0xFF4B5563),
    surfaceContainerLow = LightSurface1,
    surfaceContainer = LightSurface2,
    surfaceContainerHigh = LightSurface3,
    outline = CardBorderLight,
    error = StatusOverdue
)

@Composable
fun CropCareTheme(
    darkTheme: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    val view = LocalView.current
    val activity = LocalActivity.current

    if (activity != null && !view.isInEditMode) {
        SideEffect {
            val window = activity.window
            window.statusBarColor = colorScheme.background.toArgb()
            window.navigationBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = CropCareTypography,
        shapes = CropCareShapes,
        content = content
    )
}
