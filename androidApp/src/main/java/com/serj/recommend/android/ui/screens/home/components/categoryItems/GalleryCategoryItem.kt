package com.serj.recommend.android.ui.screens.home.components.categoryItems

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.PagerSnapDistance
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import com.serj.recommend.android.R
import com.serj.recommend.android.ui.styles.AppleRed
import com.serj.recommend.android.ui.styles.Muesli
import kotlin.math.absoluteValue


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GalleryCategoryItem(
    title: String
) {
    Column(
        modifier = Modifier
            .padding(top = 10.dp, bottom = 10.dp)
    ) {
        Text(
            modifier = Modifier
                .padding(start = 15.dp, top = 15.dp, end = 15.dp)
                .align(Alignment.CenterHorizontally),
            text = title,
            color = AppleRed,
            fontSize = 26.sp,
            maxLines = 2,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.size(5.dp))

        val pagerState = rememberPagerState(pageCount = { 1 })
        val fling = PagerDefaults.flingBehavior(
            state = pagerState,
            pagerSnapDistance = PagerSnapDistance.atMost(1)
        )
        HorizontalPager(
            state = pagerState,
            beyondBoundsPageCount = 10,
            flingBehavior = fling
        ) { page ->
            MediaGalleryItem(pagerState, page)
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MediaGalleryItem(pagerState: PagerState, page: Int) {
    Column(
        modifier = Modifier
            .width(390.dp)
            .padding(start = 15.dp, end = 15.dp)
            .graphicsLayer {
                val pageOffset = (
                        (pagerState.currentPage - page) + pagerState
                            .currentPageOffsetFraction
                        ).absoluteValue

                alpha = lerp(
                    start = 1f,
                    stop = 1f,
                    fraction = 1f - pageOffset.coerceIn(0f, 1f)
                )
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier
                .height(210.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp)),
            painter = painterResource(id = R.drawable.cover_media_never_have_i_ever),
            contentDescription = "",
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.size(5.dp))

        Text(
            text = "media.title",
            color = Color.Black,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )

        Row {
            Text(
                text = "media.type",
                color = Color.Black,
                fontSize = 12.sp
            )

            Spacer(modifier = Modifier.size(10.dp))

            Text(
                text = "media.production",
                color = Muesli,
                fontSize = 12.sp
            )
        }
    }
}