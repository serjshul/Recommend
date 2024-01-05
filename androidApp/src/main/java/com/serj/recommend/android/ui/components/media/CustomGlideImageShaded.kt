package com.serj.recommend.android.ui.components.media

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import com.bumptech.glide.integration.compose.CrossFade
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.google.firebase.storage.StorageReference
import com.serj.recommend.android.R
import com.serj.recommend.android.ui.styles.Gray

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CustomGlideImageShaded(
    modifier: Modifier = Modifier,
    reference: StorageReference?
) {
    GlideImage(
        modifier = modifier,
        model = reference,
        colorFilter = ColorFilter
            .colorMatrix(ColorMatrix().apply {
                setToScale(0.6f, 0.6f, 0.6f, 1f)
            }),
        loading = placeholder(ColorPainter(Gray)),
        failure = placeholder(R.drawable.glide_failure),
        transition = CrossFade,
        contentScale = ContentScale.Crop,
        contentDescription = "Glide image shaded"
    )
}