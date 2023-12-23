package com.serj.recommend.android.ui.components.media

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale

@Composable
fun ImageHeader(
    modifier: Modifier = Modifier,
    image: Bitmap
) {
    Image(
        modifier = modifier,
        bitmap = image.asImageBitmap(),
        colorFilter = ColorFilter
            .colorMatrix(ColorMatrix().apply {
                setToScale(0.7f, 0.7f, 0.7f, 1f)
            }
            ),
        contentDescription = "header image",
        contentScale = ContentScale.Crop
    )
}