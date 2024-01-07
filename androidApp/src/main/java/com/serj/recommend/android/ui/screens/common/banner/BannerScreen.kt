package com.serj.recommend.android.ui.screens.common.banner

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.serj.recommend.android.common.ext.bannerContentShape
import com.serj.recommend.android.common.ext.screenPaddingsInner
import com.serj.recommend.android.model.Recommendation
import com.serj.recommend.android.model.items.RecommendationItem
import com.serj.recommend.android.services.BannerResponse
import com.serj.recommend.android.services.model.Response.Failure
import com.serj.recommend.android.services.model.Response.Success
import com.serj.recommend.android.ui.components.loadingIndicators.LargeLoadingIndicator
import com.serj.recommend.android.ui.components.loadingIndicators.SmallLoadingIndicator
import com.serj.recommend.android.ui.components.recommendationPreviews.RecommendationItem
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
    val bannerResponse = viewModel.bannerResponse
    val currentRecommendations = viewModel.currentRecommendations
    val currentRecommendationsAmount = viewModel.currentRecommendationsAmount

    BannerScreenContent(
        modifier = modifier,
        bannerResponse = bannerResponse.value,
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
    bannerResponse: BannerResponse?,
    currentRecommendations: List<MutableState<RecommendationItem>>,
    recommendationsAmount: Int,
    openScreen: (String) -> Unit,
    popUpScreen: () -> Unit,
    onRecommendationClick: ((String) -> Unit, Recommendation) -> Unit
) {
    Scaffold(
        modifier = modifier
    ) { paddingValues ->
        when (bannerResponse) {
            is Success -> {
                val banner = bannerResponse.data
                if (banner != null) {
                    var isLoading by rememberSaveable { mutableStateOf(true) }
                    var currentRecommendationsAmount = 0

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
                                        .padding(top = 15.dp, bottom = 20.dp)
                                ) {
                                    Description(
                                        modifier = Modifier,
                                        description = banner.description,
                                        color = banner.color
                                    )
                                }
                            }
                        }

                        items(currentRecommendations) {
                            RecommendationItem(
                                modifier = Modifier.padding(bottom = 10.dp),
                                user = it.value.userItem,
                                date = it.value.date,
                                description = it.value.description,
                                backgroundImageReference = it.value.backgroundImageReference,
                                backgroundVideoReference = it.value.backgroundVideoReference,
                                title = it.value.title,
                                creator = it.value.creator,
                                coverType = it.value.coverType,
                                coverReference = it.value.coverReference,
                                recommendationId = it.value.id,
                                openScreen = openScreen,
                                onRecommendationClick = onRecommendationClick
                            )

                            currentRecommendationsAmount++
                            isLoading = currentRecommendationsAmount < recommendationsAmount
                        }

                        if (isLoading) {
                            item {
                                SmallLoadingIndicator(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(80.dp),
                                    backgroundColor = White
                                )
                            }
                        }
                    }
                }
            }
            is Failure -> {
                print(bannerResponse.e)
            }
            else -> {
                LargeLoadingIndicator(
                    backgroundColor = White
                )
            }
        }
    }
}