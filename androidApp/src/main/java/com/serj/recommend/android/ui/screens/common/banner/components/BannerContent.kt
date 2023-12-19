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
    Column(modifier = modifier) {
        if (!bannerItems.isNullOrEmpty()) {
            // TODO: look what to use instead of cycle - HomeScreen (items(...))
            for (i in bannerItems.indices) {
                when (coverType) {
                    // TODO: If only one element in { } - decrease level of
                    //  Reducing nesting level (one principle of the Refactoring):
                    //  u can find info about it somewhere,
                    //  or may read this: https://testing.googleblog.com/2017/06/code-health-reduce-nesting-reduce.html
                    //  p.s. I done its here, populate in other places
                    COVER_SQUARE -> SquareItemCard(
                        modifier = Modifier.padding(
                            start = 15.dp, end = 15.dp, bottom = 15.dp
                        ),
                        recommendationId = bannerItems[i]?.recommendationId,
                        title = bannerItems[i]?.title,
                        creator = bannerItems[i]?.creator,
                        description = bannerItems[i]?.description,
                        color = color,
                        cover = bannerImages?.get(bannerItems[i]?.title),
                        openScreen = openScreen,
                        onRecommendationClick = onRecommendationClick
                    )


                    COVER_HORIZONTAL -> {
                        HorizontalItemCard(
                            modifier = Modifier.padding(
                                start = 15.dp, end = 15.dp, bottom = 15.dp
                            ),
                            recommendationId = bannerItems[i]?.recommendationId,
                            title = bannerItems[i]?.title,
                            creator = bannerItems[i]?.creator,
                            description = bannerItems[i]?.description,
                            color = color,
                            // TODO: we have .get(), that returns null,
                            //  if can't get item use it everywhere using null as default
                            cover = bannerImages?.getOrDefault(bannerItems[i]?.title, null),
                            openScreen = openScreen,
                            onRecommendationClick = onRecommendationClick
                        )
                    }

                    COVER_VERTICAL -> {
                        VerticalItemCard(
                            modifier = Modifier.padding(
                                start = 15.dp, end = 15.dp, bottom = 15.dp
                            ),
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