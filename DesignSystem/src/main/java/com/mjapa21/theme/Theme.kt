package com.mjapa21.theme

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

private val DarkColorScheme = darkColorScheme(
    primary = Accent80,
    onPrimary = Neutral10,
    primaryContainer = AccentContainerDark,
    onPrimaryContainer = Accent80,

    secondary = Neutral80,
    onSecondary = Neutral10,
    tertiary = Neutral60,
    onTertiary = Color.White,

    background = Neutral10,
    onBackground = Neutral95,
    surface = Neutral20,
    onSurface = Neutral95,
    surfaceVariant = Neutral30,
    onSurfaceVariant = Neutral80,

    outline = Neutral60,
    outlineVariant = Neutral40,

    error = Error80,
    onError = Neutral10
)

private val LightColorScheme = lightColorScheme(
    primary = Accent40,
    onPrimary = Color.White,
    primaryContainer = AccentContainerLight,
    onPrimaryContainer = Accent40,

    secondary = Neutral40,
    onSecondary = Color.White,
    tertiary = Neutral60,
    onTertiary = Color.White,

    background = Neutral99,
    onBackground = Neutral10,
    surface = Color.White,
    onSurface = Neutral10,
    surfaceVariant = Neutral95,
    onSurfaceVariant = Neutral30,

    outline = Neutral60,
    outlineVariant = Neutral90,

    error = Error40,
    onError = Color.White
)

@Composable
fun MidtermAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false, //this is tp make the app colors match the background of the phone
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