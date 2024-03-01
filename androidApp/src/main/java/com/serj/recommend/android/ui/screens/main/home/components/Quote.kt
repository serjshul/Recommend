package com.serj.recommend.android.ui.screens.main.home.components

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.serj.recommend.android.R
import com.serj.recommend.android.ui.styles.background

@Composable
fun Quote(
    modifier: Modifier,
    type: String,
    title: String,
    creator: String,
    quote: String,
    color: Color?
) {
    val isCurrentlyLiked = remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(15.dp, 12.5.dp)
            .clip(RoundedCornerShape(20.dp))
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(230.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.background),
                contentDescription = "Background",
                contentScale = ContentScale.Crop,
                colorFilter = ColorFilter
                    .colorMatrix(ColorMatrix().apply {
                        setToScale(0.75f, 0.75f, 0.75f, 1f)
                    }),
                modifier = Modifier
                    .fillMaxSize()
            )
            Text(
                modifier = Modifier
                    .padding(top = 10.dp)
                    .align(Alignment.TopCenter),
                text = type,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp)
                    .align(Alignment.BottomCenter),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(8f)
                ) {
                    Text(
                        text = title,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        lineHeight = 1.4.em,
                        maxLines = 2,
                        modifier = Modifier
                            .padding(bottom = 3.dp)
                    )
                    Text(
                        text = creator,
                        color = Color.White,
                        fontSize = 12.sp,
                        lineHeight = 1.4.em,
                        maxLines = 2,
                        modifier = Modifier
                    )
                }
                IconToggleButton(
                    modifier = Modifier.weight(1f),
                    checked = isCurrentlyLiked.value,
                    onCheckedChange = {
                        isCurrentlyLiked.value = !isCurrentlyLiked.value
                    }
                ) {
                    val transition = updateTransition(isCurrentlyLiked.value, label = "likeTransition")
                    val tint by transition.animateColor(label = "likeTint") { isLiked ->
                        if (isLiked) Color.Red else Color.White
                    }
                    val size by transition.animateDp(
                        transitionSpec = {
                            if (false isTransitioningTo true) {
                                keyframes {
                                    durationMillis = 250
                                    29.dp at 0 with LinearOutSlowInEasing
                                    32.dp at 15 with FastOutLinearInEasing
                                    35.dp at 75
                                    32.dp at 150
                                }
                            } else {
                                spring(stiffness = Spring.StiffnessVeryLow)
                            }
                        },
                        label = "likeSize"
                    ) { if (it) 29.dp else 29.dp }

                    Icon(
                        ImageVector.vectorResource(
                            id =
                            if (isCurrentlyLiked.value)
                                R.drawable.interaction_like_filled
                            else
                                R.drawable.interaction_like_bordered
                        ),
                        contentDescription = "like",
                        tint = tint,
                        modifier = Modifier.size(size)
                    )
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color ?: background)
        ) {
            Text(
                text = "Quote",
                color = Color.White,
                textAlign = TextAlign.Center,
                fontSize = 12.sp,
                lineHeight = 1.4.em,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp, bottom = 5.dp)
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 15.dp, end = 15.dp, bottom = 15.dp),
                text = quote.replace("\\n", "\n"),
                fontWeight = FontWeight.Bold,
                color = Color.White,
                fontSize = 16.sp
            )
        }
    }
}

@Preview
@Composable
fun QuotePreview() {
    Quote(
        type = "Music",
        title = "Beautiful Things",
        creator = "Benson Boone",
        quote = "Please stay\\n" +
                "I want you, I need you, oh God\\n" +
                "Don't take\\n" +
                "These beautiful things that I've got",
        color = Color(0xFF6d3b30),
        modifier = Modifier
    )
}