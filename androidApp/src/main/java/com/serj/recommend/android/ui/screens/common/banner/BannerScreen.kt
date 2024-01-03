package com.serj.recommend.android.ui.screens.common.banner

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.serj.recommend.android.common.ext.bannerContentShape
import com.serj.recommend.android.common.ext.screenPaddingsInner
import com.serj.recommend.android.common.ext.screenPaddingsOuter
import com.serj.recommend.android.model.Banner
import com.serj.recommend.android.model.Recommendation
import com.serj.recommend.android.model.items.RecommendationItem
import com.serj.recommend.android.ui.components.loadingIndicators.LargeLoadingIndicator
import com.serj.recommend.android.ui.screens.common.banner.components.BannerItems
import com.serj.recommend.android.ui.screens.common.banner.components.Description
import com.serj.recommend.android.ui.screens.common.banner.components.Header
import com.serj.recommend.android.ui.styles.White

@Composable
fun BannerScreen(
    modifier: Modifier = Modifier,
    openScreen: (String) -> Unit,
    popUpScreen: () -> Unit,
    viewModel: BannerViewModel = hiltViewModel()
) {
    val banner = viewModel.banner
    val currentRecommendations = viewModel.currentRecommendations
    val currentRecommendationsAmount = viewModel.currentRecommendationsAmount

    BannerScreenContent(
        modifier = modifier,
        banner = banner.value,
        currentRecommendations = currentRecommendations,
        recommendationsAmount = currentRecommendationsAmount.intValue,
        openScreen = openScreen,
        popUpScreen = popUpScreen,
        onRecommendationClick = viewModel::onRecommendationClick
    )
}

@Composable
fun BannerScreenContent(
    modifier: Modifier = Modifier,
    banner: Banner?,
    currentRecommendations: List<MutableState<RecommendationItem?>>,
    recommendationsAmount: Int,
    openScreen: (String) -> Unit,
    popUpScreen: () -> Unit,
    onRecommendationClick: ((String) -> Unit, Recommendation) -> Unit
) {
    Scaffold(
        modifier = modifier
    ) { paddingValues ->
        if (banner != null) {
            LazyColumn(
                modifier = Modifier.padding(paddingValues)
            ) {
                item {
                    Box(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Header(
                            title = banner.title,
                            creator = banner.creator,
                            type = banner.type,
                            backgroundImageReference = banner.backgroundImageReference,
                            backgroundVideoReference = banner.backgroundVideoReference,
                            popUpScreen = popUpScreen
                        )

                        Column(
                            modifier = Modifier
                                .bannerContentShape()
                                .screenPaddingsInner()
                                .screenPaddingsOuter()
                        ) {
                            Description(
                                modifier = Modifier,
                                description = banner.description,
                                color = banner.color
                            )
                        }
                    }
                }
                item {
                    BannerItems(
                        modifier = Modifier
                            .screenPaddingsInner()
                            .screenPaddingsOuter(),
                        currentRecommendations = currentRecommendations,
                        recommendationsAmount = recommendationsAmount,
                        openScreen = openScreen,
                        onRecommendationClick = onRecommendationClick
                    )
                }
            }
        } else {
            LargeLoadingIndicator(
                backgroundColor = White
            )
        }
    }
}