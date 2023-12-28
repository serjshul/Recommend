package com.serj.recommend.android.ui.screens.common.banner.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.serj.recommend.android.R
import com.serj.recommend.android.common.ext.itemsInterval
import com.serj.recommend.android.common.ext.screenPaddingsInner
import com.serj.recommend.android.model.Banner
import com.serj.recommend.android.model.Recommendation
import com.serj.recommend.android.ui.components.items.cards.HorizontalItemCard
import com.serj.recommend.android.ui.components.items.cards.SquareItemCard
import com.serj.recommend.android.ui.components.items.cards.VerticalItemCard
import com.serj.recommend.android.ui.components.loadingIndicators.SmallLoadingIndicator
import com.serj.recommend.android.ui.components.snackbar.SnackbarManager
import com.serj.recommend.android.ui.styles.ItemsShapes
import com.serj.recommend.android.ui.styles.White

@Composable
fun BannerItems(
    modifier: Modifier = Modifier,
    color: String?,
    banner: Banner,
    openScreen: (String) -> Unit,
    onRecommendationClick: ((String) -> Unit, Recommendation) -> Unit
) {
    Column(
        modifier = modifier.screenPaddingsInner()
    ) {
        if (!banner.content.isNullOrEmpty()) {
            // TODO: look what to use instead of cycle - HomeScreen (items(...))
            for (item in banner.content) {
                when (item.coverType) {
                    // TODO: If only one element in { } - decrease level of
                    //  Reducing nesting level (one principle of the Refactoring):
                    //  u can find info about it somewhere,
                    //  or may read this: https://testing.googleblog.com/2017/06/code-health-reduce-nesting-reduce.html
                    //  p.s. I done its here, populate in other places
                    ItemsShapes.square.name ->
                        SquareItemCard(
                            modifier = Modifier.itemsInterval(),
                            recommendationId = item.id,
                            title = item.title,
                            creator = item.creator,
                            description = null,
                            color = color,
                            cover = item.cover,
                            openScreen = openScreen,
                            onRecommendationClick = onRecommendationClick
                        )
                    ItemsShapes.horizontal.name -> {
                        HorizontalItemCard(
                            modifier = Modifier.itemsInterval(),
                            recommendationId = item.id,
                            title = item.title,
                            creator = item.creator,
                            description = null,
                            color = color,
                            cover = item.cover,
                            openScreen = openScreen,
                            onRecommendationClick = onRecommendationClick
                        )
                    }
                    ItemsShapes.vertical.name -> {
                        VerticalItemCard(
                            modifier = Modifier.itemsInterval(),
                            recommendationId = item.id,
                            title = item.title,
                            creator = item.creator,
                            description = null,
                            color = color,
                            cover = item.cover,
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