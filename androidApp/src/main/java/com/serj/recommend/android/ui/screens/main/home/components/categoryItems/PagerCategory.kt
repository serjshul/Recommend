package com.serj.recommend.android.ui.screens.main.home.components.categoryItems

import android.graphics.Bitmap
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.serj.recommend.android.model.Category
import com.serj.recommend.android.model.CategoryItem
import com.serj.recommend.android.model.Recommendation
import com.serj.recommend.android.ui.components.items.transparent.PagerItemTransparent

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PagerCategory(
    modifier: Modifier = Modifier,
    items: List<CategoryItem?>?,
    covers: List<Bitmap?>?,
    category: Category,
    openScreen: (String) -> Unit,
    onRecommendationClick: ((String) -> Unit, Recommendation) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 45.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = modifier
                .padding(start = 15.dp, end = 15.dp, bottom = 10.dp),
            text = category.title,
            color = Color.Black,
            fontSize = 24.sp,
            maxLines = 2,
            fontWeight = FontWeight.Bold,
        )

        if (!items.isNullOrEmpty()) {
            val pageCount = 300
            val pagerState = rememberPagerState(
                initialPage = pageCount / 2,
                pageCount = { pageCount }
            )

            HorizontalPager(
                state = pagerState,
                contentPadding = PaddingValues(start = 20.dp, end = 20.dp)
            ) { page ->
                PagerItemTransparent(
                    recommendationId = items[page % items.size]?.recommendationId,
                    title = items[page % items.size]?.title,
                    creator = items[page % items.size]?.creator,
                    cover = covers?.getOrNull(page % items.size),
                    openScreen = openScreen,
                    onRecommendationClick = onRecommendationClick
                )
            }
        }
    }
}