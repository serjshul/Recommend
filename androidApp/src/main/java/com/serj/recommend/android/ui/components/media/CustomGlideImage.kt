package com.serj.recommend.android.ui.components.media

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import com.bumptech.glide.integration.compose.CrossFade
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.google.firebase.storage.StorageReference
import com.serj.recommend.android.R

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CustomGlideImage(
    modifier: Modifier = Modifier,
    reference: StorageReference?
) {
    GlideImage(
        modifier = modifier,
        model = reference,
        loading = placeholder(ColorPainter(Gray)),
        failure = placeholder(R.drawable.glide_failure),
        transition = CrossFade,
        contentScale = ContentScale.Crop,
        contentDescription = "Glide image"
    )
}