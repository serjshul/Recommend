package com.serj.recommend.android.ui.components.comments.items

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.google.firebase.storage.StorageReference
import com.serj.recommend.android.R
import com.serj.recommend.android.common.getCreatedTime
import com.serj.recommend.android.model.Comment
import com.serj.recommend.android.services.model.Response
import com.serj.recommend.android.ui.components.comments.CommentDropdownMenu
import com.serj.recommend.android.ui.components.media.CustomGlideImage
import com.serj.recommend.android.ui.styles.Black
import com.serj.recommend.android.ui.styles.Red
import java.util.Date

@Composable
fun CommentItem(
    modifier: Modifier = Modifier,
    comment: Comment,
    nickname: String,
    photoReference: StorageReference?,
    text: String,
    date: Date,
    isLiked: Boolean,
    likedBy: List<String>,
    isDropdownMenuExpanded: Boolean,
    onLikeClick: (Boolean, String, String) -> Response<Boolean>,
    onCommentClick: (Comment) -> Unit,
    onCommentDismissRequest: (Comment) -> Unit,
    onDeleteCommentClick: (Comment) -> Unit,
) {
    val createdTime = getCreatedTime(date)

    val isCurrentlyLiked = remember { mutableStateOf(isLiked) }

    Row(
        modifier = modifier
            .padding(10.dp, 5.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.Top
    ) {
        Box(
            modifier = Modifier
                .size(50.dp)
        ) {
            CustomGlideImage(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape),
                reference = photoReference
            )
        }

        Column(
            modifier = modifier.weight(1f)
        ) {
            Text(
                modifier = modifier
                    .padding(bottom = 3.dp)
                    .clickable { onCommentClick(comment) },
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append(nickname)
                    }
                    append(" $text")
                },
                fontSize = 14.sp,
                lineHeight = 1.2.em
            )

            Row {
                Text(
                    modifier = Modifier.padding(end = 10.dp),
                    text = createdTime,
                    color = Color.Gray,
                    fontSize = 12.sp,
                    lineHeight = 1.2.em
                )

                Text(
                    text = "Reply",
                    color = Color.Gray,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    lineHeight = 1.2.em
                )
            }

            CommentDropdownMenu(
                comment = comment,
                expanded = isDropdownMenuExpanded,
                onDeleteCommentClick = onDeleteCommentClick,
                onCommentDismissRequest = onCommentDismissRequest
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            IconToggleButton(
                modifier = Modifier.size(30.dp),
                checked = isCurrentlyLiked.value,
                onCheckedChange = {
                    // TODO: add onLikeClick function
                    isCurrentlyLiked.value = !isCurrentlyLiked.value
                }
            ) {
                val transition = updateTransition(isCurrentlyLiked.value, label = "likeTransition")
                val tint by transition.animateColor(label = "likeTint") { isLiked ->
                    if (isLiked) Red else Black
                }
                val size by transition.animateDp(
                    transitionSpec = {
                        if (false isTransitioningTo true) {
                            keyframes {
                                durationMillis = 250
                                17.dp at 0 with LinearOutSlowInEasing
                                19.dp at 15 with FastOutLinearInEasing
                                21.dp at 75
                                19.dp at 150
                            }
                        } else {
                            spring(stiffness = Spring.StiffnessVeryLow)
                        }
                    },
                    label = "likeSize"
                ) { if (it) 17.dp else 17.dp }

                Icon(
                    ImageVector.vectorResource(
                        id = if (isCurrentlyLiked.value) R.drawable.interaction_like_filled
                        else R.drawable.interaction_like_bordered
                    ),
                    contentDescription = "like",
                    tint = tint,
                    modifier = Modifier.size(size)
                )
            }

            if (likedBy.isNotEmpty()) {
                Text(
                    text = likedBy.size.toString(),
                    color = Color.Gray,
                    fontSize = 10.sp,
                    lineHeight = 1.2.em
                )
            }
        }
    }
}

@Preview
@Composable
fun CommentFullItemPreview() {
    CommentItem(
        modifier = Modifier.background(Color.White),
        comment = Comment(),
        nickname = "succession",
        photoReference = null,
        isLiked = false,
        text = "The saga about ",
        date = Date(Date().time - 22 * 60 * 60 * 1000L),
        likedBy = listOf("", "", "", "", "", "", "", "", "", "", "", "", "", "", ""),
        isDropdownMenuExpanded = false,
        onLikeClick = { _: Boolean, _: String, _: String -> Response.Success(true) },
        onCommentClick = { _: Comment -> },
        onCommentDismissRequest = { },
        onDeleteCommentClick = { }
    )
}