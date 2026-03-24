package com.rainyday.momentapp.core.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val darkColorScheme = darkColorScheme(
    primary = PrimaryPurple,
    onPrimary = TextPrimary,
    primaryContainer = BackgroundElevated,
    onPrimaryContainer = PrimaryPurpleLight,

    secondary = AccentPink,
    onSecondary = TextPrimary,
    secondaryContainer = BackgroundCard,
    onSecondaryContainer = AccentPinkLight,

    background = BackgroundDeep,
    onBackground = TextPrimary,

    surface = BackgroundCard,
    onSurface = TextPrimary,
    surfaceVariant = BackgroundElevated,
    onSurfaceVariant = TextSecondary,

    outline = NavUnselected,
)

private val lightColorScheme = lightColorScheme(
    primary = LightPrimaryPurple,
    onPrimary = LightFabContent,
    primaryContainer = LightBackgroundAccent,
    onPrimaryContainer = LightPrimaryPurpleLight,

    secondary = LightAccentPink,
    onSecondary = LightFabContent,
    secondaryContainer = LightBackgroundElevated,
    onSecondaryContainer = LightAccentPinkLight,

    background = LightBackgroundBase,
    onBackground = LightTextPrimary,

    surface = LightBackgroundCard,
    onSurface = LightTextPrimary,
    surfaceVariant = LightTabBackground,
    onSurfaceVariant = LightTextSecondary,

    outline = LightOutline,
)

@Composable
fun MomentAppTheme(
//    darkTheme: Boolean = isSystemInDarkTheme(),
    darkTheme: Boolean = true,
    content: @Composable () -> Unit
) {
//    val colorScheme = when {
//        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
//            val context = LocalContext.current
//            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
//        }
//
//        darkTheme -> darkColorScheme
//        else -> lightColorScheme
//    }


    val colorScheme = if (darkTheme) darkColorScheme else lightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}