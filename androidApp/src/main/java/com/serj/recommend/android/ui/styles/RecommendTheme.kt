package com.serj.recommend.android.ui.styles

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val lightColorScheme = lightColorScheme(
    primary = TexasHeatwave,
    secondary = KiriumeRed,
    tertiary = Gray
)

private val darkColorScheme = darkColorScheme(
    primary = TexasHeatwave,
    secondary = KiriumeRed,
    tertiary = Gray
)

@Composable
fun RecommendTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        darkColorScheme
    } else {
        lightColorScheme
    }
    MaterialTheme(
        colorScheme = colors,
        typography = DefaultTypography,
        shapes = DefaultsShapes,
        content = content
    )
}
