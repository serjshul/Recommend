package com.serj.recommend.android.ui.screens.common.recommendation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.serj.recommend.android.common.ext.toParagraphText
import com.serj.recommend.android.ui.components.text.TextParagraphs

@Composable
fun Description(
    modifier: Modifier = Modifier,
    description: String
) {
    val paragraphs = description.toParagraphText()

    TextParagraphs(
        modifier = modifier
            .fillMaxWidth()
            .clip(
                RoundedCornerShape(
                    topStart = 20.dp,
                    topEnd = 20.dp,
                    bottomStart = 0.dp,
                    bottomEnd = 0.dp
                )
            )
            .background(Color.White)
            .padding(
                start = 10.dp,
                top = 15.dp,
                end = 10.dp,
                bottom = 7.5.dp
            ),
        paragraphTexts = paragraphs
    )
}

@Preview
@Composable
fun DescriptionPreview() {
    Description(
        description = "description description description description description description " +
                "description description description description description description " +
                "description description description description description description\n" +
                "description description description description description description " +
                "description description description description description description " +
                "description description description description description description "
    )
}