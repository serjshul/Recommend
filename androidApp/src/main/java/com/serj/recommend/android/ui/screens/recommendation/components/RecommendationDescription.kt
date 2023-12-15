package com.serj.recommend.android.ui.screens.recommendation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun RecommendationDescription(
    modifier: Modifier = Modifier,
    description: String?
) {
    if (description != null) {
        val paragraphs = getParagraphTexts(description)
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 15.dp, bottom = 25.dp),
        ) {
            Column {
                for (i in paragraphs.indices) {
                    Text(
                        modifier = if (i != paragraphs.size - 1) Modifier.padding(bottom = 12.dp)
                        else Modifier,
                        text = paragraphs[i],
                        color = Color.Black,
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}