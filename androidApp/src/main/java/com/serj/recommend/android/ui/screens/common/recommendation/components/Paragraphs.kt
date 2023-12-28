package com.serj.recommend.android.ui.screens.common.recommendation.components

import android.graphics.Bitmap
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.serj.recommend.android.common.ext.recommendationMediaClip
import com.serj.recommend.android.common.ext.recommendationMediaShape
import com.serj.recommend.android.common.ext.recommendationParagraphShape
import com.serj.recommend.android.common.ext.textInterval
import com.serj.recommend.android.common.ext.textShape
import com.serj.recommend.android.common.ext.toParagraphText
import com.serj.recommend.android.ui.components.media.CustomVideoPlayer
import com.serj.recommend.android.ui.components.media.ImageOrdinary
import com.serj.recommend.android.ui.components.text.TextParagraphs
import com.serj.recommend.android.ui.components.text.Title

@Composable
fun Paragraphs(
    modifier: Modifier = Modifier,
    paragraphs: ArrayList<HashMap<String, String>>,
    paragraphsImages: Map<String, Bitmap?>,
    color: Color
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        for (i in paragraphs.indices) {
            Paragraph(
                modifier = Modifier.recommendationParagraphShape(),
                title = paragraphs[i]["title"] ?: "",
                image = paragraphsImages[paragraphs[i]["title"]],
                video = paragraphs[i]["video"],
                text = paragraphs[i]["text"] ?: "",
                color = color
            )
        }
    }
}

@Composable
fun Paragraph(
    modifier: Modifier = Modifier,
    title: String,
    image: Bitmap?,
    video: String?,
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

        if (video != null) {
            CustomVideoPlayer()
        } else if (image != null) {
            ImageOrdinary(
                modifier = Modifier
                    .recommendationMediaShape()
                    .textInterval()
                    .recommendationMediaClip(),
                image = image
            )
        }

        TextParagraphs(
            modifier = Modifier.textShape(),
            paragraphTexts = paragraphTexts
        )
    }
}