package com.serj.recommend.android.ui.components.recommendation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp

@Composable
fun CommentShortItem(
    modifier: Modifier = Modifier,
    user: String,
    comment: String
) {
    Text(
        modifier = modifier
            .padding(bottom = 3.dp),
        text = buildAnnotatedString {
            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                append(user)
            }
            append(" $comment")
        },
        fontSize = 14.sp,
        maxLines = 1,
        lineHeight = 1.2.em,
        overflow = TextOverflow.Ellipsis
    )
}