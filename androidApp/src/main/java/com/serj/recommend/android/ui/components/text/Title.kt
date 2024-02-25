package com.serj.recommend.android.ui.components.text

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun Title(
    modifier: Modifier = Modifier,
    title: String,
    color: Color
) {
    Text(
        modifier = modifier,
        text = title,
        color = color,
        fontSize = 22.sp,
        maxLines = 2,
        fontWeight = FontWeight.Bold
    )
}