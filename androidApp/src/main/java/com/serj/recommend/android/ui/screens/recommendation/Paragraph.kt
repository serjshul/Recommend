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
import androidx.core.graphics.toColorInt
import com.serj.recommend.android.R

@Composable
fun Paragraph(
    modifier: Modifier = Modifier
) {
    val text = "\"Saoko\" was first mentioned in November 2021, in a Rolling Stone article by Diego Ortiz that covered the emancipation of Rosalía and the recording process of her album Motomami. It was also revealed to be the opening track of the album. The singer previewed the song on TikTok on 29 December.\n" +
            "Upon unveiling the cover art of the album, the singer announced a new song to be released on 4 February. A teaser of the music video for \"Saoko\" was posted on 2 February, indicating the release of the song that week.\n" +
            "This song is set to be used on FIFA 23's soundtrack, which was released on 30 September 2022. Due to players that pre-ordered the game via Xbox getting access to the game earlier than the official release date accidentally, the entire soundtrack has been leaked early to the game's release, confirming \"Saoko\" being part of it."
    val paragraphs = getParagraphs(text)
    val video: Int? = null
    val image: Int? = R.drawable.music_paragraph_1
    val color = "#E03038"

    Column(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(start = 15.dp, end = 15.dp, bottom = 20.dp)
    ){
        Text(
            modifier = modifier.padding(bottom = 15.dp),
            text = "Background",
            color = Color(color.toColorInt()),
            fontSize = 26.sp,
            maxLines = 2,
            fontWeight = FontWeight.Bold
        )

        if (video != null) {
            CustomVideoPlayer()
        } else {
            Image(
                modifier = Modifier
                    .height(180.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp)),
                painter = painterResource(id = image!!),
                contentDescription = "paragraph",
                contentScale = ContentScale.Crop
            )
        }

        for (i in paragraphs.indices) {
            Text(
                modifier = when (i) {
                    0 -> modifier.padding(top = 15.dp)
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
    return text.split("\\n".toRegex()).map { it.trim() }
}