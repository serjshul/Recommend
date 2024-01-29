package com.serj.recommend.android.ui.components.comments

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.serj.recommend.android.model.Comment
import com.serj.recommend.android.services.model.Response
import com.serj.recommend.android.ui.components.comments.items.CommentItem

@Composable
fun CommentsBottomSheet(
    modifier: Modifier = Modifier,
    comments: List<Comment>,
    commentInput: String,
    currentRecommendationId: String,
    onCommentInputValueChange: (String) -> Unit,
    onUploadCommentClick: (String) -> Unit
) {
    Column (
        modifier = modifier
            .background(Color.White)
    ) {
        Text(
            modifier = Modifier
                .padding(bottom = 10.dp)
                .fillMaxWidth(),
            text = "Comments",
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            fontSize = 14.sp
        )

        Divider(
            modifier = Modifier.fillMaxWidth()
        )

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
                    text = "There is no comments\nBe the first who comment the recommendation",
                    textAlign = TextAlign.Center,
                    color = Color.Gray,
                    fontSize = 14.sp
                )
            }
        }

        CommentInput(
            photoReference = null,
            commentInput = commentInput,
            currentRecommendationId = currentRecommendationId,
            onCommentInputValueChange = onCommentInputValueChange,
            onUploadCommentClick = onUploadCommentClick
        )
    }
}

@Preview
@Composable
fun CommentsBottomSheetPreview() {
    val comments = listOf<Comment>()

    CommentsBottomSheet(
        comments = comments,
        commentInput = "",
        currentRecommendationId = "",
        onCommentInputValueChange = { },
        onUploadCommentClick = { }
    )
}
