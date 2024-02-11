package com.serj.recommend.android.ui.components.comments

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
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
import com.serj.recommend.android.model.collections.Comment
import com.serj.recommend.android.services.model.Response
import com.serj.recommend.android.ui.components.comments.items.CommentItem

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
    Scaffold(
        topBar = {
            Surface(
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
        },
        bottomBar = {
            CommentInput(
                modifier = Modifier.background(Color.White),
                currentUserPhotoReference = currentUserPhotoReference,
                commentInput = commentInput,
                onCommentInputValueChange = onCommentInputValueChange,
                onUploadCommentClick = onUploadCommentClick
            )
        },
        modifier = modifier,
        containerColor = Color.White
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
        ) {
            item {
                Spacer(modifier = Modifier.size(10.dp))
            }
            if (comments.isNotEmpty()) {
                items(comments.keys.toList(), key = { it.id!! }) {
                    if (it.userItem != null && it.userItem!!.nickname != null &&
                        it.userItem!!.photoReference != null && it.text != null && it.date != null
                    ) {
                        CommentItem(
                            modifier = Modifier,
                            comment = it,
                            nickname = it.userItem!!.nickname!!,
                            photoReference = it.userItem!!.photoReference,
                            text = it.text,
                            date = it.date,
                            isLiked = false,
                            likedBy = it.likedBy,
                            isDropdownMenuExpanded = comments[it]!!,
                            onLikeClick = { _: Boolean, _: String, _: String -> Response.Success(true) },
                            onCommentClick = onCommentClick,
                            onCommentDismissRequest = onCommentDismissRequest,
                            onDeleteCommentClick = onDeleteCommentClick
                        )
                    }
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
