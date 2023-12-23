package com.serj.recommend.android.ui.components.comments

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun CommentsList(
    modifier: Modifier = Modifier,
    comments: List<HashMap<String, String>>
) {
    Column (
        modifier = modifier.fillMaxWidth(),
    ) {
        for (comment in comments) {
            CommentShortItem(
                user = comment["userId"] ?: "",
                comment = comment["text"] ?: ""
            )
        }
    }
}