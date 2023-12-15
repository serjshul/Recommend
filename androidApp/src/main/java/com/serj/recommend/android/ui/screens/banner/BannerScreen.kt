package com.serj.recommend.android.ui.screens.banner

import android.graphics.Bitmap
import android.service.controls.ControlsProviderService.TAG
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.serj.recommend.android.model.Banner
import com.serj.recommend.android.model.BannerItem
import com.serj.recommend.android.model.Recommendation
import com.serj.recommend.android.ui.components.loadingIndicators.LargeLoadingIndicator
import com.serj.recommend.android.ui.screens.banner.components.BannerContent
import com.serj.recommend.android.ui.screens.banner.components.BannerDescription
import com.serj.recommend.android.ui.screens.banner.components.BannerHeader

@Composable
fun BannerScreen(
    openScreen: (String) -> Unit,
    popUpScreen: () -> Unit,
    viewModel: BannerViewModel = hiltViewModel()
) {
    val banner = viewModel.banner
    val bannerBackground = viewModel.bannerBackground.value
    val bannerItems = viewModel.bannerItems
    val bannerImages = viewModel.bannerImages

    Log.v(TAG, bannerItems.joinToString())

    BannerScreenContent(
        banner = banner.value,
        bannerBackgroundImage = bannerBackground,
        bannerItems = bannerItems,
        bannerImages = bannerImages,
        openScreen = openScreen,
        popUpScreen = popUpScreen,
        onRecommendationClick = viewModel::onRecommendationClick
    )
}

@Composable
fun BannerScreenContent(
    modifier: Modifier = Modifier,
    banner: Banner?,
    bannerBackgroundVideo: String? = null,
    bannerBackgroundImage: Bitmap?,
    bannerItems: List<BannerItem?>?,
    bannerImages: Map<String?, Bitmap?>?,
    openScreen: (String) -> Unit,
    popUpScreen: () -> Unit,
    onRecommendationClick: ((String) -> Unit, Recommendation) -> Unit
) {
    Scaffold(
        modifier = modifier
    ) { paddingValues ->
        if (banner != null) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                item {
                    Box(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        BannerHeader(
                            title = banner.title,
                            creator = banner.creator,
                            type = banner.type,
                            bannerBackgroundVideo = bannerBackgroundVideo,
                            bannerBackgroundImage = bannerBackgroundImage,
                            popUpScreen = popUpScreen
                        )

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 380.dp)
                                .background(Color.White, RoundedCornerShape(20.dp))
                        ) {
                            BannerDescription(
                                modifier = Modifier.padding(start = 15.dp, end = 15.dp),
                                description = banner.description,
                                color = banner.color
                            )

                            BannerContent(
                                coverType = banner.coverType,
                                color = banner.color,
                                bannerItems = bannerItems,
                                bannerImages = bannerImages,
                                openScreen = openScreen,
                                onRecommendationClick = onRecommendationClick
                            )
                        }
                    }
                }
            }
        } else {
            LargeLoadingIndicator()
        }
    }
}