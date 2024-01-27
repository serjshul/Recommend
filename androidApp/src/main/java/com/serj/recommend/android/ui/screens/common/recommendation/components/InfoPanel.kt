package com.serj.recommend.android.ui.screens.common.recommendation.components

import androidx.compose.foundation.background
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

@Composable
fun InfoPanel(
    modifier: Modifier = Modifier,
    author: String,
    date: String
) {
    Text(
        modifier = modifier,
        text = buildAnnotatedString {
            append("by ")
            withStyle(style = SpanStyle(color = Color.Black)) {
                append(author)
            }
            append(" on $date")
        },
        color = Color.Gray,
        fontSize = 14.sp
    )
}

@Preview
@Composable
fun InfoPanelPreview() {
    InfoPanel(
        modifier = Modifier.background(White),
        author = "@serjshul",
        date = "1 may 12:52"
    )
}