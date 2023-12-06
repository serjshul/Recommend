package com.serj.recommend.android.ui.screens.recommendation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.serj.recommend.android.R

@Composable
fun Paragraph(
    modifier: Modifier = Modifier,
    title: String,
    image: String?,
    video: String?,
    text: String,
    color: String
) {
    val paragraphs = getParagraphs(text)

    Column(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(start = 15.dp, end = 15.dp, bottom = 20.dp)
    ){
        Text(
            modifier = modifier.padding(bottom = 15.dp),
            text = title,
            color = color.toColor(),
            fontSize = 22.sp,
            maxLines = 2,
            fontWeight = FontWeight.Bold
        )

        if (video != null) {
            CustomVideoPlayer()
        } else if (image != null) {
            Image(
                modifier = Modifier
                    .height(180.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp)),
                painter = painterResource(id = R.drawable.music_paragraph_1),
                contentDescription = "paragraph",
                contentScale = ContentScale.Crop
            )
        }

        for (i in paragraphs.indices) {
            Text(
                modifier = when (i) {
                    0 -> modifier.padding(top = 15.dp, bottom = 12.dp)
                    paragraphs.size - 1 -> modifier
                    else -> modifier.padding(bottom = 12.dp)
                },
                text = paragraphs[i],
                color = Color.Black,
                fontSize = 14.sp
            )
        }
    }
}

fun getParagraphs(text: String): List<String> {
    val prepare = text.replace("\\n", "\n")
    return prepare.split("\\n".toRegex()).map { it.trim() }
}

fun String.toColor(): Color {
    return Color(android.graphics.Color.parseColor(this))
}