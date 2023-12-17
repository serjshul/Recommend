package com.serj.recommend.android.ui.screens.common.banner.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.serj.recommend.android.ui.components.loadingIndicators.SmallLoadingIndicator
import com.serj.recommend.android.ui.screens.common.recommendation.components.getParagraphTexts
import com.serj.recommend.android.ui.screens.common.recommendation.components.toColor
import com.serj.recommend.android.ui.styles.AppleRed

@Composable
fun BannerDescription(
    modifier: Modifier = Modifier,
    description: String?,
    color: String?
) {
    var sizeImage by remember { mutableStateOf(IntSize.Zero) }
    var isOpened by rememberSaveable { mutableStateOf(false) }

    if (description != null) {
        val paragraphs = getParagraphTexts(description)
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 15.dp, bottom = 25.dp)
                .onGloballyPositioned { sizeImage = it.size },
        ) {
            Box(
                modifier = if (isOpened) Modifier
                else Modifier.height(100.dp)
            ) {
                Column {
                    for (i in paragraphs.indices) {
                        Text(
                            modifier = if (i != paragraphs.size - 1) Modifier.padding(bottom = 12.dp)
                            else Modifier,
                            text = paragraphs[i],
                            color = Color.Black,
                            fontSize = 14.sp
                        )
                    }
                }

                if (!isOpened) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(Color.Transparent, Color.White),
                                    startY = sizeImage.height.toFloat() / 7,
                                    endY = sizeImage.height.toFloat()
                                )
                            )
                    )
                }
            }

            if (!isOpened) {
                Text(
                    modifier = Modifier
                        .padding(top = 2.dp)
                        .clickable { isOpened = true },
                    text = "Read all",
                    color = color?.toColor() ?: AppleRed,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
            } else {
                Text(
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .clickable { isOpened = false },
                    text = "Close",
                    color = color?.toColor() ?: AppleRed,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
            }
        }
    } else {
        Box(
            modifier = Modifier
                .height(100.dp)
                .fillMaxWidth()
        ) {
            SmallLoadingIndicator()
        }
    }
}