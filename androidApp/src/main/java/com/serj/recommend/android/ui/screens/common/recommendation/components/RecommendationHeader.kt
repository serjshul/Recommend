package com.serj.recommend.android.ui.screens.common.recommendation.components

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.serj.recommend.android.R
import com.serj.recommend.android.ui.styles.Muesli

@Composable
fun RecommendationHeader(
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
    // TODO: save like / unlike by user
    var isSaved by rememberSaveable { mutableStateOf(false) }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(300.dp)
            .background(Muesli)
    ) {
        when {
            backgroundVideo != null -> {
                // TODO: add video player
            }
            backgroundImage != null -> {
                Image(
                    modifier = Modifier
                        .fillMaxSize(),
                    bitmap = backgroundImage.asImageBitmap(),
                    colorFilter = ColorFilter
                        .colorMatrix(ColorMatrix().apply {
                            setToScale(0.7f, 0.7f, 0.7f, 1f)
                        }
                    ),
                    contentDescription = "background_image",
                    contentScale = ContentScale.Crop
                )
            }
            else -> {
                Image(
                    modifier = Modifier
                        .fillMaxSize(),
                    painter = painterResource(id = R.drawable.gradient),
                    contentDescription = "background_gradient",
                    contentScale = ContentScale.Crop
                )
            }
        }

        Column(
            modifier = modifier
                .fillMaxSize()
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
                    contentDescription = "button_back",
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
                    contentDescription = if (isSaved) "button_saved"
                        else "button_unsaved",
                    contentScale = ContentScale.Crop
                )
            }

            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(35.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom
            ) {
                Text(
                    modifier = modifier
                        .padding(bottom = 10.dp),
                    text = title,
                    color = Color.White,
                    fontSize = 24.sp,
                    maxLines = 4,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
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
                        lineHeight = 1.2.em,
                        fontSize = 14.sp,
                        maxLines = 4
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
                        text = tags.joinToString(),
                        overflow = TextOverflow.Ellipsis,
                        textAlign = TextAlign.Center,
                        color = Color.White,
                        lineHeight = 1.2.em,
                        fontSize = 14.sp,
                        maxLines = 4
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
                        text = year.toString(),
                        overflow = TextOverflow.Ellipsis,
                        textAlign = TextAlign.Center,
                        color = Color.White,
                        fontSize = 14.sp,
                        maxLines = 1
                    )
                }
            }
        }
    }
}