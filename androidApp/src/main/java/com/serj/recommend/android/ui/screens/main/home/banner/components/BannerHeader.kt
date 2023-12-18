package com.serj.recommend.android.ui.screens.main.home.banner.components

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.serj.recommend.android.R
import com.serj.recommend.android.ui.components.loadingIndicators.LargeLoadingIndicator

@Composable
fun BannerHeader(
    modifier: Modifier = Modifier,
    title: String?,
    creator: String?,
    type: ArrayList<String>?,
    bannerBackgroundVideo: String? = null,
    bannerBackgroundImage: Bitmap?,
    popUpScreen: () -> Unit
) {
    var sizeImage by remember { mutableStateOf(IntSize.Zero) }

    Box(
        modifier = modifier
            .height(400.dp)
            .fillMaxWidth()
    ) {
        when {
            bannerBackgroundVideo != null -> {
                // TODO: add video player
            }
            bannerBackgroundImage != null -> {
                Image(
                    modifier = Modifier
                        .matchParentSize()
                        .onGloballyPositioned { sizeImage = it.size },
                    bitmap = bannerBackgroundImage.asImageBitmap(),
                    colorFilter = ColorFilter.colorMatrix(ColorMatrix().apply {
                            setToScale(0.7f, 0.7f, 0.7f, 1f)
                        }),
                    contentDescription = title,
                    contentScale = ContentScale.Crop
                )
            }
            else -> {
                LargeLoadingIndicator(
                    modifier = Modifier.background(Color.LightGray)
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
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
        }

        if (title != null) {
            Column(
                modifier = Modifier
                    .matchParentSize()
                    .padding(start = 10.dp, end = 10.dp, bottom = 35.dp),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier.padding(bottom = 10.dp),
                    text = title,
                    color = Color.White,
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )

                if (creator != null && type != null) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
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
                            text = type.joinToString(),
                            overflow = TextOverflow.Ellipsis,
                            textAlign = TextAlign.Center,
                            color = Color.White,
                            lineHeight = 1.2.em,
                            fontSize = 14.sp,
                            maxLines = 4
                        )
                    }
                }
            }
        }
    }
}