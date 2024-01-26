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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.serj.recommend.android.R
import com.serj.recommend.android.services.model.Response
import com.serj.recommend.android.ui.styles.Black
import com.serj.recommend.android.ui.styles.primary
import com.serj.recommend.android.ui.styles.Red
import com.serj.recommend.android.ui.styles.White

@SuppressLint("UnusedTransitionTargetStateParameter")
@Composable
fun InteractionPanel(
    modifier: Modifier = Modifier,
    isLiked: Boolean,
    recommendationId: String?,
    currentUserUid: String?,
    onLikeClick: (Boolean, String, String) -> Response<Boolean>,
) {
    val isCurrentlyLiked = remember { mutableStateOf(isLiked) }
    val isCommented = remember { mutableStateOf(false) }
    val isReposted = remember { mutableStateOf(false) }

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
            checked = isCommented.value,
            onCheckedChange = {
                isCommented.value = !isCommented.value
            }
        ) {
            val transition = updateTransition(isCommented.value, label = "CommentTransition")
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
            checked = isReposted.value,
            onCheckedChange = {
                isReposted.value = !isReposted.value
            }
        ) {
            val transition = updateTransition(isReposted.value, label = "repostTransition")
            val tint by transition.animateColor(label = "repostTint") { isLiked ->
                if (isReposted.value) primary else Black
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
}

@Preview
@Composable
fun InteractionPanelPreview() {
    InteractionPanel(
        modifier = Modifier.background(White),
        isLiked = true,
        recommendationId = "",
        currentUserUid = "",
        onLikeClick = { b: Boolean, s1: String, s2: String -> Response.Success(true) }
    )
}