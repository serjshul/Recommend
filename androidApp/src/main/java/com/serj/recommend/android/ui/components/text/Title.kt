package com.serj.recommend.android.ui.components.text

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.serj.recommend.android.common.ext.toColor

@Composable
fun Title(
    modifier: Modifier = Modifier,
    title: String,
    color: String
) {
    Text(
        modifier = modifier,
        text = title,
        color = color.toColor(),
        fontSize = 22.sp,
        maxLines = 2,
        fontWeight = FontWeight.Bold
    )
}