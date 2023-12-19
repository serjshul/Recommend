package com.serj.recommend.android.ui.screens.common.banner.components

import android.graphics.Bitmap
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.serj.recommend.android.R
import com.serj.recommend.android.model.BannerItem
import com.serj.recommend.android.model.Recommendation
import com.serj.recommend.android.ui.COVER_HORIZONTAL
import com.serj.recommend.android.ui.COVER_SQUARE
import com.serj.recommend.android.ui.COVER_VERTICAL
import com.serj.recommend.android.ui.components.items.cards.HorizontalItemCard
import com.serj.recommend.android.ui.components.items.cards.SquareItemCard
import com.serj.recommend.android.ui.components.items.cards.VerticalItemCard
import com.serj.recommend.android.ui.components.loadingIndicators.SmallLoadingIndicator
import com.serj.recommend.android.ui.components.snackbar.SnackbarManager
import com.serj.recommend.android.ui.styles.White

@Composable
fun BannerContent(
    modifier: Modifier = Modifier,
    coverType: String?,
    color: String?,
    bannerItems: List<BannerItem?>?,
    bannerImages: Map<String?, Bitmap?>?,
    openScreen: (String) -> Unit,
    onRecommendationClick: ((String) -> Unit, Recommendation) -> Unit
) {
    Column (
        modifier = modifier
    ) {
        if (!bannerItems.isNullOrEmpty()) {
            for (i in bannerItems.indices) {
                when (coverType) {
                    COVER_SQUARE -> {
                        SquareItemCard(
                            modifier = Modifier.padding(start = 15.dp, end = 15.dp, bottom = 15.dp),
                            recommendationId = bannerItems[i]?.recommendationId,
                            title = bannerItems[i]?.title,
                            creator = bannerItems[i]?.creator,
                            description = bannerItems[i]?.description,
                            color = color,
                            cover = bannerImages?.getOrDefault(bannerItems[i]?.title, null),
                            openScreen = openScreen,
                            onRecommendationClick = onRecommendationClick
                        )
                    }
                    COVER_HORIZONTAL -> {
                        HorizontalItemCard(
                            modifier = Modifier.padding(start = 15.dp, end = 15.dp, bottom = 15.dp),
                            recommendationId = bannerItems[i]?.recommendationId,
                            title = bannerItems[i]?.title,
                            creator = bannerItems[i]?.creator,
                            description = bannerItems[i]?.description,
                            color = color,
                            cover = bannerImages?.getOrDefault(bannerItems[i]?.title, null),
                            openScreen = openScreen,
                            onRecommendationClick = onRecommendationClick
                        )
                    }
                    COVER_VERTICAL -> {
                        VerticalItemCard(
                            modifier = Modifier.padding(start = 15.dp, end = 15.dp, bottom = 15.dp),
                            recommendationId = bannerItems[i]?.recommendationId,
                            title = bannerItems[i]?.title,
                            creator = bannerItems[i]?.creator,
                            description = bannerItems[i]?.description,
                            color = color,
                            cover = bannerImages?.getOrDefault(bannerItems[i]?.title, null),
                            openScreen = openScreen,
                            onRecommendationClick = onRecommendationClick
                        )
                    }
                    else -> {
                        SnackbarManager.showMessage(R.string.error_cover_type)
                    }
                }
            }
        } else {
            SmallLoadingIndicator(
                modifier = Modifier
                    .height(135.dp)
                    .fillMaxWidth(),
                backgroundColor = White
            )
        }
    }
}