package com.serj.recommend.android.ui.components.interaction

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.serj.recommend.android.R
import com.serj.recommend.android.model.collections.Comment
import com.serj.recommend.android.model.items.UserItem
import com.serj.recommend.android.services.model.Response
import com.serj.recommend.android.ui.components.comments.items.CommentRecommendationItem
import com.serj.recommend.android.ui.styles.primary
import java.util.Date

@Composable
fun InteractionPanelRecommendation(
    modifier: Modifier = Modifier,
    color: Color?,
    isLiked: Boolean,
    isReposted: Boolean,
    likedBy: ArrayList<String>,
    comments: List<Comment>,
    repostedBy: ArrayList<String>,
    topLikedComment: Comment?,
    recommendationId: String?,
    authorUserId: String?,
    currentUserid: String?,
    onLikeClick: (Boolean, String, String) -> Response<Boolean>,
    onCommentClick: (List<Comment>) -> Unit,
    onRepostClick: (String, String, Boolean) -> Unit
) {
    val isOwnerView = authorUserId == currentUserid

    val isCurrentlyLiked = remember { mutableStateOf(isLiked) }
    val isCommentCurrentlyClicked = remember { mutableStateOf(false) }
    val isCommented = remember { mutableStateOf(false) }
    val isCurrentlyReposted = remember { mutableStateOf(isReposted) }

    var likesAmount by remember { mutableIntStateOf(likedBy.size) }
    var repostsAmount by remember { mutableIntStateOf(repostedBy.size) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(start = 15.dp, end = 15.dp, top = 5.dp, bottom = 12.5.dp)
    ) {
        if (topLikedComment != null) {
            Column(
                modifier = Modifier
                    .padding(bottom = 10.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .background(color ?: primary)
            ) {
                Text(
                    modifier = Modifier
                        .padding(top = 15.dp, bottom = 7.dp)
                        .align(Alignment.CenterHorizontally),
                    text = "Top liked comment",
                    color = Color.White,
                    fontSize = 14.sp
                )

                if (topLikedComment.userItem?.nickname != null && topLikedComment.text != null && topLikedComment.date != null) {
                    CommentRecommendationItem(
                        modifier = Modifier.padding(start = 5.dp, end = 5.dp, bottom = 10.dp),
                        comment = topLikedComment,
                        nickname = topLikedComment.userItem?.nickname!!,
                        photoReference = topLikedComment.userItem?.photoReference,
                        text = topLikedComment.text,
                        date = topLikedComment.date,
                        onCommentClick = { _: Comment -> }
                    )
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 5.dp, end = 5.dp, bottom = 15.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Row(
                modifier = Modifier
                    .weight(1f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                IconToggleButton(
                    checked = isCurrentlyLiked.value,
                    onCheckedChange = {
                        if (currentUserid != null && recommendationId != null) {
                            onLikeClick(isCurrentlyLiked.value, currentUserid, recommendationId)
                            isCurrentlyLiked.value = !isCurrentlyLiked.value
                            if (isCurrentlyLiked.value) likesAmount++ else likesAmount--
                        }
                    }
                ) {
                    val transition = updateTransition(isCurrentlyLiked.value, label = "likeTransition")
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
                    ) { if (it) 29.dp else 29.dp }

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

                if (!isOwnerView) {
                    Text(
                        modifier = Modifier.width(32.dp),
                        text = if (isCurrentlyLiked.value) "Liked" else "Like",
                        color = if (isCurrentlyLiked.value) Color.Red else Color.Black,
                        textAlign = TextAlign.Start,
                        fontSize = 12.sp
                    )
                }
            }

            Row(
                modifier = Modifier
                    .weight(1f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                IconToggleButton(
                    modifier = Modifier,
                    checked = isCommented.value,
                    onCheckedChange = {
                        isCommentCurrentlyClicked.value = !isCommentCurrentlyClicked.value
                        onCommentClick(comments)
                    }
                ) {
                    val transition = updateTransition(
                        isCommentCurrentlyClicked.value,
                        label = "CommentTransition"
                    )
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
                    ) { if (it) 29.dp else 29.dp }

                    Icon(
                        ImageVector.vectorResource(id = R.drawable.interaction_comment),
                        contentDescription = "Comment",
                        tint = Color.Black,
                        modifier = Modifier.size(size)
                    )
                }

                if (!isOwnerView) {
                    Text(
                        modifier = Modifier.padding(end = 9.dp),
                        text = "Comment",
                        color = Color.Black,
                        textAlign = TextAlign.Center,
                        fontSize = 12.sp
                    )
                }
            }

            Row(
                modifier = Modifier
                    .weight(1f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                IconToggleButton(
                    checked = isCurrentlyReposted.value,
                    onCheckedChange = {
                        if (recommendationId != null && currentUserid != null) {
                            onRepostClick(recommendationId, currentUserid, isCurrentlyReposted.value)
                            isCurrentlyReposted.value = !isCurrentlyReposted.value
                            if (isCurrentlyReposted.value) repostsAmount++ else repostsAmount--
                        }
                    }
                ) {
                    val transition = updateTransition(isCurrentlyReposted.value, label = "repostTransition")
                    val tint by transition.animateColor(label = "repostTint") { isReposted ->
                        if (isReposted)
                            color ?: primary
                        else
                            Color.Black
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
                    ) { if (it) 29.dp else 29.dp }

                    Icon(
                        ImageVector.vectorResource(id = R.drawable.interaction_repost),
                        contentDescription = "Repost",
                        tint = tint,
                        modifier = Modifier.size(size)
                    )
                }

                if (!isOwnerView) {
                    Text(
                        modifier = Modifier.width(57.dp),
                        text = if (isCurrentlyReposted.value) "Reposted" else "Repost",
                        color = if (isCurrentlyReposted.value) color ?: primary else Color.Black,
                        textAlign = TextAlign.Start,
                        fontSize = 12.sp
                    )
                }
            }

            if (isOwnerView) {
                Box(
                    modifier = Modifier
                        .weight(2f)
                ) {
                    OutlinedButton(
                        modifier = Modifier.align(Alignment.Center),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = color ?: primary,
                        ),
                        border = BorderStroke(1.dp, color ?: primary),
                        onClick = { }
                    ) {
                        Text(
                            text = "View insights",
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun InteractionPanelRecommendationPreview() {
    InteractionPanelRecommendation(
        color = null,
        isLiked = false,
        isReposted = false,
        likedBy = arrayListOf("", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""),
        comments = listOf(),
        repostedBy = arrayListOf("", "", "", ""),
        topLikedComment = Comment(
            text = "A note to adults in the audience: “13 Reasons Why” is not Netflix’s next “Stranger Things”.",
            userItem = UserItem(nickname = "serjshul"),
            date = Date()
        ),
        recommendationId = "recommendationId",
        authorUserId = "2131240",
        currentUserid = "2131241",
        onLikeClick = { _: Boolean, _: String, _: String -> Response.Success(true) },
        onCommentClick = { },
        onRepostClick = { _: String, _: String, _: Boolean -> },
    )
}