package com.serj.recommend.android.ui.screens.main.newRecommendation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.google.firebase.storage.StorageReference
import com.serj.recommend.android.common.ext.recommendationMediaClip
import com.serj.recommend.android.common.ext.recommendationMediaShape
import com.serj.recommend.android.common.ext.textInterval
import com.serj.recommend.android.common.ext.textShape
import com.serj.recommend.android.common.ext.toParagraphText
import com.serj.recommend.android.ui.components.media.CustomGlideImage
import com.serj.recommend.android.ui.components.media.CustomVideoPlayer
import com.serj.recommend.android.ui.components.text.TextParagraphs
import com.serj.recommend.android.ui.components.text.Title

@Composable
fun NewRecommendationParagraph(
    modifier: Modifier = Modifier,
    title: String,
    imageReference: StorageReference?,
    videoReference: StorageReference?,
    text: String,
    color: Color
) {
    val paragraphTexts = text.toParagraphText()

    Column(
        modifier = modifier
    ){
        Title(
            modifier = Modifier.textInterval(),
            title = title,
            color = color
        )

        if (videoReference != null) {
            CustomVideoPlayer()
        } else if (imageReference != null) {
            CustomGlideImage(
                modifier = Modifier
                    .recommendationMediaShape()
                    .textInterval()
                    .recommendationMediaClip(),
                reference = imageReference
            )
        }

        TextParagraphs(
            modifier = Modifier.textShape(),
            paragraphTexts = paragraphTexts
        )
    }
}