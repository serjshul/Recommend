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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.graphics.Brush
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
import com.serj.recommend.android.ui.components.loadingIndicators.SmallLoadingIndicator
import com.serj.recommend.android.ui.components.media.ImageOrdinary
import com.serj.recommend.android.ui.styles.Brown
import com.serj.recommend.android.ui.styles.Gray
import com.serj.recommend.android.ui.styles.LightGray
import com.serj.recommend.android.ui.styles.White

@Composable
fun PostWithoutBackground(
    modifier: Modifier = Modifier,
    nickname: String?,
    date: String?,
    userPhoto: Bitmap?,
    description: String?,
    cover: Bitmap? = null,
    title: String?,
    creator: String?,
    recommendationId: String?,
    openScreen: (String) -> Unit,
    onRecommendationClick: ((String) -> Unit, Recommendation) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    if (nickname != null && date != null && description != null && title != null && creator != null) {
        Box(
            modifier = modifier.postShape()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(listOf(Brown, Gray))
                    )
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(15.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (userPhoto != null) {
                        ImageOrdinary(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape),
                            image = userPhoto
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
                            text = nickname,
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
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Bottom
                ) {
                    if (cover != null) {
                        Image(
                            modifier = Modifier
                                .size(190.dp)
                                .padding(end = 10.dp)
                                .clip(RoundedCornerShape(5.dp)),
                            bitmap = cover.asImageBitmap(),
                            contentDescription = title,
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        SmallLoadingIndicator(
                            modifier = Modifier
                                .padding(bottom = 25.dp)
                                .size(190.dp)
                                .clip(RoundedCornerShape(5.dp)),
                            backgroundColor = LightGray
                        )
                    }

                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = title,
                        color = White,
                        fontSize = 16.sp,
                        maxLines = 2,
                        fontWeight = FontWeight.Bold,
                        overflow = TextOverflow.Ellipsis
                    )

                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 10.dp),
                        text = creator,
                        color = White,
                        maxLines = 2,
                        fontSize = 14.sp,
                        overflow = TextOverflow.Ellipsis
                    )

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
fun PostWithoutBackgroundPreview() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
    ) {
        item {
            Spacer(modifier = Modifier.size(10.dp))
        }

        items(5) {
            PostWithoutBackground(
                modifier = Modifier
                    .padding(start = 5.dp, end = 5.dp, bottom = 10.dp),
                nickname = "serjshul",
                date = "1d",
                userPhoto = null,
                description = "text text text text text text text text text text text text text text text " +
                        "text text text text text text text text text text text text text text text " +
                        "text text text text text text text text text text text text text text",
                title = "title",
                creator = "creator",
                recommendationId = "",
                openScreen = { },
                onRecommendationClick = { _: (String) -> Unit, _: Recommendation -> }
            )
        }
    }
}