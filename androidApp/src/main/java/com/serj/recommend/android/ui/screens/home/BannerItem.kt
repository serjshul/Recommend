package com.serj.recommend.android.ui.screens.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.serj.recommend.android.R


data class BannerItemData(
    var title: String,
    var description: String,
    var cover: Int
)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BannerItem(
    banners: ArrayList<BannerItemData>
) {
    Box() {
        val pagerState = rememberPagerState(pageCount = { banners.size })
        HorizontalPager(
            state = pagerState,
            verticalAlignment = Alignment.Top
        ) { page ->
            BannerLayout(banners[page])
        }
        BannerIndicator(pagerState = pagerState)
    }
}

@Composable
fun BannerLayout(banner: BannerItemData) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp)
            .paint(
                painterResource(id = banner.cover),
                contentScale = ContentScale.Crop,
            ),
        verticalArrangement = Arrangement.Bottom,
    ) {
        Text(
            text = banner.title,
            modifier = Modifier.padding(start = 15.dp, end = 15.dp),
            color = Color.White,
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.size(5.dp))

        Text(
            text = banner.description,
            modifier = Modifier.padding(start = 15.dp, end = 15.dp),
            color = Color.White,
            fontSize = 14.sp
        )

        Spacer(modifier = Modifier.size(5.dp))

        OutlinedButton(
            onClick = {},
            border= BorderStroke(1.dp, Color.White),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 15.dp, bottom = 10.dp, end = 15.dp),
        ) {
            Text(
                text = "Read the article",
                color = Color.White,
                fontSize = 14.sp,
                maxLines = 3
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BannerIndicator(pagerState: PagerState) {
    Row(
        Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .padding(start = 15.dp, top = 220.dp),
        horizontalArrangement = Arrangement.Start
    ) {
        repeat(pagerState.pageCount) { iteration ->
            val color = if (pagerState.currentPage == iteration) colorResource(id = R.color.muesli) else Color.LightGray
            if (pagerState.currentPage == iteration) {
                Box(
                    modifier = Modifier
                        .padding(2.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(color)
                        .size(30.dp, 8.dp)
                )
            } else {
                Box(
                    modifier = Modifier
                        .padding(2.dp)
                        .clip(CircleShape)
                        .background(color)
                        .size(8.dp)
                )
            }
        }
    }
}
