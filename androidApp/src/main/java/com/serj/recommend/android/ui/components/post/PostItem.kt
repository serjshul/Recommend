package com.serj.recommend.android.ui.components.post

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.serj.recommend.android.R
import com.serj.recommend.android.model.Recommendation
import com.serj.recommend.android.ui.styles.Black
import com.serj.recommend.android.ui.styles.Gray
import com.serj.recommend.android.ui.styles.White

@Composable
fun PostItem(
    modifier: Modifier = Modifier,
    name: String?,
    nickname: String?,
    date: String?,
    userPhoto: Bitmap?,
    text: String?,
    background: Bitmap?,
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
    if (name != null && nickname != null && date != null && text != null) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .shadow(2.dp)
                .background(White)
        ) {
            if (userPhoto != null) {
                Image(
                    modifier = Modifier
                        .padding(10.dp)
                        .size(40.dp)
                        .clip(CircleShape),
                    bitmap = userPhoto.asImageBitmap(),
                    contentDescription = title,
                    contentScale = ContentScale.Crop
                )
            } else {
                Image(
                    modifier = Modifier
                        .padding(10.dp)
                        .size(40.dp)
                        .clip(CircleShape),
                    painter = painterResource(id = R.drawable.no_user_photo),
                    contentDescription = "photo",
                    contentScale = ContentScale.Crop,
                    alignment = Alignment.TopCenter
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp, end = 10.dp, bottom = 10.dp)
            ) {
                Text(
                    buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                fontWeight = FontWeight.Bold,
                                color = Black
                            )
                        ) {
                            append("$name ")
                        }
                        withStyle(style = SpanStyle(color = Black)) {
                            append("@$nickname ")
                        }
                        withStyle(
                            style = SpanStyle(
                                fontWeight = FontWeight.Bold,
                                color = Gray
                            )
                        ) {
                            append("· ")
                        }
                        withStyle(style = SpanStyle(color = Gray)) {
                            append("$date")
                        }
                    },
                    maxLines = 1
                )

                Text(
                    modifier = Modifier.padding(top = 2.dp),
                    text = text,
                    color = Black,
                    fontSize = 14.sp
                )

                PostRecommendationCard(
                    background = background,
                    title = title,
                    creator = creator,
                    recommendationId = recommendationId,
                    openScreen = openScreen,
                    onRecommendationClick = onRecommendationClick
                )

                InteractionPanel(
                    modifier = Modifier.padding(top = 10.dp),
                    views = viewsCounter.toString(),
                    likes = likesCounter.toString(),
                    comments = commentsCounter.toString(),
                    reposts = repostsCounter.toString()
                )
            }
        }
    }
}

@Preview
@Composable
fun PostItemPreview() {
    LazyColumn(
        modifier = Modifier.background(White)
    ) {
        items(3) {
            PostItem(
                modifier = Modifier.padding(bottom = 2.dp),
                name = "Serj's cringe",
                nickname = "serjshul",
                date = "1d",
                userPhoto = null,
                text = "text text text text text text text text text text text text text text text " +
                        "text text text text text text text text text text text text text text text " +
                        "text text text text text text text text text text text text text text",
                background = null,
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
    }
}