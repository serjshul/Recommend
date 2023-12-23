package com.serj.recommend.android.ui.screens.common.recommendation.components

import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.serj.recommend.android.common.ext.toParagraphText
import com.serj.recommend.android.ui.components.text.TextParagraphs
import com.serj.recommend.android.ui.styles.White

@Composable
fun Description(
    modifier: Modifier = Modifier,
    description: String
) {
    val paragraphs = description.toParagraphText()

    TextParagraphs(
        modifier = modifier,
        paragraphTexts = paragraphs
    )
}

@Preview
@Composable
fun DescriptionPreview() {
    Description(
        modifier = Modifier.background(White),
        description = "In a world divided by factions based on virtues, Tris learns she's " +
                "Divergent and won't fit in. When she discovers a plot to destroy Divergents, " +
                "Tris and the mysterious Four must find out what makes Divergents dangerous " +
                "before it's too late."
    )
}