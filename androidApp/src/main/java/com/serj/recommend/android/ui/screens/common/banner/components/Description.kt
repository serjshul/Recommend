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
import com.serj.recommend.android.common.ext.toColor
import com.serj.recommend.android.common.ext.toParagraphText
import com.serj.recommend.android.ui.components.loadingIndicators.SmallLoadingIndicator
import com.serj.recommend.android.ui.components.text.TextParagraphs
import com.serj.recommend.android.ui.styles.primary
import com.serj.recommend.android.ui.styles.White

@Composable
fun Description(
    modifier: Modifier = Modifier,
    description: String?,
    color: String?
) {
    var textSize by remember { mutableStateOf(IntSize.Zero) }
    var isOpened by rememberSaveable { mutableStateOf(false) }

    if (description != null) {
        val paragraphs = description.toParagraphText()

        Column(
            modifier = modifier
                .fillMaxWidth()
                .onGloballyPositioned { textSize = it.size },
        ) {
            Box(
                modifier = if (isOpened) Modifier
                else Modifier.height(100.dp)
            ) {
                TextParagraphs(
                    paragraphTexts = paragraphs
                )

                if (!isOpened) {
                    TextHider(
                        textSize = textSize
                    )
                }
            }

            if (!isOpened) {
                Text(
                    modifier = Modifier
                        .padding(top = 2.dp)
                        .clickable { isOpened = true },
                    text = "Read all",
                    color = color?.toColor() ?: primary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
            } else {
                Text(
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .clickable { isOpened = false },
                    text = "Close",
                    color = color?.toColor() ?: primary,
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
            SmallLoadingIndicator(
                backgroundColor = White
            )
        }
    }
}

@Composable
fun TextHider(
    textSize: IntSize
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color.Transparent, Color.White),
                    startY = textSize.height.toFloat() / 7,
                    endY = textSize.height.toFloat()
                )
            )
    )
}