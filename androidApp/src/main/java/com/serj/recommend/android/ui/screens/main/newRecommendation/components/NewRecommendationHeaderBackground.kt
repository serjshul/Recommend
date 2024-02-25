package com.serj.recommend.android.ui.screens.main.newRecommendation.components

import android.net.Uri
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import com.bumptech.glide.integration.compose.CrossFade
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.serj.recommend.android.common.ext.recommendationHeaderShape
import com.serj.recommend.android.ui.styles.background
import com.serj.recommend.android.ui.styles.primary

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun NewRecommendationHeaderBackground(
    modifier: Modifier = Modifier,
    backgroundImageUri: Uri?,
    color: Color? = primary
) {
    Box(
        modifier = modifier.recommendationHeaderShape()
    ) {
        when {
            backgroundImageUri != null -> {
                GlideImage(
                    model = backgroundImageUri,
                    contentDescription = "Background",
                    colorFilter = ColorFilter
                        .colorMatrix(ColorMatrix().apply {
                            setToScale(0.6f, 0.6f, 0.6f, 1f)
                        }),
                    transition = CrossFade,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }
            else -> {
                val brush =
                    if (color != null)
                        Brush.verticalGradient(listOf(color, background))
                    else
                        Brush.verticalGradient(listOf(primary, background))
                Canvas(
                    modifier = Modifier.fillMaxSize(),
                    onDraw = { drawRect(brush) }
                )
            }
        }
    }
}

@Preview
@Composable
fun NewRecommendationHeaderBackgroundPreview() {
    NewRecommendationHeaderBackground(
        backgroundImageUri = null
    )
}