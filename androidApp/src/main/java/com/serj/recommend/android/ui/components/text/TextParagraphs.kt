package com.serj.recommend.android.ui.components.text

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import com.serj.recommend.android.common.ext.textInterval

@Composable
fun TextParagraphs(
    modifier: Modifier = Modifier,
    paragraphTexts: List<String>
) {
    Column(
        modifier = modifier
    ) {
        for (i in paragraphTexts.indices) {
            Text(
                modifier = if (i != paragraphTexts.size - 1) Modifier.textInterval()
                else Modifier,
                text = paragraphTexts[i],
                color = Color.Black,
                fontSize = 14.sp
            )
        }
    }
}