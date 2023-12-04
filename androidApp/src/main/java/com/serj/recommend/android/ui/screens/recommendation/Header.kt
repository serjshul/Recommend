package com.serj.recommend.android.ui.screens.recommendation

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.serj.recommend.android.R

@Composable
fun Header(
    modifier: Modifier = Modifier,
    popUpScreen: () -> Unit
    //article: Article
) {
    val title = "Saoko"
    val type = "Music"
    val creator = "ROSALIA"
    val tags = "Latino pop"
    val year = "2023"

    var isSaved by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(300.dp)
            .paint(
                painterResource(id = R.drawable.background_music_rosalia_saoko),
                colorFilter = ColorFilter.colorMatrix(
                    ColorMatrix().apply { setToScale(0.7f, 0.7f, 0.7f, 1f) }
                ),
                contentScale = ContentScale.Crop
            )
    ) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .height(64.dp)
        ) {
            Image(
                modifier = modifier
                    .padding(20.dp)
                    .align(Alignment.TopStart)
                    .clickable { popUpScreen() },
                painter = painterResource(id = R.drawable.icon_arrow_back),
                contentDescription = "back",
                contentScale = ContentScale.Crop
            )

            Text(
                modifier = modifier
                    .align(Alignment.Center),
                text = type,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = Color.White,
                fontSize = 14.sp
            )

            Image(
                modifier = modifier
                    .padding(20.dp)
                    .align(Alignment.TopEnd)
                    .clickable { isSaved = !isSaved },
                painter = if (isSaved) painterResource(id = R.drawable.icon_saved)
                else painterResource(id = R.drawable.icon_unsaved),
                contentDescription = if (isSaved) "saved"
                else "unsaved",
                contentScale = ContentScale.Crop
            )
        }

        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(30.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {
            Text(
                modifier = modifier
                    .padding(bottom = 5.dp),
                text = title,
                color = Color.White,
                fontSize = 30.sp,
                maxLines = 2,
                fontWeight = FontWeight.Bold
            )

            Row(
                modifier = modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    modifier = modifier
                        .weight(1f),
                    text = creator,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    fontSize = 14.sp,
                    maxLines = 3
                )

                Text(
                    modifier = modifier
                        .padding(start = 10.dp, end = 10.dp),
                    text = "/",
                    color = Color.White,
                    fontSize = 23.sp
                )

                Text(
                    modifier = modifier
                        .weight(1f),
                    text = tags,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    fontSize = 14.sp,
                    maxLines = 3
                )

                Text(
                    modifier = modifier
                        .padding(start = 10.dp, end = 10.dp),
                    text = "/",
                    color = Color.White,
                    fontSize = 23.sp
                )

                Text(
                    modifier = modifier
                        .weight(1f),
                    text = year,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    fontSize = 14.sp,
                    maxLines = 3
                )
            }
        }
    }
}