package com.serj.recommend.android.ui.components.recommendationPreviews

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
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
import com.serj.recommend.android.ui.components.interaction.InteractionPanel
import com.serj.recommend.android.ui.components.media.CustomGlideImage
import com.serj.recommend.android.ui.components.media.CustomGlideImageShaded
import com.serj.recommend.android.ui.styles.Black
import com.serj.recommend.android.ui.styles.LightGray
import com.serj.recommend.android.ui.styles.White
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
    isLiked: Boolean,
    comments: List<Comment>,
    recommendationId: String?,
    currentUserUid: String?,
    openScreen: (String) -> Unit,
    onLikeClick: (Boolean, String, String) -> Response<Boolean>,
    onRecommendationClick: ((String) -> Unit, Recommendation) -> Unit
) {
    val isCommentClicked = remember { mutableStateOf(false) }
    var isDropDownExpanded by remember { mutableStateOf(false) }

    var backgroundSize by remember { mutableStateOf(IntSize.Zero) }
    var coverSize by remember { mutableStateOf(IntSize.Zero) }

    if (user?.nickname != null && date != null && description != null &&
        title != null && creator != null) {
        val createdTime = getCreatedTime(date)

        Box(
            modifier = modifier
                .fillMaxWidth()
                .background(White)
        ) {
            if (backgroundVideoReference != null) {
                // TODO: add video glide item
            } else if (backgroundImageReference != null) {
                CustomGlideImageShaded(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .onGloballyPositioned { backgroundSize = it.size },
                    reference = backgroundImageReference
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 15.dp, top = 15.dp, end = 15.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                CustomGlideImage(
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
                        color = if (backgroundSize.height == 0) Black
                        else White,
                        maxLines = 1,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        overflow = TextOverflow.Ellipsis
                    )

                    Text(
                        modifier = Modifier.weight(1f),
                        text = createdTime,
                        color = if (backgroundSize.height == 0) Black
                        else White,
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
                                tint = if (backgroundSize.height == 0) Black
                                else White
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
                        start = 15.dp,
                        top = if (backgroundSize.height == 0) 75.dp
                        else with(LocalDensity.current) { backgroundSize.height.toDp() -
                                coverSize.height.toDp() / 3 },
                        end = 15.dp
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
                                    }
                                    .onGloballyPositioned { coverSize = it.size },
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
                                    }
                                    .onGloballyPositioned { coverSize = it.size },
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
                                    }
                                    .onGloballyPositioned { coverSize = it.size },
                                reference = coverReference
                            )
                        }
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 10.dp)
                            .align(Alignment.Bottom)
                    ) {
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
                            maxLines = 2,
                            fontSize = 16.sp,
                            lineHeight = 1.1.em,
                            fontWeight = FontWeight.Bold,
                            overflow = TextOverflow.Ellipsis
                        )

                        Text(
                            text = creator,
                            color = Black,
                            maxLines = 2,
                            fontSize = 14.sp,
                            lineHeight = 1.1.em,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = if (backgroundSize.height == 0)
                            with(LocalDensity.current) { 75.dp + coverSize.height.toDp() + 10.dp }
                        else with(LocalDensity.current) {
                            backgroundSize.height.toDp() +
                                    (coverSize.height.toDp() / 3) * 2 + 10.dp
                        },
                        bottom = 15.dp
                    )
            ) {

                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 15.dp, end = 15.dp),
                    text = description,
                    color = Black,
                    fontSize = 14.sp,
                    lineHeight = 1.4.em
                )

                if (comments.isNotEmpty()) {
                    CommentsShortList(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 15.dp, end = 15.dp, top = 5.dp)
                            .clickable { isCommentClicked.value = true },
                        comments = comments
                    )
                }

                InteractionPanel(
                    modifier = Modifier.padding(start = 5.dp, top = 5.dp),
                    isLiked = isLiked,
                    isCommentClicked = isCommentClicked,
                    comments = comments,
                    recommendationId = recommendationId,
                    currentUserUid = currentUserUid,
                    onLikeClick = onLikeClick
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

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(LightGray)
    ) {
        item {
            Spacer(modifier = Modifier.size(10.dp))
        }

        items(5) {
            RecommendationItem(
                modifier = Modifier
                    .padding(start = 5.dp, end = 5.dp, bottom = 10.dp),
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
                title = "title",
                creator = "creator",
                coverType = ItemsShapes.horizontal.name,
                comments = comments,
                isLiked = true,
                recommendationId = "",
                currentUserUid = "",
                openScreen = { },
                onLikeClick = { _: Boolean, _: String, _: String -> Response.Success(true) },
                onRecommendationClick = { _: (String) -> Unit, _: Recommendation -> }
            )
        }
    }
}