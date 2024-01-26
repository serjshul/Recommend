package com.serj.recommend.android.ui.screens.common.recommendation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.serj.recommend.android.common.ext.screenPaddingsInner
import com.serj.recommend.android.common.ext.screenPaddingsOuter
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
        modifier = modifier
            .clip(RoundedCornerShape(10.dp, 10.dp, 0.dp, 0.dp))
            .background(White)
            .screenPaddingsInner()
            .screenPaddingsOuter(),
        paragraphTexts = paragraphs
    )
}

@Preview
@Composable
fun DescriptionPreview() {
    Description(
        modifier = Modifier,
        description = "In a world divided by factions based on virtues, Tris learns she's " +
                "Divergent and won't fit in. When she discovers a plot to destroy Divergents, " +
                "Tris and the mysterious Four must find out what makes Divergents dangerous " +
                "before it's too late."
    )
}