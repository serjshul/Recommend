package com.serj.recommend.android.ui.components.post

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
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
import com.serj.recommend.android.ui.components.media.ImageShaded
import com.serj.recommend.android.ui.styles.LightGray
import com.serj.recommend.android.ui.styles.White

@Composable
fun PostItem(
    modifier: Modifier = Modifier,
    nickname: String?,
    date: String?,
    userPhoto: Bitmap?,
    text: String?,
    cover: Bitmap? = null,
    backgroundImage: Bitmap?,
    backgroundVideo: String? = null,
    title: String?,
    creator: String?,
    likesCounter: Int?,
    commentsCounter: Int?,
    repostsCounter: Int?,
    viewsCounter: Int?,
    recommendationId: String?,
    openScreen: (String) -> Unit,
    onRecommendationClick: ((String) -> Unit, Recommendation) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    if (nickname != null && date != null && text != null && title != null && creator != null) {
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

                    /*
                    SmallLoadingIndicator(
                        backgroundColor = White
                    )

                     */
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
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            modifier = Modifier.weight(1f),
                            text = "16h",
                            color = White,
                            fontSize = 14.sp
                        )

                        IconButton(
                            modifier = Modifier
                                .weight(1f),
                            onClick = { expanded = true }
                        ) {
                            Icon(
                                Icons.Default.MoreVert,
                                contentDescription = "Show the menu",
                                tint = White
                            )
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
                        if (cover != null) {
                            Image(
                                modifier = Modifier
                                    .size(100.dp)
                                    .padding(end = 10.dp)
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

                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.Bottom
                        ) {
                            Text(
                                text = title,
                                color = White,
                                fontSize = 16.sp,
                                maxLines = 2,
                                fontWeight = FontWeight.Bold,
                                overflow = TextOverflow.Ellipsis
                            )

                            Text(
                                text = creator,
                                color = White,
                                maxLines = 2,
                                fontSize = 14.sp,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }

                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = text,
                        color = White,
                        fontSize = 14.sp,
                        maxLines = 4,
                        lineHeight = 1.4.em,
                        overflow = TextOverflow.Ellipsis
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp)
                    ) {
                        IconButton(
                            modifier = Modifier
                                .size(35.dp)
                                .padding(end = 10.dp),
                            onClick = { }
                        ) {
                            Icon(
                                ImageVector.vectorResource(id = R.drawable.icon_like_bordered_1),
                                contentDescription = "Like",
                                tint = White
                            )
                        }

                        IconButton(
                            modifier = Modifier
                                .size(30.dp)
                                .padding(top = 7.dp, end = 6.dp),
                            onClick = { }
                        ) {
                            Icon(
                                ImageVector.vectorResource(id = R.drawable.icon_comment_1),
                                contentDescription = "Comment",
                                tint = White
                            )
                        }

                        IconButton(
                            modifier = Modifier
                                .size(30.dp)
                                .padding(top = 7.dp),
                            onClick = { }
                        ) {
                            Icon(
                                ImageVector.vectorResource(id = R.drawable.icon_repost_1),
                                contentDescription = "Repost",
                                tint = White
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PostItemPreview() {
    PostItem(
        modifier = Modifier.padding(bottom = 2.dp),
        nickname = "serjshul",
        date = "1d",
        userPhoto = null,
        text = "text text text text text text text text text text text text text text text " +
                "text text text text text text text text text text text text text text text " +
                "text text text text text text text text text text text text text text",
        backgroundImage = null,
        title = "title",
        creator = "creator",
        likesCounter = 43,
        commentsCounter = 7,
        repostsCounter = 4,
        viewsCounter = 185,
        recommendationId = "",
        openScreen = { },
        onRecommendationClick = { _: (String) -> Unit, _: Recommendation -> }
    )
}