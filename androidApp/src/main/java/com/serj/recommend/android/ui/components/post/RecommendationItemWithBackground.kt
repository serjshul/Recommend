package com.serj.recommend.android.ui.components.post

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.serj.recommend.android.R
import com.serj.recommend.android.common.ext.postShape
import com.serj.recommend.android.model.Recommendation
import com.serj.recommend.android.model.items.UserItem
import com.serj.recommend.android.ui.components.loadingIndicators.SmallLoadingIndicator
import com.serj.recommend.android.ui.components.media.ImageOrdinary
import com.serj.recommend.android.ui.components.media.ImageShaded
import com.serj.recommend.android.ui.styles.ItemsShapes
import com.serj.recommend.android.ui.styles.LightGray
import com.serj.recommend.android.ui.styles.White
import java.util.Date

@Composable
fun RecommendationItemWithBackground(
    modifier: Modifier = Modifier,
    user: UserItem?,
    date: Date?,
    description: String?,
    coverType: String?,
    cover: Bitmap? = null,
    backgroundImage: Bitmap?,
    backgroundVideo: String? = null,
    title: String?,
    creator: String?,
    recommendationId: String?,
    openScreen: (String) -> Unit,
    onRecommendationClick: ((String) -> Unit, Recommendation) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    if (user!!.nickname != null && date != null && description != null && title != null && creator != null) {
        Box(
            modifier = modifier.postShape()
        ) {
            when {
                backgroundVideo != null -> {

                }
                backgroundImage != null -> {
                    ImageShaded(
                        modifier = Modifier.fillMaxSize(),
                        image = backgroundImage
                    )
                }
                else -> {
                    Image(
                        modifier = Modifier.fillMaxSize(),
                        colorFilter = ColorFilter
                            .colorMatrix(ColorMatrix().apply {
                                setToScale(0.7f, 0.7f, 0.7f, 1f)
                            }),
                        painter = painterResource(id = R.drawable.background),
                        contentDescription = "photo",
                        contentScale = ContentScale.Crop
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(15.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (user.photo != null) {
                        ImageOrdinary(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape),
                            image = user.photo!!
                        )
                    } else {
                        Image(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape),
                            painter = painterResource(id = R.drawable.no_user_photo),
                            contentDescription = "photo",
                            contentScale = ContentScale.Crop
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            modifier = Modifier
                                .weight(8f)
                                .padding(start = 15.dp),
                            text = user.nickname!!,
                            color = White,
                            maxLines = 1,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            overflow = TextOverflow.Ellipsis
                        )

                        Text(
                            modifier = Modifier.weight(1f),
                            text = "16h",
                            color = White,
                            fontSize = 14.sp
                        )

                        Box(
                            modifier = Modifier
                                .weight(1f)
                        ) {
                            IconButton(
                                onClick = { expanded = true }
                            ) {
                                Icon(
                                    Icons.Default.MoreVert,
                                    contentDescription = "Show the menu",
                                    tint = White
                                )
                            }

                            DropdownMenu(
                                expanded = expanded,
                                onDismissRequest = { expanded = false }
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

                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Bottom
                ) {
                    Row(
                        modifier = Modifier
                            .padding(bottom = 10.dp),
                        verticalAlignment = Alignment.Bottom
                    ) {
                        when (coverType) {
                            ItemsShapes.square.name -> {
                                if (cover != null) {
                                    Image(
                                        modifier = Modifier
                                            .padding(end = 10.dp)
                                            .size(100.dp)
                                            .clip(RoundedCornerShape(5.dp)),
                                        bitmap = cover.asImageBitmap(),
                                        contentDescription = title,
                                        contentScale = ContentScale.Crop
                                    )
                                } else {
                                    SmallLoadingIndicator(
                                        modifier = Modifier
                                            .padding(end = 10.dp)
                                            .size(100.dp)
                                            .clip(RoundedCornerShape(5.dp)),
                                        backgroundColor = LightGray
                                    )
                                }
                            }
                            ItemsShapes.vertical.name -> {
                                if (cover != null) {
                                    Image(
                                        modifier = Modifier
                                            .padding(end = 10.dp)
                                            .height(150.dp)
                                            .width(110.dp)
                                            .clip(RoundedCornerShape(5.dp)),
                                        bitmap = cover.asImageBitmap(),
                                        contentDescription = title,
                                        contentScale = ContentScale.Crop
                                    )
                                } else {
                                    SmallLoadingIndicator(
                                        modifier = Modifier
                                            .padding(end = 10.dp)
                                            .height(150.dp)
                                            .width(110.dp)
                                            .clip(RoundedCornerShape(5.dp)),
                                        backgroundColor = LightGray
                                    )
                                }
                            }
                            else -> {
                                if (cover != null) {
                                    Image(
                                        modifier = Modifier
                                            .padding(end = 10.dp)
                                            .height(100.dp)
                                            .width(165.dp)
                                            .clip(RoundedCornerShape(5.dp)),
                                        bitmap = cover.asImageBitmap(),
                                        contentDescription = title,
                                        contentScale = ContentScale.Crop
                                    )
                                } else {
                                    SmallLoadingIndicator(
                                        modifier = Modifier
                                            .padding(end = 10.dp)
                                            .height(100.dp)
                                            .width(165.dp)
                                            .clip(RoundedCornerShape(5.dp)),
                                        backgroundColor = LightGray
                                    )
                                }
                            }
                        }

                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.Bottom
                        ) {
                            Text(
                                text = title,
                                color = White,
                                maxLines = 2,
                                fontSize = 16.sp,
                                lineHeight = 1.4.em,
                                fontWeight = FontWeight.Bold,
                                overflow = TextOverflow.Ellipsis
                            )

                            Text(
                                text = creator,
                                color = White,
                                maxLines = 2,
                                fontSize = 14.sp,
                                lineHeight = 1.4.em,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }

                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = description,
                        color = White,
                        fontSize = 14.sp,
                        maxLines = 4,
                        lineHeight = 1.4.em,
                        overflow = TextOverflow.Ellipsis
                    )

                    InteractionPanel(
                        modifier = Modifier
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun PostWithBackgroundPreview() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
    ) {
        item {
            Spacer(modifier = Modifier.size(10.dp))
        }

        items(5) {
            RecommendationItemWithBackground(
                modifier = Modifier
                    .padding(start = 5.dp, end = 5.dp, bottom = 10.dp),
                user = UserItem(
                    nickname = "serjshul"
                ),
                date = Date(0),
                description = "text text text text text text text text text text text text text text text " +
                        "text text text text text text text text text text text text text text text " +
                        "text text text text text text text text text text text text text text",
                backgroundImage = null,
                title = "title",
                creator = "creator",
                coverType = ItemsShapes.horizontal.name,
                recommendationId = "",
                openScreen = { },
                onRecommendationClick = { _: (String) -> Unit, _: Recommendation -> }
            )
        }
    }
}