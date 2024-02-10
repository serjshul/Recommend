package com.serj.recommend.android.ui.styles

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color.Companion.Gray

private val lightColorScheme = lightColorScheme(
    primary = secondary,
    secondary = primary,
    tertiary = Gray
)

private val darkColorScheme = darkColorScheme(
    primary = secondary,
    secondary = primary,
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
