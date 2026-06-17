package com.cropcare.core.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val LightColorScheme = lightColorScheme(
    primary = GreenPrimary,
    onPrimary = OnPrimaryLight,
    primaryContainer = GreenPrimaryLight,
    onPrimaryContainer = GreenPrimaryDark,
    secondary = GreenSecondary,
    onSecondary = OnPrimaryLight,
    tertiary = GreenTertiary,
    onTertiary = OnBackgroundLight,
    background = BackgroundLight,
    onBackground = OnBackgroundLight,
    surface = SurfaceLight,
    onSurface = OnBackgroundLight,
    surfaceVariant = EarthCream,
    onSurfaceVariant = EarthBrown,
    error = StatusOverdue
)

private val DarkColorScheme = darkColorScheme(
    primary = GreenPrimaryLight,
    onPrimary = GreenPrimaryDark,
    primaryContainer = MossGreen,
    onPrimaryContainer = OnPrimaryDark,
    secondary = GreenSecondary,
    onSecondary = OnPrimaryDark,
    tertiary = GreenTertiary,
    onTertiary = OnBackgroundDark,
    background = BackgroundDark,
    onBackground = OnBackgroundDark,
    surface = SurfaceDark,
    onSurface = OnBackgroundDark,
    surfaceVariant = MossGreen,
    onSurfaceVariant = EarthSand,
    error = StatusOverdue
)

@Composable
fun CropCareTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
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
        typography = CropCareTypography,
        content = content
    )
}
