package com.serj.recommend.android.ui.components.media

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.bumptech.glide.integration.compose.CrossFade
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.google.firebase.storage.StorageReference
import com.serj.recommend.android.R

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun GlideUserImage(
    modifier: Modifier = Modifier,
    reference: StorageReference?
) {
    if (reference != null) {
        GlideImage(
            modifier = modifier,
            model = reference,
            loading = placeholder(ColorPainter(Color.LightGray)),
            failure = placeholder(R.drawable.glide_failure),
            transition = CrossFade,
            contentScale = ContentScale.Crop,
            contentDescription = "Glide image"
        )
    } else {
        Image(
            modifier = modifier,
            painter = painterResource(id = R.drawable.no_user_photo),
            contentDescription = "No user photo"
        )
    }
}