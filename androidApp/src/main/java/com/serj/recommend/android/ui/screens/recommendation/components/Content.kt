package com.serj.recommend.android.ui.screens.recommendation.components

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.serj.recommend.android.ui.components.recommendation.CustomVideoPlayer
import com.serj.recommend.android.ui.components.recommendation.Description

@Composable
fun Content(
    modifier: Modifier = Modifier,
    description: String,
    paragraphs: ArrayList<HashMap<String, String>>,
    paragraphsImages: Map<Int?, Bitmap?>,
    quote: String,
    color: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(start = 15.dp, end = 15.dp, bottom = 20.dp)
    ) {
        Description(
            modifier = modifier,
            description = description,
            color = color
        )

        for (i in paragraphs.indices) {
            Paragraph(
                modifier = modifier,
                title = paragraphs[i]["title"] ?: "",
                image = paragraphsImages[i],
                video = paragraphs[i]["video"],
                text = paragraphs[i]["text"] ?: "",
                color = color
            )
        }

        Quote(
            modifier = modifier,
            quote = quote,
            color = color
        )
    }
}

@Composable
fun Paragraph(
    modifier: Modifier = Modifier,
    title: String,
    image: Bitmap?,
    video: String?,
    text: String,
    color: String
) {
    val paragraphTexts = getParagraphTexts(text)

    Column(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(bottom = 20.dp)
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
                    .padding(bottom = 15.dp)
                    .clip(RoundedCornerShape(10.dp)),
                bitmap = image.asImageBitmap(),
                contentDescription = "paragraph",
                contentScale = ContentScale.Crop
            )
        }

        for (i in paragraphTexts.indices) {
            Text(
                modifier = if (i != paragraphTexts.size - 1) modifier.padding(bottom = 12.dp)
                    else modifier,
                text = paragraphTexts[i],
                color = Color.Black,
                fontSize = 14.sp
            )
        }
    }
}

@Composable
fun Quote(
    modifier: Modifier = Modifier,
    quote: String,
    color: String
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 0.dp)
            .background(color.toColor(), RoundedCornerShape(15.dp))
    ) {
        Text(
            modifier = modifier
                .padding(top = 10.dp, bottom = 5.dp)
                .align(Alignment.CenterHorizontally),
            text = "Favourite quote",
            color = Color.White,
            fontSize = 12.sp
        )

        Text(
            modifier = modifier.padding(start = 15.dp, end = 15.dp, bottom = 15.dp),
            text = quote.replace("\\n", "\n"),
            fontWeight = FontWeight.Bold,
            color = Color.White,
            fontSize = 17.sp
        )
    }
}

fun getParagraphTexts(text: String): List<String> {
    val prepare = text.replace("\\n", "\n")
    return prepare.split("\\n".toRegex()).map { it.trim() }
}

fun String.toColor(): Color {
    return Color(android.graphics.Color.parseColor(this))
}