package com.serj.recommend.android.ui.components.comments

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.serj.recommend.android.model.subcollections.RecommendationComment
import com.serj.recommend.android.model.items.UserItem
import com.serj.recommend.android.ui.components.comments.items.CommentShortItem

@Composable
fun CommentsShortList(
    modifier: Modifier = Modifier,
    comments: List<RecommendationComment>
) {
    Column (
        modifier = modifier.fillMaxWidth(),
    ) {
        if (comments.size <= 3) {
            for (comment in comments) {
                if (comment.userItem?.nickname != null && comment.text != null) {
                    CommentShortItem(
                        nickname = comment.userItem?.nickname!!,
                        text = comment.text
                    )
                }
            }
        } else {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 1.dp),
                text = "View all ${comments.size} comments",
                overflow = TextOverflow.Ellipsis,
                lineHeight = 1.2.em,
                color = Gray,
                fontSize = 14.sp,
                maxLines = 1
            )
        }
    }
}

@Preview
@Composable
fun CommentsShortListPreview() {
    val comments = listOf(
        RecommendationComment(
            text = "preview  preview preview preview preview preview preview",
            userItem = UserItem(nickname = "preview")
        ),
        RecommendationComment(
            text = "preview  preview preview preview preview preview preview",
            userItem = UserItem(nickname = "preview")
        ),
        RecommendationComment(
            text = "preview  preview preview preview preview preview preview",
            userItem = UserItem(nickname = "preview")
        ),
        RecommendationComment(
            text = "preview  preview preview preview preview preview preview",
            userItem = UserItem(nickname = "preview")
        )
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        CommentsShortList(
            comments = comments
        )
    }
}