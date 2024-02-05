package com.serj.recommend.android.ui.screens.main.newRecommendation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp

@Composable
fun NewRecommendationDescription(
    modifier: Modifier = Modifier,
    description: String,
    onDescriptionValueChange: (String) -> Unit
) {
    NewRecommendationInput(
        text = description,
        placeholder = "Description...",
        textColor = Color.Black,
        fontSize = 14.sp,
        lineHeight = 1.2.em,
        textAlign = TextAlign.Start,
        onValueChange = onDescriptionValueChange,
        modifier = modifier
            .fillMaxWidth()
            .clip(
                RoundedCornerShape(
                    topStart = 20.dp,
                    topEnd = 20.dp,
                    bottomStart = 0.dp,
                    bottomEnd = 0.dp
                )
            )
            .background(Color.White)
            .padding(
                top = 2.5.dp,
                bottom = 2.5.dp
            )
    )
}

@Preview
@Composable
fun NewRecommendationDescriptionPreview() {
    NewRecommendationDescription(
        description = "description description description description description description " +
                "description description description description description description " +
                "description description description description description description\n" +
                "description description description description description description " +
                "description description description description description description " +
                "description description description description description description",
        onDescriptionValueChange = { }
    )
}