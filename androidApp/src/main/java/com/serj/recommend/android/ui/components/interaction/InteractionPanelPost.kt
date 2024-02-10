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
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.serj.recommend.android.R
import com.serj.recommend.android.model.Comment
import com.serj.recommend.android.services.model.Response
import com.serj.recommend.android.ui.styles.primary

@SuppressLint("UnusedTransitionTargetStateParameter")
@Composable
fun InteractionPanelPost(
    modifier: Modifier = Modifier,
    isLiked: Boolean,
    isCommentClicked: MutableState<Boolean>,
    comments: List<Comment>,
    recommendationId: String?,
    currentUserUid: String?,
    onLikeClick: (Boolean, String, String) -> Response<Boolean>,
    onCommentClick: (String, List<Comment>) -> Unit
) {
    val isCurrentlyLiked = remember { mutableStateOf(isLiked) }
    val isCurrentlyReposted = remember { mutableStateOf(false) }
    val isCommentCurrentlyClicked = remember { mutableStateOf(false) }
    val isCurrentlySaved = remember { mutableStateOf(false) }

    Box(
        modifier = modifier.fillMaxWidth()
    ) {
        IconToggleButton(
            modifier = Modifier.align(Alignment.CenterStart),
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
                    id =
                        if (isCurrentlyLiked.value)
                            R.drawable.interaction_like_filled
                        else
                            R.drawable.interaction_like_bordered
                ),
                contentDescription = "like",
                tint = tint,
                modifier = Modifier.size(size)
            )
        }

        IconToggleButton(
            modifier = Modifier
                .padding(start = 45.dp)
                .align(Alignment.CenterStart),
            checked = isCommentCurrentlyClicked.value,
            onCheckedChange = {
                isCommentCurrentlyClicked.value = !isCommentCurrentlyClicked.value
                if (recommendationId != null) {
                    onCommentClick(recommendationId, comments)
                }
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
            modifier = Modifier
                .padding(start = 90.dp)
                .align(Alignment.CenterStart),
            checked = isCurrentlyReposted.value,
            onCheckedChange = {
                isCurrentlyReposted.value = !isCurrentlyReposted.value
            }
        ) {
            val transition = updateTransition(isCurrentlyReposted.value, label = "repostTransition")
            val tint by transition.animateColor(label = "repostTint") { isReposted ->
                if (isReposted) primary else Black
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

        IconToggleButton(
            modifier = Modifier
                .align(Alignment.CenterEnd),
            checked = isCurrentlySaved.value,
            onCheckedChange = {
                isCurrentlySaved.value = !isCurrentlySaved.value
            }
        ) {
            val transition = updateTransition(isCurrentlySaved.value, label = "saveTransition")
            val tint by transition.animateColor(label = "saveTint") { isSaved ->
                if (isSaved) primary else Black
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
                label = "saveSize"
            ) { 29.dp }

            Icon(
                ImageVector.vectorResource(
                    id =
                        if (isCurrentlySaved.value)
                            R.drawable.icon_saved
                        else
                            R.drawable.icon_unsaved
                ),
                contentDescription = "Save",
                tint = tint,
                modifier = Modifier.size(size)
            )
        }
    }
}

@Preview
@Composable
fun InteractionPanelPreview() {
    val isCommentClicked = remember { mutableStateOf(false) }

    InteractionPanelPost(
        modifier = Modifier.background(White),
        isLiked = true,
        isCommentClicked = isCommentClicked,
        comments = arrayListOf(),
        recommendationId = "",
        currentUserUid = "",
        onLikeClick = { _: Boolean, _: String, _: String -> Response.Success(true) },
        onCommentClick = { _: String, _: List<Comment> -> }
    )
}