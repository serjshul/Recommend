package com.serj.recommend.android.ui.components.comments.items

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp

@Composable
fun CommentShortItem(
    modifier: Modifier = Modifier,
    nickname: String,
    text: String
) {
    Text(
        modifier = modifier
            .fillMaxWidth()
            .padding(0.dp, 1.dp),
        text = buildAnnotatedString {
            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                append(nickname)
            }
            append(" $text")
        },
        fontSize = 14.sp,
        maxLines = 1,
        lineHeight = 1.2.em,
        overflow = TextOverflow.Ellipsis
    )
}

@Preview
@Composable
fun CommentShortItemPreview() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        for (i in 0..2) {
            CommentShortItem(
                nickname = "succession",
                text = "The saga about a back-stabbing media dynasty won best drama series, " +
                        "best actor, best actress and best supporting actor"
            )
        }
    }
}