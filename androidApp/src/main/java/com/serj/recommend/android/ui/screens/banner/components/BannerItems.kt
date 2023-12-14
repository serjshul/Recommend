package com.serj.recommend.android.ui.screens.banner.components

import android.graphics.Bitmap
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.serj.recommend.android.model.CategoryItem
import com.serj.recommend.android.model.Recommendation
import com.serj.recommend.android.ui.components.loadingIndicators.SmallLoadingIndicator
import com.serj.recommend.android.ui.screens.banner.components.bannerItems.SquareBannerItem

@Composable
fun BannerItems(
    modifier: Modifier = Modifier,
    coverType: String?,
    color: String?,
    bannerItems: List<CategoryItem?>,
    bannerImages: List<Bitmap?>?,
    openScreen: (String) -> Unit,
    onRecommendationClick: ((String) -> Unit, Recommendation) -> Unit
) {
    Column (
        modifier = modifier
    ) {
        if (bannerItems.isNotEmpty()) {
            for (i in bannerItems.indices) {
                when (coverType) {
                    "square" -> {
                        SquareBannerItem(
                            modifier = Modifier.padding(start = 15.dp, bottom = 15.dp, end = 15.dp),
                            title = bannerItems[i]?.title,
                            creator = bannerItems[i]?.creator,
                            description = bannerItems[i]?.description,
                            color = color,
                            cover = bannerImages?.getOrNull(i),
                            recommendationId = bannerItems[i]?.recommendationId,
                            openScreen = openScreen,
                            onRecommendationClick = onRecommendationClick
                        )
                    }
                    "horizontal" -> {
                        // TODO: write a horizontal item
                    }
                    "vertical" -> {
                        // TODO: write a vertical item
                    }
                    else -> {
                        // TODO: what else?
                    }
                }
            }
        } else {
            SmallLoadingIndicator(
                modifier = Modifier
                    .height(150.dp)
                    .fillMaxWidth()
            )
        }
    }
}