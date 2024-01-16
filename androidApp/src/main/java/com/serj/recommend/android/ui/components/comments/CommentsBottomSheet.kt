package com.serj.recommend.android.ui.components.comments

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommentsBottomSheet(
    modifier: Modifier = Modifier,
    comments: List<HashMap<String, String>>
) {
    Column (
        modifier = modifier
    ) {
        for (comment in comments) {
            /*
            CommentFullItem(
                modifier = Modifier,
                user = comment["userId"] ?: "",
                comment = comment["text"] ?: "",
            )

             */
        }
    }
}