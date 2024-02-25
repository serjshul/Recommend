package com.serj.recommend.android.ui.screens.common.recommendation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
fun Paragraphs(
    modifier: Modifier = Modifier,
    paragraphs: List<HashMap<String, String>>,
    paragraphsReferences: HashMap<String, StorageReference?>,
    color: Color
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(White)
            .padding(15.dp, 12.5.dp)
    ) {
        for (i in paragraphs.indices) {
            Paragraph(
                modifier =
                    if (i != paragraphs.size - 1)
                        Modifier.padding(bottom = 25.dp)
                    else
                        Modifier,
                title = paragraphs[i]["title"] ?: "",
                imageReference = paragraphsReferences[paragraphs[i]["title"]],
                videoReference = null,
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
    imageReference: StorageReference?,
    videoReference: StorageReference?,
    text: String,
    color: Color = Color.Black
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

@Preview
@Composable
fun ParagraphsPreview() {
    val paragraphs = listOf(
        hashMapOf(
            "title" to "Title",
            "text" to "Text text text text text text text text text text text text text text " +
                    "text text text text text text text text text text text text text text " +
                    "text text text text text text text text text text text text text text\n" +
                    "Text text text text text text text text text text text text text text " +
                    "text text text text text text text text text text text text text text"
        ),
        hashMapOf(
            "title" to "Title",
            "text" to "Text text text text text text text text text text text text text text " +
                    "text text text text text text text text text text text text text text " +
                    "text text text text text text text text text text text text text text\n" +
                    "Text text text text text text text text text text text text text text " +
                    "text text text text text text text text text text text text text text"
        ),
        hashMapOf(
            "title" to "Title",
            "text" to "Text text text text text text text text text text text text text text " +
                    "text text text text text text text text text text text text text text " +
                    "text text text text text text text text text text text text text text\n" +
                    "Text text text text text text text text text text text text text text " +
                    "text text text text text text text text text text text text text text"
        )
    )

    Paragraphs(
        paragraphs = paragraphs,
        paragraphsReferences = hashMapOf(),
        color = Color.DarkGray
    )
}