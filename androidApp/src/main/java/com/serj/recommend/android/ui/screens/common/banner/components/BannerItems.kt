package com.serj.recommend.android.ui.screens.common.banner.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.serj.recommend.android.model.Recommendation
import com.serj.recommend.android.model.items.RecommendationItem
import com.serj.recommend.android.ui.components.loadingIndicators.SmallLoadingIndicator
import com.serj.recommend.android.ui.components.post.RecommendationItemWithBackground
import com.serj.recommend.android.ui.components.post.RecommendationItemWithoutBackground
import com.serj.recommend.android.ui.styles.White

@Composable
fun BannerItems(
    modifier: Modifier = Modifier,
    currentRecommendations: List<MutableState<RecommendationItem>>,
    recommendationsAmount: Int,
    openScreen: (String) -> Unit,
    onRecommendationClick: ((String) -> Unit, Recommendation) -> Unit
) {
    if (currentRecommendations.isNotEmpty()) {
        var isLoading by rememberSaveable { mutableStateOf(true) }
        var currentCategoriesAmount = 0

        Column (
            modifier = modifier
        ) {
            for (it in currentRecommendations) {
                val recommendationItem = it.value

                if (recommendationItem.backgroundImage.value != null ||
                    recommendationItem.backgroundVideo != null) {
                    RecommendationItemWithBackground(
                        modifier = Modifier.padding(bottom = 15.dp),
                        user = recommendationItem.user,
                        date = recommendationItem.date,
                        description = recommendationItem.description,
                        backgroundImage = recommendationItem.backgroundImage.value,
                        title = recommendationItem.title,
                        creator = recommendationItem.creator,
                        coverType = recommendationItem.coverType,
                        cover = recommendationItem.cover.value,
                        recommendationId = recommendationItem.id,
                        openScreen = openScreen,
                        onRecommendationClick = onRecommendationClick
                    )
                } else {
                    RecommendationItemWithoutBackground(
                        modifier = Modifier.padding(bottom = 15.dp),
                        user = recommendationItem.user,
                        date = recommendationItem.date,
                        description = recommendationItem.description,
                        title = recommendationItem.title,
                        creator = recommendationItem.creator,
                        coverType = recommendationItem.coverType,
                        cover = recommendationItem.cover.value,
                        recommendationId = recommendationItem.id,
                        openScreen = openScreen,
                        onRecommendationClick = onRecommendationClick
                    )
                }

                currentCategoriesAmount++
                isLoading = currentCategoriesAmount < recommendationsAmount
            }

            if (isLoading) {
                SmallLoadingIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp),
                    backgroundColor = White
                )
            }
        }
    } else {
        SmallLoadingIndicator(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            backgroundColor = White
        )
    }
}