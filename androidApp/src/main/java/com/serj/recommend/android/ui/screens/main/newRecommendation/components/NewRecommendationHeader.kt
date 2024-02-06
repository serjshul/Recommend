package com.serj.recommend.android.ui.screens.main.newRecommendation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.google.firebase.storage.StorageReference
import com.serj.recommend.android.ui.screens.common.recommendation.components.UserInfo

@Composable
fun NewRecommendationHeader(
    modifier: Modifier = Modifier,
    title: String,
    type: String,
    creator: String,
    tags: String,
    year: String,
    photoReference: StorageReference?,
    nickname: String?,
    onTitleValueChange: (String) -> Unit,
    onTypeValueChange: (String) -> Unit,
    onCreatorValueChange: (String) -> Unit,
    onTagsValueChange: (String) -> Unit,
    onYearValueChange: (String) -> Unit
) {
    Column(
        modifier = modifier.padding(top = 40.dp)
    ) {
        if (nickname != null) {
            UserInfo(
                photoReference = photoReference,
                nickname = nickname
            )
        }

        NewRecommendationInput(
            text = title,
            placeholder = "Title",
            enabled = true,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            maxLines = 4,
            modifier = Modifier
                .padding(
                    start = 15.dp,
                    end = 15.dp,
                    top = if (nickname != null) 130.dp else 186.dp
                )
                .align(Alignment.CenterHorizontally),
            onValueChange = onTitleValueChange
        )

        NewRecommendationInput(
            text = type,
            placeholder = "Type",
            enabled = true,
            fontSize = 14.sp,
            lineHeight = 1.2.em,
            maxLines = 4,
            modifier = Modifier,
            onValueChange = onTypeValueChange,
        )

        NewRecommendationHeaderInfo(
            modifier = Modifier
                .padding(15.dp, 20.dp)
                .fillMaxWidth(),
            creator = creator,
            tags = tags,
            year = year,
            onCreatorValueChange = onCreatorValueChange,
            onTagsValueChange = onTagsValueChange,
            onYearValueChange = onYearValueChange
        )
    }
}

@Composable
fun NewRecommendationHeaderInfo(
    modifier: Modifier = Modifier,
    creator: String,
    tags: String,
    year: String,
    onCreatorValueChange: (String) -> Unit,
    onTagsValueChange: (String) -> Unit,
    onYearValueChange: (String) -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        NewRecommendationInput(
            text = creator,
            placeholder = "Creator",
            enabled = true,
            fontSize = 14.sp,
            lineHeight = 1.2.em,
            maxLines = 4,
            modifier = Modifier
                .weight(1f),
            onValueChange = onCreatorValueChange,
        )

        Text(
            modifier = Modifier
                .padding(start = 10.dp, end = 10.dp),
            text = "/",
            color = Color.White,
            fontSize = 23.sp
        )

        NewRecommendationInput(
            text = tags,
            placeholder = "Tags",
            enabled = true,
            fontSize = 14.sp,
            lineHeight = 1.2.em,
            maxLines = 4,
            modifier = Modifier
                .weight(1f),
            onValueChange = onTagsValueChange,
        )

        Text(
            modifier = Modifier
                .padding(start = 10.dp, end = 10.dp),
            text = "/",
            color = Color.White,
            fontSize = 23.sp
        )

        NewRecommendationInput(
            text = year,
            placeholder = "Year",
            enabled = true,
            fontSize = 14.sp,
            maxLines = 1,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier
                .weight(1f),
            onValueChange = onYearValueChange,
        )
    }
}

@Preview
@Composable
fun NewRecommendationHeaderPreview() {
    NewRecommendationHeader(
        title = "Title",
        type = "Type",
        creator = "Creator",
        tags = "Tag, Tag, Tag, Tag, Tag",
        year = "2023",
        photoReference = null,
        nickname = "nickname",
        onTitleValueChange = { },
        onTypeValueChange = { },
        onCreatorValueChange = { },
        onTagsValueChange = { },
        onYearValueChange = { }
    )
}