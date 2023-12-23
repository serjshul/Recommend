package com.serj.recommend.android.ui.components.media

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale

@Composable
fun ImageRecommendation(
    modifier: Modifier = Modifier,
    image: Bitmap
) {
    Image(
        modifier = modifier,
        bitmap = image.asImageBitmap(),
        contentDescription = "paragraph",
        contentScale = ContentScale.Crop
    )
}