package com.serj.recommend.android.ui.components.social

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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.IconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.serj.recommend.android.R
import com.serj.recommend.android.ui.styles.Black
import com.serj.recommend.android.ui.styles.White

@SuppressLint("UnusedTransitionTargetStateParameter")
@Composable
fun InteractionPanel(
    modifier: Modifier = Modifier,
    tintData: Color = Black
) {
    val isLiked = remember { mutableStateOf(false) }
    val isCommented = remember { mutableStateOf(false) }

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconToggleButton(
            checked = isLiked.value,
            onCheckedChange = {
                isLiked.value = !isLiked.value
            }
        ) {
            val transition = updateTransition(isLiked.value, label = "likeTransition")
            val tint by transition.animateColor(label = "likeTint") { isLiked ->
                if (isLiked) Color.Red else Color.Black
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
                    id = if (isLiked.value) R.drawable.icon_like_1
                    else R.drawable.icon_like_bordered_1
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
                ImageVector.vectorResource(id = R.drawable.icon_comment_1),
                contentDescription = "Comment",
                tint = Black,
                modifier = Modifier.size(size)
            )
        }

        IconButton(
            modifier = Modifier
                .padding(top = 3.dp)
                .size(34.dp),
            onClick = { }
        ) {
            Icon(
                ImageVector.vectorResource(id = R.drawable.icon_repost_1),
                contentDescription = "Repost",
                tint = tintData
            )
        }
    }
}

@Preview
@Composable
fun InteractionPanelPreview() {
    InteractionPanel(
        modifier = Modifier
            .background(White)
    )
}