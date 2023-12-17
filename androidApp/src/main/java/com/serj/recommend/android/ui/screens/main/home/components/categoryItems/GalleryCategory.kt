package com.serj.recommend.android.ui.screens.main.home.components.categoryItems

import android.graphics.Bitmap
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import com.serj.recommend.android.model.Category
import com.serj.recommend.android.model.CategoryItem
import com.serj.recommend.android.model.Recommendation
import com.serj.recommend.android.ui.screens.main.home.components.contentItems.GalleryCategoryItem
import kotlin.math.absoluteValue

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GalleryCategory(
    modifier: Modifier = Modifier,
    items: List<CategoryItem?>?,
    covers: List<Bitmap?>?,
    category: Category,
    openScreen: (String) -> Unit,
    onRecommendationClick: ((String) -> Unit, Recommendation) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(top = 10.dp, bottom = 10.dp)
    ) {
        if (!items.isNullOrEmpty()) {
            val pagerState = rememberPagerState(pageCount = { items.size })

            HorizontalPager(state = pagerState) { page ->
                GalleryCategoryItem(
                    modifier = Modifier
                        .size(200.dp)
                        .graphicsLayer {
                            // Calculate the absolute offset for the current page from the
                            // scroll position. We use the absolute value which allows us to mirror
                            // any effects for both directions
                            val pageOffset = (
                                    (pagerState.currentPage - page) + pagerState
                                        .currentPageOffsetFraction
                                    ).absoluteValue

                            // We animate the alpha, between 50% and 100%
                            alpha = lerp(
                                start = 0.5f,
                                stop = 1f,
                                fraction = 1f - pageOffset.coerceIn(0f, 1f)
                            )
                        },
                    recommendationId = items[page]?.recommendationId,
                    title = items[page]?.title,
                    creator = items[page]?.creator,
                    cover = covers?.getOrNull(page),
                    openScreen = openScreen,
                    onRecommendationClick = onRecommendationClick
                )
            }
        }
    }
}