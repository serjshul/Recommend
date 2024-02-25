package com.serj.recommend.android.ui.screens.common.recommendation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.google.firebase.storage.StorageReference

@Composable
fun Header(
    modifier: Modifier = Modifier,
    title: String,
    creator: String,
    tags: List<String>,
    year: Int,
    photoReference: StorageReference?,
    nickname: String?
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (nickname != null) UserInfo(
            photoReference = photoReference,
            nickname = nickname,
            modifier = Modifier
                .padding(top = 10.dp)
        )

        Text(
            modifier = Modifier
                .padding(
                    start = 15.dp,
                    end = 15.dp,
                    top = if (nickname != null) 190.dp else 246.dp
                )
                .align(Alignment.CenterHorizontally),
            text = title,
            color = Color.White,
            fontSize = 24.sp,
            maxLines = 4,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        HeaderInfo(
            modifier = Modifier
                .padding(15.dp, 20.dp)
                .fillMaxWidth(),
            creator = creator,
            tags = tags,
            year = year
        )
    }
}

@Composable
fun HeaderInfo(
    modifier: Modifier = Modifier,
    creator: String,
    tags: List<String>,
    year: Int
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            modifier = Modifier
                .weight(1f),
            text = creator,
            overflow = TextOverflow.Ellipsis,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            color = Color.White,
            lineHeight = 1.2.em,
            fontSize = 14.sp,
            maxLines = 4
        )

        Text(
            modifier = Modifier
                .padding(start = 10.dp, end = 10.dp),
            text = "/",
            color = Color.White,
            fontSize = 23.sp
        )

        Text(
            modifier = Modifier
                .weight(1f),
            text = tags.joinToString(),
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center,
            color = Color.White,
            lineHeight = 1.2.em,
            fontSize = 14.sp,
            maxLines = 4
        )

        Text(
            modifier = Modifier
                .padding(start = 10.dp, end = 10.dp),
            text = "/",
            color = Color.White,
            fontSize = 23.sp
        )

        Text(
            modifier = Modifier
                .weight(1f),
            text = year.toString(),
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center,
            color = Color.White,
            fontSize = 14.sp,
            maxLines = 1
        )
    }
}

@Preview
@Composable
private fun RecommendationHeaderPreview() {
    Header(
        title = "Title",
        creator = "Creator",
        tags = listOf(
            "tag",
            "tag",
            "tag",
            "tag",
            "tag",
            "tag",
            "tag",
            "tag",
            "tag",
            "tag",
            "tag",
            "tag"
        ),
        year = 2023,
        photoReference = null,
        nickname = "nickname"
    )
}