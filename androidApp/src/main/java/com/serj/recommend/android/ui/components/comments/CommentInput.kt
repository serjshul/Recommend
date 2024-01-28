package com.serj.recommend.android.ui.components.comments

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.firebase.storage.StorageReference
import com.serj.recommend.android.R
import com.serj.recommend.android.ui.components.media.CustomGlideImage

@Composable
fun CommentInput(
    modifier: Modifier = Modifier,
    photoReference: StorageReference?,
    onSendCommentClick: () -> Unit
) {
    var comment by remember { mutableStateOf("") }
    val enableToSendComment = remember { mutableStateOf(false) }

    Row(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .weight(1.2f)
                .padding(top = 8.dp)
        ) {
            if (photoReference != null) {
                CustomGlideImage(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .align(Alignment.Center),
                    reference = photoReference
                )
            } else {
                Image(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .align(Alignment.Center),
                    painter = painterResource(id = R.drawable.no_user_photo),
                    contentDescription = "No user photo"
                )
            }
        }

        TextField(
            modifier = Modifier
                .weight(6f)
                .align(Alignment.CenterVertically),
            value = comment,
            onValueChange = {
                comment = it
                enableToSendComment.value = it != ""
            },
            placeholder = { Text(text = "Add a comment...") },
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White,
                disabledContainerColor = Color.White,
                unfocusedPlaceholderColor = Color.Gray,
                focusedPlaceholderColor = Color.Gray,
                disabledPlaceholderColor = Color.Gray,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            )
        )

        IconToggleButton(
            modifier = Modifier
                .weight(1f)
                .padding(top = 3.dp),
            checked = enableToSendComment.value,
            onCheckedChange = {
                enableToSendComment.value = !enableToSendComment.value
                comment = ""
            }
        ) {
            val transition = updateTransition(enableToSendComment.value, label = "SendIconTransition")
            val tint by transition.animateColor(label = "SendIconTint") {
                if (it) Color.Black else Color.Transparent
            }
            val size by transition.animateDp(
                    transitionSpec = {
                        keyframes {
                            durationMillis = 250
                            24.dp at 0 with LinearOutSlowInEasing
                            27.dp at 15 with FastOutLinearInEasing
                            30.dp at 75
                            27.dp at 150
                        }
                    },
            label = "CommentSize"
            ) { if (it) 24.dp else 24.dp }

            Icon(
                imageVector = Icons.Filled.ArrowForward,
                contentDescription = "Send the comment",
                tint = tint,
                modifier = Modifier
                    .size(size)
            )
        }
    }
}

@Preview
@Composable
fun CommentInputPreview() {
    Box(
        modifier = Modifier
            .background(Color.White)
    ) {
        CommentInput(
            photoReference = null,
            onSendCommentClick = { }
        )
    }
}