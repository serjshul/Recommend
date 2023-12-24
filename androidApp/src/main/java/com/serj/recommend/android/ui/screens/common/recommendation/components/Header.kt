package com.serj.recommend.android.ui.screens.common.recommendation.components

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.serj.recommend.android.R
import com.serj.recommend.android.common.ext.mediaHeaderShape
import com.serj.recommend.android.common.ext.recommendationHeaderShape
import com.serj.recommend.android.common.ext.topBarShape
import com.serj.recommend.android.ui.components.media.ImageShaded

@Composable
fun Header(
    modifier: Modifier = Modifier,
    title: String,
    type: String,
    creator: String,
    tags: List<String>,
    year: Int,
    backgroundImage: Bitmap?,
    backgroundVideo: String?,
    popUpScreen: () -> Unit
) {
    Box(
        modifier = modifier.recommendationHeaderShape()
    ) {
        when {
            backgroundVideo != null -> {
                // TODO: add video player
            }
            backgroundImage != null -> {
                ImageShaded(
                    modifier = Modifier.mediaHeaderShape(),
                    image = backgroundImage
                )
            }
            else -> {
                Image(
                    modifier = Modifier.mediaHeaderShape(),
                    painter = painterResource(id = R.drawable.gradient),
                    contentDescription = "header background",
                    contentScale = ContentScale.Crop
                )
            }
        }

        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            HeaderTopBar(
                modifier = Modifier.topBarShape(),
                type = type,
                popUpScreen = popUpScreen
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(35.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom
            ) {
                Text(
                    modifier = Modifier.padding(bottom = 10.dp),
                    text = title,
                    color = Color.White,
                    fontSize = 24.sp,
                    maxLines = 4,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )

                HeaderInfo(
                    modifier = Modifier.fillMaxWidth(),
                    creator = creator,
                    tags = tags,
                    year = year
                )
            }
        }
    }
}

@Composable
fun HeaderTopBar(
    modifier: Modifier = Modifier,
    type: String,
    popUpScreen: () -> Unit
) {
    // TODO: save like / unlike by user
    var isSaved by rememberSaveable { mutableStateOf(false) }

    Box(
        modifier = modifier
    ) {
        Image(
            modifier = Modifier
                .padding(20.dp)
                .align(Alignment.TopStart)
                .clickable { popUpScreen() },
            painter = painterResource(id = R.drawable.icon_arrow_back_white),
            contentDescription = "button_back",
            contentScale = ContentScale.Crop
        )

        Text(
            modifier = Modifier
                .align(Alignment.Center),
            text = type,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            color = Color.White,
            fontSize = 14.sp
        )

        Image(
            modifier = Modifier
                .padding(20.dp)
                .align(Alignment.TopEnd)
                .clickable { isSaved = !isSaved },
            painter = if (isSaved) painterResource(id = R.drawable.icon_saved)
            else painterResource(id = R.drawable.icon_unsaved),
            contentDescription = if (isSaved) "button_saved"
            else "button_unsaved",
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
fun HeaderInfo(
    modifier: Modifier = Modifier,
    creator: String,
    tags: List<String>,
    year: Int
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            modifier = Modifier
                .weight(1f),
            text = creator,
            overflow = TextOverflow.Ellipsis,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            color = Color.White,
            lineHeight = 1.2.em,
            fontSize = 14.sp,
            maxLines = 4
        )

        Text(
            modifier = Modifier
                .padding(start = 10.dp, end = 10.dp),
            text = "/",
            color = Color.White,
            fontSize = 23.sp
        )

        Text(
            modifier = Modifier
                .weight(1f),
            text = tags.joinToString(),
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center,
            color = Color.White,
            lineHeight = 1.2.em,
            fontSize = 14.sp,
            maxLines = 4
        )

        Text(
            modifier = Modifier
                .padding(start = 10.dp, end = 10.dp),
            text = "/",
            color = Color.White,
            fontSize = 23.sp
        )

        Text(
            modifier = Modifier
                .weight(1f),
            text = year.toString(),
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center,
            color = Color.White,
            fontSize = 14.sp,
            maxLines = 1
        )
    }
}

@Preview
@Composable
fun HeaderPreview() {
    Header(
        title = "Title",
        type = "Type",
        creator = "Creator",
        tags = arrayListOf("Tags"),
        year = 2023,
        backgroundImage = null,
        backgroundVideo = null,
        popUpScreen = { }
    )
}