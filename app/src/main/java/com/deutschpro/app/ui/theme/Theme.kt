package com.deutschpro.app.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection

private val LightColors = lightColorScheme(
    primary = SlateBlue60,
    onPrimary = Neutral10,
    primaryContainer = SlateBlue20,
    onPrimaryContainer = SlateBlue90,
    secondary = Amber60,
    onSecondary = Neutral95,
    secondaryContainer = Amber40,
    onSecondaryContainer = Amber80,
    background = Neutral10,
    onBackground = Neutral90,
    surface = Color(0xFFFFFFFF),
    onSurface = Neutral90,
    surfaceVariant = Neutral20,
    onSurfaceVariant = Neutral80,
    error = Error40,
    onError = Neutral10,
    outline = Neutral40
)

private val DarkColors = darkColorScheme(
    primary = SlateBlue40,
    onPrimary = SlateBlue90,
    primaryContainer = SlateBlue80,
    onPrimaryContainer = SlateBlue20,
    secondary = Amber40,
    onSecondary = Amber80,
    secondaryContainer = Amber80,
    onSecondaryContainer = Amber40,
    background = Neutral95,
    onBackground = Neutral20,
    surface = Neutral90,
    onSurface = Neutral20,
    surfaceVariant = Neutral80,
    onSurfaceVariant = Neutral40,
    error = Error80,
    onError = Neutral10,
    outline = Neutral60
)

/**
 * App-wide theme. [useDarkTheme] defaults to the system setting but is
 * driven by the user's explicit choice (stored via PreferencesManager)
 * once they change it in Settings. The whole app is forced to RTL layout
 * direction since the entire UI is Persian-first.
 */
@Composable
fun DeutschProTheme(
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (useDarkTheme) DarkColors else LightColors

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        MaterialTheme(
            colorScheme = colors,
            typography = DeutschProTypography,
            shapes = DeutschProShapes,
            content = content
        )
    }
}
