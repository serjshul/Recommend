package com.serj.recommend.android.ui.screens.recommendation

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Description(
    modifier: Modifier = Modifier,
    description: String
) {
    Text(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(start = 15.dp, top = 15.dp, end = 15.dp, bottom = 20.dp),
        text = description,
        color = Color.Black,
        fontSize = 14.sp
    )
}