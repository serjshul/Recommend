package com.serj.recommend.android.ui.components.comments

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.serj.recommend.android.model.Comment
import com.serj.recommend.android.services.model.Response

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommentsBottomSheet(
    modifier: Modifier = Modifier,
    comments: List<Comment>
) {
    Column (
        modifier = modifier
    ) {
        if (comments.isNotEmpty()) {
            for (comment in comments) {
                if (comment.userItem != null && comment.userItem!!.nickname != null &&
                    comment.userItem!!.photoReference != null && comment.text != null && comment.date != null
                ) {
                    CommentItem(
                        nickname = comment.userItem!!.nickname!!,
                        photoReference = comment.userItem!!.photoReference,
                        text = comment.text,
                        date = comment.date,
                        isLiked = false,
                        onLikeClick = { _: Boolean, _: String, _: String -> Response.Success(true) }
                    )
                }
            }
        } else {
            Box(
                modifier = Modifier
                    .height(200.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = "Has no comments",
                    color = Color.Gray,
                    fontSize = 14.sp
                )
            }
        }
    }
}