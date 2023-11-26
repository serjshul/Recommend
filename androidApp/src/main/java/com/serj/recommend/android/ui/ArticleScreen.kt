package com.serj.recommend.android.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.serj.recommend.android.R
import com.serj.recommend.datalayer.navigation.Navigation

@Composable
fun ArticleScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    Scaffold {paddingValues ->
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues = paddingValues)
        ) {
            item {
                Header(navController = navController)
            }
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(start = 15.dp, top = 30.dp, end = 15.dp, bottom = 20.dp)
                ){
                    Text(
                        text = "“SAOKO” is the second single from ROSALÍA's MOTOMAMI album, and the first song officially released by her during 2022",
                        color = Color.Black,
                        fontSize = 14.sp
                    )
                }
            }
            items(1) {
                Paragraph()
            }
        }
    }
}

@Composable
fun Header(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    var isSaved by rememberSaveable { mutableStateOf<Boolean>(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(270.dp)
            .paint(
                painterResource(id = R.drawable.background_music_rosalia_saoko),
                colorFilter = ColorFilter.colorMatrix(
                    ColorMatrix().apply {
                        setToScale(0.7f, 0.7f, 0.7f, 1f)
                    }
                ),
                contentScale = ContentScale.Crop
            )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
        ) {
            Image(
                modifier = Modifier
                    .padding(20.dp)
                    .align(Alignment.TopStart)
                    .clickable { navController.popBackStack() },
                painter = painterResource(id = R.drawable.icon_arrow_back),
                contentDescription = "back",
                contentScale = ContentScale.Crop
            )

            Image(
                modifier = Modifier
                    .padding(20.dp)
                    .align(Alignment.TopEnd)
                    .clickable { isSaved = !isSaved },
                painter = if (isSaved)
                    painterResource(id = R.drawable.icon_saved)
                else
                    painterResource(id = R.drawable.icon_unsaved),
                contentDescription = "Unsaved",
                contentScale = ContentScale.Crop
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(15.dp)
                .align(Alignment.CenterHorizontally),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {
            Text(
                text = "Saoko",
                color = Color.White,
                fontSize = 30.sp,
                maxLines = 2,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.size(5.dp))

            Row {
                Text(
                    text = "ROSALÍA",
                    color = Color.White,
                    fontSize = 14.sp,
                    maxLines = 2,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.size(10.dp))

                Text(
                    text = "/",
                    color = Color.White,
                    fontSize = 14.sp,
                    maxLines = 2
                )

                Spacer(modifier = Modifier.size(10.dp))

                Text(
                    text = "Latin Urbano",
                    color = Color.White,
                    fontSize = 14.sp,
                    maxLines = 2
                )

                Spacer(modifier = Modifier.size(10.dp))

                Text(
                    text = "/",
                    color = Color.White,
                    fontSize = 14.sp,
                    maxLines = 2
                )

                Spacer(modifier = Modifier.size(10.dp))

                Text(
                    text = "2022",
                    color = Color.White,
                    fontSize = 14.sp,
                    maxLines = 2
                )
            }
        }
    }
}

@Composable
fun Paragraph() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(start = 15.dp, end = 15.dp, bottom = 25.dp)
    ){
        Text(
            text = "Background",
            color = colorResource(id = R.color.apple_red),
            fontSize = 26.sp,
            maxLines = 2,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.size(20.dp))

        Text(
            text = "\"Saoko\" was first mentioned in November 2021, in a Rolling Stone article by Diego Ortiz that covered the emancipation of Rosalía and the recording process of her album Motomami. It was also revealed to be the opening track of the album. The singer previewed the song on TikTok on 29 December.\n" +
                    "\n" +
                    "Upon unveiling the cover art of the album, the singer announced a new song to be released on 4 February. A teaser of the music video for \"Saoko\" was posted on 2 February, indicating the release of the song that week.\n" +
                    "\n" +
                    "This song is set to be used on FIFA 23's soundtrack, which was released on 30 September 2022. Due to players that pre-ordered the game via Xbox getting access to the game earlier than the official release date accidentally, the entire soundtrack has been leaked early to the game's release, confirming \"Saoko\" being part of it.",
            color = Color.Black,
            fontSize = 14.sp
        )
    }
}