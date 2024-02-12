package com.serj.recommend.android.ui.screens.common.recommendation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.serj.recommend.android.R
import com.serj.recommend.android.common.getMonthAndDay
import com.serj.recommend.android.common.getYear
import java.util.Date

@Composable
fun InsightsBottomSheet(
    modifier: Modifier = Modifier,
    likesAmount: Int,
    commentsAmount: Int,
    repostsAmount: Int,
    savedAmounts: Int,
    viewsAmount: Int,
    coverageAmount: Int,
    date: Date
) {
    val contentColor = Color.Black
    val day = getMonthAndDay(date)
    val year = getYear(date)

    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = modifier
                .padding(start = 15.dp, top = 80.dp, end = 15.dp, bottom = 30.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = buildAnnotatedString {
                    append("Post on ")
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("$year $day")
                    }
                },
                color = contentColor,
                textAlign = TextAlign.Center,
                fontSize = 14.sp,
                modifier = Modifier
                    .padding(bottom = 25.dp)
                    .fillMaxWidth()
            )

            Row(
                modifier = Modifier
                    .padding(bottom = 30.dp)
                    .fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        ImageVector.vectorResource(id = R.drawable.interaction_like_filled),
                        tint = Color.Black,
                        contentDescription = "Likes",
                        modifier = Modifier.size(24.dp)
                    )
                    Text(
                        text = likesAmount.toString(),
                        color = contentColor,
                        textAlign = TextAlign.Start,
                        fontSize = 14.sp
                    )
                }

                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        ImageVector.vectorResource(id = R.drawable.interaction_comment),
                        tint = Color.Black,
                        contentDescription = "Comments",
                        modifier = Modifier.size(24.dp)
                    )
                    Text(
                        text = commentsAmount.toString(),
                        color = contentColor,
                        textAlign = TextAlign.Start,
                        fontSize = 14.sp
                    )
                }

                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        ImageVector.vectorResource(id = R.drawable.interaction_repost),
                        tint = Color.Black,
                        contentDescription = "Reposts",
                        modifier = Modifier.size(24.dp)
                    )
                    Text(
                        text = repostsAmount.toString(),
                        color = contentColor,
                        textAlign = TextAlign.Start,
                        fontSize = 14.sp
                    )
                }

                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        ImageVector.vectorResource(id = R.drawable.icon_saved),
                        tint = Color.Black,
                        contentDescription = "Saved",
                        modifier = Modifier.size(24.dp)
                    )
                    Text(
                        text = savedAmounts.toString(),
                        color = contentColor,
                        textAlign = TextAlign.Start,
                        fontSize = 14.sp
                    )
                }
            }

            Column(
                modifier = Modifier
                    .padding(bottom = 25.dp)
                    .fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(bottom = 15.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Overview",
                        color = contentColor,
                        textAlign = TextAlign.Start,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        modifier = Modifier.padding(end = 15.dp)
                    )
                    Icon(
                        imageVector = Icons.Outlined.Info,
                        tint = Color.Black,
                        contentDescription = "Likes",
                        modifier = Modifier.size(18.dp)
                    )
                }

                Box(
                    modifier = Modifier
                        .padding(bottom = 15.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "Views",
                        color = contentColor,
                        fontSize = 14.sp,
                        modifier = Modifier.align(Alignment.CenterStart)
                    )
                    Text(
                        text = viewsAmount.toString(),
                        color = contentColor,
                        fontSize = 14.sp,
                        modifier = Modifier.align(Alignment.CenterEnd)
                    )
                }

                Box(
                    modifier = Modifier
                        .padding(bottom = 15.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "Coverage",
                        color = contentColor,
                        fontSize = 14.sp,
                        modifier = Modifier.align(Alignment.CenterStart)
                    )
                    Text(
                        text = coverageAmount.toString(),
                        color = contentColor,
                        fontSize = 14.sp,
                        modifier = Modifier.align(Alignment.CenterEnd)
                    )
                }

                Box(
                    modifier = Modifier
                        .padding(bottom = 15.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "Accounts reached",
                        color = contentColor,
                        fontSize = 14.sp,
                        modifier = Modifier.align(Alignment.CenterStart)
                    )
                    Text(
                        text = "N/A",
                        color = contentColor,
                        fontSize = 14.sp,
                        modifier = Modifier.align(Alignment.CenterEnd)
                    )
                }

                Box(
                    modifier = Modifier
                        .padding(bottom = 15.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "Accounts engaged",
                        color = contentColor,
                        fontSize = 14.sp,
                        modifier = Modifier.align(Alignment.CenterStart)
                    )
                    Text(
                        text = "N/A",
                        color = contentColor,
                        fontSize = 14.sp,
                        modifier = Modifier.align(Alignment.CenterEnd)
                    )
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "Profile activity",
                        color = contentColor,
                        fontSize = 14.sp,
                        modifier = Modifier.align(Alignment.CenterStart)
                    )
                    Text(
                        text = "N/A",
                        color = contentColor,
                        fontSize = 14.sp,
                        modifier = Modifier.align(Alignment.CenterEnd)
                    )
                }
            }

            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(bottom = 15.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Impressions",
                        color = contentColor,
                        textAlign = TextAlign.Start,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        modifier = Modifier.padding(end = 15.dp)
                    )
                    Icon(
                        imageVector = Icons.Outlined.Info,
                        tint = Color.Black,
                        contentDescription = "Likes",
                        modifier = Modifier.size(18.dp)
                    )
                }

                Box(
                    modifier = Modifier
                        .padding(bottom = 15.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "From recommendation",
                        color = contentColor,
                        fontSize = 14.sp,
                        modifier = Modifier.align(Alignment.CenterStart)
                    )
                    Text(
                        text = "N/A",
                        color = contentColor,
                        fontSize = 14.sp,
                        modifier = Modifier.align(Alignment.CenterEnd)
                    )
                }

                Box(
                    modifier = Modifier
                        .padding(bottom = 15.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "From post",
                        color = contentColor,
                        fontSize = 14.sp,
                        modifier = Modifier.align(Alignment.CenterStart)
                    )
                    Text(
                        text = "N/A",
                        color = contentColor,
                        fontSize = 14.sp,
                        modifier = Modifier.align(Alignment.CenterEnd)
                    )
                }

                Box(
                    modifier = Modifier
                        .padding(bottom = 15.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "From feed",
                        color = contentColor,
                        fontSize = 14.sp,
                        modifier = Modifier.align(Alignment.CenterStart)
                    )
                    Text(
                        text = "N/A",
                        color = contentColor,
                        fontSize = 14.sp,
                        modifier = Modifier.align(Alignment.CenterEnd)
                    )
                }

                Box(
                    modifier = Modifier
                        .padding(bottom = 15.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "From home",
                        color = contentColor,
                        fontSize = 14.sp,
                        modifier = Modifier.align(Alignment.CenterStart)
                    )
                    Text(
                        text = "N/A",
                        color = contentColor,
                        fontSize = 14.sp,
                        modifier = Modifier.align(Alignment.CenterEnd)
                    )
                }

                Box(
                    modifier = Modifier
                        .padding(bottom = 15.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "From profile",
                        color = contentColor,
                        fontSize = 14.sp,
                        modifier = Modifier.align(Alignment.CenterStart)
                    )
                    Text(
                        text = "N/A",
                        color = contentColor,
                        fontSize = 14.sp,
                        modifier = Modifier.align(Alignment.CenterEnd)
                    )
                }

                Box(
                    modifier = Modifier
                        .padding(bottom = 15.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "From search",
                        color = contentColor,
                        fontSize = 14.sp,
                        modifier = Modifier.align(Alignment.CenterStart)
                    )
                    Text(
                        text = "N/A",
                        color = contentColor,
                        fontSize = 14.sp,
                        modifier = Modifier.align(Alignment.CenterEnd)
                    )
                }
            }
        }

        Surface(
            modifier = Modifier
                .fillMaxWidth(),
            shadowElevation = 4.dp
        ) {
            Box(
                modifier = Modifier
                    .height(50.dp)
                    .fillMaxWidth()
                    .background(Color.White),
            ) {
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = "Insights",
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    fontSize = 14.sp
                )
            }
        }
    }
}

@Preview
@Composable
fun InsightsBottomSheetPreview() {
    InsightsBottomSheet(
        modifier = Modifier.background(Color.White),
        likesAmount = 642,
        commentsAmount = 84,
        repostsAmount = 14,
        savedAmounts = 45,
        viewsAmount = 3462,
        coverageAmount = 14524,
        date = Date()
    )
}