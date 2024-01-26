package com.serj.recommend.android.ui.screens.common.recommendation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.google.firebase.storage.StorageReference
import com.serj.recommend.android.R
import com.serj.recommend.android.common.ext.mediaHeaderShape
import com.serj.recommend.android.common.ext.recommendationHeaderShape
import com.serj.recommend.android.ui.components.media.CustomGlideImageShaded

@Composable
fun HeaderBackground(
    modifier: Modifier = Modifier,
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
                Image(
                    modifier = Modifier.mediaHeaderShape(),
                    painter = painterResource(id = R.drawable.gradient),
                    contentDescription = "header background",
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}

@Preview
@Composable
fun HeaderPreview() {
    HeaderBackground(
        backgroundImageReference = null,
        backgroundVideoReference = null
    )
}