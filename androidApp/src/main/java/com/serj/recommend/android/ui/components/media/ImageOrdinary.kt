package com.serj.recommend.android.ui.components.media

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale

@Composable
fun ImageOrdinary(
    modifier: Modifier = Modifier,
    image: Bitmap
) {
    Image(
        modifier = modifier,
        bitmap = image.asImageBitmap(),
        contentDescription = "image",
        contentScale = ContentScale.Crop
    )
}