package com.serj.recommend.android.ui.screens.main.feed

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
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
import com.serj.recommend.android.ui.components.recommendation.InteractionPanel
import com.serj.recommend.android.ui.styles.Black
import com.serj.recommend.android.ui.styles.Gray
import com.serj.recommend.android.ui.styles.White

@Composable
fun PostItem(
    modifier: Modifier = Modifier,
    name: String?,
    nickname: String?,
    date: String?,
    text: String?,
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
            Image(
                modifier = Modifier
                    .padding(10.dp)
                    .size(40.dp)
                    .clip(CircleShape),
                painter = painterResource(id = R.drawable.profile_photo),
                contentDescription = "photo",
                contentScale = ContentScale.Crop,
                alignment = Alignment.TopCenter
            )

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
                            append("$nickname ")
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

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .padding(top = 5.dp)
                        .clip(RoundedCornerShape(5.dp))
                        .clickable {
                            if (recommendationId != null) {
                                onRecommendationClick(
                                    openScreen,
                                    Recommendation(id = recommendationId)
                                )
                            }
                        }
                ) {
                    Image(
                        modifier = Modifier
                            .fillMaxSize()
                            .blur(radius = 3.dp),
                        painter = painterResource(id = R.drawable.background),
                        colorFilter = ColorFilter.colorMatrix(ColorMatrix().apply {
                            setToScale(0.8f, 0.8f, 0.8f, 1f)
                        }),
                        contentDescription = "",
                        contentScale = ContentScale.Crop
                    )

                    Column(
                        modifier = Modifier.align(Alignment.Center),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Title",
                            color = White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            text = "Creator",
                            color = White,
                            fontSize = 16.sp
                        )
                    }
                }

                InteractionPanel(
                    modifier = Modifier.padding(top = 10.dp),
                    views = "183",
                    likes = "93",
                    comments = "5",
                    reposts = "4"
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
                nickname = "@serjshul",
                date = "1d",
                text = "text text text text text text text text text text text text text text text " +
                        "text text text text text text text text text text text text text text text " +
                        "text text text text text text text text text text text text text text",
                recommendationId = "",
                openScreen = { },
                onRecommendationClick = { _: (String) -> Unit, _: Recommendation -> }
            )
        }
    }
}