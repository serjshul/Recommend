package com.serj.recommend.android.ui.screens.common.banner

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import com.serj.recommend.android.RecommendRoutes
import com.serj.recommend.android.common.Constants.BANNER_ID
import com.serj.recommend.android.common.Constants.RECOMMENDATION_ID
import com.serj.recommend.android.common.ext.idFromParameter
import com.serj.recommend.android.model.Banner
import com.serj.recommend.android.model.Recommendation
import com.serj.recommend.android.model.items.RecommendationItem
import com.serj.recommend.android.services.GetBannerByIdResponse
import com.serj.recommend.android.services.LogService
import com.serj.recommend.android.services.StorageService
import com.serj.recommend.android.services.model.Response.Success
import com.serj.recommend.android.ui.screens.RecommendViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BannerViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    logService: LogService,
    private val storageService: StorageService
) : RecommendViewModel(logService) {

    val bannerResponse = mutableStateOf<GetBannerByIdResponse?>(null)
    val currentRecommendations = mutableStateListOf<MutableState<RecommendationItem>>()
    val currentRecommendationsAmount = mutableIntStateOf(0)

    init {
        val bannerId = savedStateHandle.get<String>(BANNER_ID)
        if (bannerId != null) {
            launchCatching {
                bannerResponse.value = storageService
                    .getBannerById(bannerId.idFromParameter())

                if (bannerResponse.value is Success) {
                    val currentBanner = (bannerResponse.value as Success<Banner?>).data
                    if (currentBanner?.recommendationIds?.size != null) {
                        currentRecommendationsAmount.intValue =
                            currentBanner.recommendationIds.size
                        for (recommendationId in currentBanner.recommendationIds) {
                            val currentRecommendationItemResponse = storageService
                                .getRecommendationItemById(recommendationId)
                            if (currentRecommendationItemResponse is Success &&
                                currentRecommendationItemResponse.data != null) {
                                currentRecommendations.add(
                                    mutableStateOf(currentRecommendationItemResponse.data)
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    fun onRecommendationClick(openScreen: (String) -> Unit, recommendation: Recommendation) {
        openScreen("${RecommendRoutes.RecommendationScreen.name}?$RECOMMENDATION_ID={${recommendation.id}}")
    }
}