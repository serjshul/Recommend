package com.serj.recommend.android.ui.components.interaction

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
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.serj.recommend.android.R
import com.serj.recommend.android.common.getMonthAndDay
import com.serj.recommend.android.common.getYear
import com.serj.recommend.android.model.Comment
import com.serj.recommend.android.services.model.Response
import com.serj.recommend.android.ui.styles.primary
import com.serj.recommend.android.ui.styles.secondary
import java.util.Date

@Composable
fun InteractionPanelRecommendation(
    modifier: Modifier = Modifier,
    color: Color?,
    isLiked: Boolean,
    isReposted: Boolean,
    likedBy: ArrayList<String>,
    repostedBy: ArrayList<String>,
    comments: ArrayList<Comment>,
    views: Int,
    coverage: Int,
    date: Date,
    recommendationId: String?,
    authorUserId: String?,
    currentUserid: String?,
    onLikeClick: (Boolean, String, String) -> Response<Boolean>,
) {
    val isCurrentlyLiked = remember { mutableStateOf(isLiked) }
    val isCommented = remember { mutableStateOf(false) }
    val isCurrentlyReposted = remember { mutableStateOf(isReposted) }

    val day = getMonthAndDay(date)
    val year = getYear(date)

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(15.dp, 12.5.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp)
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
                        }
                        isCurrentlyLiked.value = !isCurrentlyLiked.value
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

                Text(
                    modifier = Modifier.padding(end = 9.dp),
                    text = likedBy.size.toString(),
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    fontSize = 14.sp
                )
            }

            Spacer(modifier = Modifier.size(10.dp))

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
                    ) { if (it) 29.dp else 29.dp }

                    Icon(
                        ImageVector.vectorResource(id = R.drawable.interaction_comment),
                        contentDescription = "Comment",
                        tint = Color.Black,
                        modifier = Modifier.size(size)
                    )
                }

                Text(
                    modifier = Modifier.padding(end = 9.dp),
                    text = comments.size.toString(),
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    fontSize = 14.sp
                )
            }

            Spacer(modifier = Modifier.size(10.dp))

            Row(
                modifier = Modifier
                    .weight(1f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                IconToggleButton(
                    checked = isCurrentlyReposted.value,
                    onCheckedChange = {
                        isCurrentlyReposted.value = !isCurrentlyReposted.value
                    }
                ) {
                    val transition = updateTransition(isCurrentlyReposted.value, label = "repostTransition")
                    val tint by transition.animateColor(label = "repostTint") { isReposted ->
                        if (isReposted) secondary else Color.Black
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

                Text(
                    modifier = Modifier.padding(end = 9.dp),
                    text = repostedBy.size.toString(),
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    fontSize = 14.sp
                )
            }
        }

        Box(
            modifier = Modifier
                .padding(bottom = 10.dp)
                .fillMaxWidth()
                .height(100.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(color ?: primary)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp)
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
            ) {
                Column(
                    modifier = Modifier
                        .padding(10.dp, 20.dp)
                        .align(Alignment.Center)
                ) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = views.toString(),
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        fontSize = 17.sp
                    )

                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "Views",
                        color = Color.Black,
                        textAlign = TextAlign.Center,
                        fontSize = 14.sp
                    )
                }
            }

            Spacer(modifier = Modifier.size(10.dp))

            if (authorUserId == currentUserid) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                ) {
                    Column(
                        modifier = Modifier
                            .padding(10.dp, 20.dp)
                            .align(Alignment.Center)
                    ) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = coverage.toString(),
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            fontSize = 17.sp
                        )

                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = "coverage",
                            color = Color.Black,
                            textAlign = TextAlign.Center,
                            fontSize = 14.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.size(10.dp))
            }

            Box(
                modifier = Modifier
                    .weight(1f)
            ) {
                Column(
                    modifier = Modifier
                        .padding(10.dp, 20.dp)
                        .align(Alignment.Center)
                ) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = day,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        fontSize = 17.sp
                    )

                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = year,
                        color = Color.Black,
                        textAlign = TextAlign.Center,
                        fontSize = 14.sp
                    )
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
        repostedBy = arrayListOf("", "", "", ""),
        comments = arrayListOf(),
        views = 348,
        coverage = 6542,
        date = Date(),
        recommendationId = "recommendationId",
        authorUserId = "2131241",
        currentUserid = "2131241",
        onLikeClick = { _: Boolean, _: String, _: String -> Response.Success(true) }
    )
}