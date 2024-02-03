package com.serj.recommend.android.ui.components.categories

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.serj.recommend.android.common.ext.screenPaddingsInner
import com.serj.recommend.android.model.Category
import com.serj.recommend.android.model.Recommendation
import com.serj.recommend.android.ui.components.recommendationPreviews.transparent.HorizontalItemTransparent

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PagerCategory(
    modifier: Modifier = Modifier,
    category: Category,
    openScreen: (String) -> Unit,
    onRecommendationClick: ((String) -> Unit, Recommendation) -> Unit,
    onCategoryClick: ((String) -> Unit, String) -> Unit
) {
    val items = category.content

    if (items.isNotEmpty() && category.title != null) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(bottom = 30.dp)
        ) {
            Text(
                modifier = Modifier
                    .screenPaddingsInner()
                    .padding(bottom = 10.dp)
                    .clickable { onCategoryClick(openScreen, category.id) },
                text = category.title,
                color = Color.Black,
                fontSize = 22.sp,
                maxLines = 2,
                fontWeight = FontWeight.Bold
            )

            val pageCount = 300
            val pagerState = rememberPagerState(
                initialPage = pageCount / 2,
                pageCount = { pageCount }
            )

            HorizontalPager(
                modifier = Modifier.fillMaxWidth(),
                state = pagerState,
                contentPadding = PaddingValues(start = 20.dp, end = 20.dp)
            ) { page ->
                HorizontalItemTransparent(
                    recommendationId = items[page % items.size].id,
                    title = items[page % items.size].title,
                    creator = items[page % items.size].creator,
                    coverReference = items[page % items.size].coverReference,
                    isOnPager = true,
                    openScreen = openScreen,
                    onRecommendationClick = onRecommendationClick
                )
            }
        }
    }
}