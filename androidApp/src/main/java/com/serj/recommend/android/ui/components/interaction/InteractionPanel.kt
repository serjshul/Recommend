package com.serj.recommend.android.ui.components.interaction

import android.annotation.SuppressLint
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.serj.recommend.android.R
import com.serj.recommend.android.model.Comment
import com.serj.recommend.android.services.model.Response
import com.serj.recommend.android.ui.components.comments.CommentsBottomSheet
import com.serj.recommend.android.ui.styles.Black
import com.serj.recommend.android.ui.styles.KiriumeRed
import com.serj.recommend.android.ui.styles.Red
import com.serj.recommend.android.ui.styles.White

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedTransitionTargetStateParameter")
@Composable
fun InteractionPanel(
    modifier: Modifier = Modifier,
    isLiked: Boolean,
    isCommentClicked: MutableState<Boolean>,
    comments: List<Comment>,
    recommendationId: String?,
    currentUserUid: String?,
    onLikeClick: (Boolean, String, String) -> Response<Boolean>
) {
    val isCurrentlyLiked = remember { mutableStateOf(isLiked) }
    val isCurrentlyReposted = remember { mutableStateOf(false) }

    val isCommentCurrentlyClicked = remember { mutableStateOf(false) }
    val commentSheetState = rememberModalBottomSheetState()
    var showCommentsBottomSheet by remember { isCommentClicked }

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        IconToggleButton(
            checked = isCurrentlyLiked.value,
            onCheckedChange = {
                if (currentUserUid != null && recommendationId != null) {
                    onLikeClick(isCurrentlyLiked.value, currentUserUid, recommendationId)
                }
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
                            29.dp at 0 with LinearOutSlowInEasing
                            32.dp at 15 with FastOutLinearInEasing
                            35.dp at 75
                            32.dp at 150
                        }
                    } else {
                        spring(stiffness = Spring.StiffnessVeryLow)
                    }
                },
                label = "likeSize"
            ) { 29.dp }

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

        IconToggleButton(
            checked = isCommentCurrentlyClicked.value,
            onCheckedChange = {
                isCommentCurrentlyClicked.value = !isCommentCurrentlyClicked.value
                showCommentsBottomSheet = true
            }
        ) {
            val transition = updateTransition(isCommentCurrentlyClicked.value, label = "CommentTransition")
            val size by transition.animateDp(
                transitionSpec = {
                    keyframes {
                        durationMillis = 250
                        29.dp at 0 with LinearOutSlowInEasing
                        32.dp at 15 with FastOutLinearInEasing
                        35.dp at 75
                        32.dp at 150
                    }
                },
                label = "CommentSize"
            ) { 29.dp }

            Icon(
                ImageVector.vectorResource(id = R.drawable.interaction_comment),
                contentDescription = "Comment",
                tint = Black,
                modifier = Modifier.size(size)
            )
        }

        IconToggleButton(
            checked = isCurrentlyReposted.value,
            onCheckedChange = {
                isCurrentlyReposted.value = !isCurrentlyReposted.value
            }
        ) {
            val transition = updateTransition(isCurrentlyReposted.value, label = "repostTransition")
            val tint by transition.animateColor(label = "repostTint") { isLiked ->
                if (isCurrentlyReposted.value) KiriumeRed else Black
            }
            val size by transition.animateDp(
                transitionSpec = {
                    if (false isTransitioningTo true) {
                        keyframes {
                            durationMillis = 250
                            29.dp at 0 with LinearOutSlowInEasing
                            32.dp at 15 with FastOutLinearInEasing
                            35.dp at 75
                            32.dp at 150
                        }
                    } else {
                        spring(stiffness = Spring.StiffnessVeryLow)
                    }
                },
                label = "repostSize"
            ) { 29.dp }

            Icon(
                ImageVector.vectorResource(id = R.drawable.interaction_repost),
                contentDescription = "Repost",
                tint = tint,
                modifier = Modifier.size(size)
            )
        }
    }

    if (showCommentsBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { showCommentsBottomSheet = false },
            sheetState = commentSheetState,
            containerColor = Color.White
        ) {
            CommentsBottomSheet(
                comments = comments
            )
        }
    }
}

@Preview
@Composable
fun InteractionPanelPreview() {
    val isCommentClicked = remember { mutableStateOf(false) }

    InteractionPanel(
        modifier = Modifier.background(White),
        isLiked = true,
        isCommentClicked = isCommentClicked,
        comments = arrayListOf(),
        recommendationId = "",
        currentUserUid = "",
        onLikeClick = { _: Boolean, _: String, _: String -> Response.Success(true) }
    )
}