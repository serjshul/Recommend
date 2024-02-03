package com.serj.recommend.android.ui.components.recommendationPreviews

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.google.firebase.storage.StorageReference
import com.serj.recommend.android.common.getCreatedTime
import com.serj.recommend.android.model.Comment
import com.serj.recommend.android.model.Recommendation
import com.serj.recommend.android.model.items.UserItem
import com.serj.recommend.android.services.model.Response
import com.serj.recommend.android.ui.components.comments.CommentsShortList
import com.serj.recommend.android.ui.components.interaction.InteractionPanelPost
import com.serj.recommend.android.ui.components.media.CustomGlideImage
import com.serj.recommend.android.ui.components.media.CustomGlideImageShaded
import com.serj.recommend.android.ui.components.media.GlideUserImage
import com.serj.recommend.android.ui.styles.primary
import java.util.Date

@Composable
fun RecommendationItem(
    modifier: Modifier = Modifier,
    user: UserItem?,
    date: Date?,
    description: String?,
    coverType: String?,
    coverReference: StorageReference?,
    backgroundImageReference: StorageReference?,
    backgroundVideoReference: StorageReference?,
    title: String?,
    creator: String?,
    type: String?,
    tags: List<String>,
    isLiked: Boolean,
    comments: List<Comment>,
    recommendationId: String?,
    currentUserUid: String?,
    openScreen: (String) -> Unit,
    onLikeClick: (Boolean, String, String) -> Response<Boolean>,
    onCommentIconClick: (String, List<Comment>) -> Unit,
    onRecommendationClick: ((String) -> Unit, Recommendation) -> Unit
) {
    val isCommentClicked = remember { mutableStateOf(false) }
    var isDropDownExpanded by remember { mutableStateOf(false) }

    if (user?.nickname != null && date != null && description != null &&
        title != null && creator != null) {
        val createdTime = getCreatedTime(date)

        Box(
            modifier = modifier
                .padding(0.dp, 5.dp)
                .fillMaxWidth()
        ) {
            if (backgroundVideoReference != null) {
                // TODO: add video glide item
            } else if (backgroundImageReference != null) {
                CustomGlideImageShaded(
                    modifier = Modifier
                        .padding(top = 55.dp)
                        .fillMaxWidth()
                        .height(200.dp),
                    reference = backgroundImageReference
                )
            }

            Row(
                modifier = Modifier
                    .padding(10.dp, 0.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                GlideUserImage(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape),
                    reference = user.photoReference
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier
                            .weight(8f)
                            .padding(start = 15.dp),
                        text = user.nickname,
                        color = Black,
                        maxLines = 1,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        overflow = TextOverflow.Ellipsis
                    )

                    Text(
                        modifier = Modifier.weight(1f),
                        text = createdTime,
                        color = Black,
                        fontSize = 14.sp
                    )

                    Box(
                        modifier = Modifier
                            .weight(1f)
                    ) {
                        IconButton(
                            onClick = { isDropDownExpanded = true }
                        ) {
                            Icon(
                                Icons.Default.MoreVert,
                                contentDescription = "Show the menu",
                                tint = Black
                            )
                        }

                        DropdownMenu(
                            expanded = isDropDownExpanded,
                            onDismissRequest = { isDropDownExpanded = false }
                        ) {
                            Text(
                                modifier = Modifier
                                    .padding(10.dp)
                                    .clickable(onClick = { }),
                                text = "Add to bookmarks",
                                fontSize = 14.sp
                            )
                        }
                    }
                }
            }

            Box(
                modifier = Modifier
                    .padding(
                        start = 10.dp,
                        end = 10.dp,
                        top =
                            if (backgroundImageReference != null || backgroundVideoReference != null)
                                when (coverType) {
                                    ItemsShapes.horizontal.name -> 220.dp
                                    ItemsShapes.square.name -> 220.dp
                                    else -> 170.dp
                                }
                            else
                                55.dp
                    )
            ) {
                Row {
                    when (coverType) {
                        ItemsShapes.square.name -> {
                            CustomGlideImage(
                                modifier = Modifier
                                    .size(100.dp)
                                    .clip(RoundedCornerShape(5.dp))
                                    .clickable {
                                        if (recommendationId != null) {
                                            onRecommendationClick(
                                                openScreen,
                                                Recommendation(id = recommendationId)
                                            )
                                        }
                                    },
                                reference = coverReference
                            )
                        }
                        ItemsShapes.vertical.name -> {
                            CustomGlideImage(
                                modifier = Modifier
                                    .height(150.dp)
                                    .width(110.dp)
                                    .clip(RoundedCornerShape(5.dp))
                                    .clickable {
                                        if (recommendationId != null) {
                                            onRecommendationClick(
                                                openScreen,
                                                Recommendation(id = recommendationId)
                                            )
                                        }
                                    },
                                reference = coverReference
                            )
                        }
                        else -> {
                            CustomGlideImage(
                                modifier = Modifier
                                    .height(100.dp)
                                    .width(165.dp)
                                    .clip(RoundedCornerShape(5.dp))
                                    .clickable {
                                        if (recommendationId != null) {
                                            onRecommendationClick(
                                                openScreen,
                                                Recommendation(id = recommendationId)
                                            )
                                        }
                                    },
                                reference = coverReference
                            )
                        }
                    }

                    Column(
                        modifier = Modifier
                            .padding(start = 10.dp)
                            .align(Alignment.Bottom)
                    ) {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 2.dp),
                            text = "$type   /   ${tags.joinToString(separator = " & ")}",
                            color = primary,
                            maxLines = 1,
                            fontSize = 12.sp,
                            lineHeight = 1.2.em,
                            fontWeight = FontWeight.Bold,
                            overflow = TextOverflow.Ellipsis,
                            textAlign = TextAlign.Start
                        )

                        Text(
                            modifier = Modifier
                                .clickable {
                                    if (recommendationId != null) {
                                        onRecommendationClick(
                                            openScreen,
                                            Recommendation(id = recommendationId)
                                        )
                                    }
                                },
                            text = title,
                            color = Black,
                            maxLines = 1,
                            fontSize = 14.sp,
                            lineHeight = 1.2.em,
                            fontWeight = FontWeight.Bold,
                            overflow = TextOverflow.Ellipsis
                        )

                        Text(
                            text = creator,
                            color = Black,
                            maxLines = 1,
                            fontSize = 14.sp,
                            lineHeight = 1.2.em,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top =
                            if (backgroundImageReference != null || backgroundVideoReference != null)
                                when (coverType) {
                                    ItemsShapes.horizontal.name -> 330.dp
                                    ItemsShapes.square.name -> 330.dp
                                    else -> 330.dp
                                }
                            else
                                when (coverType) {
                                    ItemsShapes.horizontal.name -> 165.dp
                                    ItemsShapes.square.name -> 165.dp
                                    else -> 215.dp
                                }
                    )
            ) {
                Text(
                    modifier = Modifier
                        .padding(start = 10.dp, end = 10.dp, bottom = 5.dp)
                        .fillMaxWidth(),
                    text = description,
                    color = Black,
                    fontSize = 14.sp,
                    lineHeight = 1.4.em
                )

                if (comments.isNotEmpty()) {
                    CommentsShortList(
                        modifier = Modifier
                            .padding(10.dp, 0.dp)
                            .fillMaxWidth()
                            .clickable { isCommentClicked.value = true },
                        comments = comments
                    )
                }

                InteractionPanelPost(
                    isLiked = isLiked,
                    isCommentClicked = isCommentClicked,
                    comments = comments,
                    recommendationId = recommendationId,
                    currentUserUid = currentUserUid,
                    onLikeClick = onLikeClick,
                    onCommentClick = onCommentIconClick
                )
            }
        }
    }
}

@Preview
@Composable
fun PostWithBackgroundPreview() {
    val comments = listOf(
        Comment(
            text = "preview  preview preview preview preview preview preview",
            userItem = UserItem(nickname = "preview")
        ),
        Comment(
            text = "preview  preview preview preview preview preview preview",
            userItem = UserItem(nickname = "preview")
        ),
        Comment(
            text = "preview  preview preview preview preview preview preview",
            userItem = UserItem(nickname = "preview")
        ),
        Comment(
            text = "preview  preview preview preview preview preview preview",
            userItem = UserItem(nickname = "preview")
        )
    )

    RecommendationItem(
        modifier = Modifier.background(Color.White),
        user = UserItem(
            nickname = "serjshul"
        ),
        date = Date(0),
        description = "text text text text text text text text text text text text text text text " +
                "text text text text text text text text text text text text text text text " +
                "text text text text text text text text text text text text text text",
        coverReference = null,
        backgroundImageReference = null,
        backgroundVideoReference = null,
        title = "title title title title title title title title",
        creator = "creator creator creator creator creator",
        type = "type",
        tags = listOf("tag", "tag", "tag", "tag"),
        coverType = ItemsShapes.vertical.name,
        comments = comments,
        isLiked = true,
        recommendationId = "",
        currentUserUid = "",
        openScreen = { },
        onLikeClick = { _: Boolean, _: String, _: String -> Response.Success(true) },
        onCommentIconClick = { _: String, _: List<Comment> -> },
        onRecommendationClick = { _: (String) -> Unit, _: Recommendation -> }
    )
}