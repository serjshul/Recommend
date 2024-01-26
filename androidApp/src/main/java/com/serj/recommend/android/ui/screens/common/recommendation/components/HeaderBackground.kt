package com.serj.recommend.android.ui.screens.common.recommendation.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.google.firebase.storage.StorageReference
import com.serj.recommend.android.common.ext.mediaHeaderShape
import com.serj.recommend.android.common.ext.recommendationHeaderShape
import com.serj.recommend.android.ui.components.media.CustomGlideImageShaded
import com.serj.recommend.android.ui.styles.background
import com.serj.recommend.android.ui.styles.primary

@Composable
fun HeaderBackground(
    modifier: Modifier = Modifier,
    color: Color?,
    backgroundImageReference: StorageReference?,
    backgroundVideoReference: StorageReference?
) {
    Box(
        modifier = modifier.recommendationHeaderShape()
    ) {
        when {
            backgroundVideoReference != null -> {
                // TODO: add video player
            }
            backgroundImageReference != null -> {
                CustomGlideImageShaded(
                    modifier = Modifier.mediaHeaderShape(),
                    reference = backgroundImageReference
                )
            }
            else -> {
                val brush =
                    if (color != null)
                        Brush.verticalGradient(listOf(color, background))
                    else
                        Brush.verticalGradient(listOf(primary, background))
                Canvas(
                    modifier = Modifier.mediaHeaderShape(),
                    onDraw = {
                        drawRect(brush)
                    }
                )
            }
        }
    }
}

@Preview
@Composable
fun HeaderPreview() {
    HeaderBackground(
        color = Color.Red,
        backgroundImageReference = null,
        backgroundVideoReference = null
    )
}