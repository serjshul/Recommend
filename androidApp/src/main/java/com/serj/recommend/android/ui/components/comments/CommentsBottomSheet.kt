package com.serj.recommend.android.ui.components.comments

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Surface
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
import com.google.firebase.storage.StorageReference
import com.serj.recommend.android.model.subcollections.Comment
import com.serj.recommend.android.services.model.Response
import com.serj.recommend.android.ui.components.comments.items.CommentItem

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CommentsBottomSheet(
    modifier: Modifier = Modifier,
    comments: Map<Comment, Boolean>,
    commentInput: String,
    currentUserPhotoReference: StorageReference?,
    onCommentInputValueChange: (String) -> Unit,
    onUploadCommentClick: () -> Unit,
    onDeleteCommentClick: (Comment) -> Unit,
    onCommentClick: (Comment) -> Unit,
    onCommentDismissRequest: (Comment) -> Unit
) {
    Box(
        modifier = modifier
            .background(Color.White)
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(top = 50.dp, bottom = 80.dp)
                .height(400.dp)
                .fillMaxWidth()
                .align(Alignment.TopCenter)
        ) {
            item {
                Spacer(modifier = Modifier.size(15.dp))
            }
            
            if (comments.isNotEmpty()) {
                items(comments.keys.toList(), key = { it.id!! }) {
                    CommentItem(
                        comment = it,
                        nickname = it.userItem!!.nickname!!,
                        photoReference = it.userItem!!.photoReference,
                        text = it.text!!,
                        date = it.date!!,
                        isLiked = false,
                        likedBy = it.likedBy,
                        isDropdownMenuExpanded = comments[it]!!,
                        onLikeClick = { _: Boolean, _: String, _: String -> Response.Success(true) },
                        onCommentClick = onCommentClick,
                        onCommentDismissRequest = onCommentDismissRequest,
                        onDeleteCommentClick = onDeleteCommentClick
                    )
                }
            } else {
                item {
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
            }
        }

        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter),
            shadowElevation = 4.dp
        ) {
            Box(
                modifier = Modifier
                    .height(50.dp)
                    .fillMaxWidth()
                    .background(Color.White),
            ) {
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = "Comments",
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    fontSize = 14.sp
                )
            }
        }

        CommentInput(
            modifier = Modifier
                .padding(bottom = 10.dp)
                .background(Color.White)
                .align(Alignment.BottomCenter),
            currentUserPhotoReference = currentUserPhotoReference,
            commentInput = commentInput,
            onCommentInputValueChange = onCommentInputValueChange,
            onUploadCommentClick = onUploadCommentClick
        )
    }
}

@Preview
@Composable
fun CommentsBottomSheetPreview() {
    val comments = mapOf<Comment, Boolean>()

    CommentsBottomSheet(
        comments = comments,
        commentInput = "",
        currentUserPhotoReference = null,
        onCommentInputValueChange = { },
        onUploadCommentClick = { },
        onDeleteCommentClick = { },
        onCommentClick = { _: Comment -> },
        onCommentDismissRequest = { }
    )
}
