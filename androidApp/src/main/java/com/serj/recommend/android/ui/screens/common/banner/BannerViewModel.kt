package com.serj.recommend.android.ui.screens.common.banner

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import com.serj.recommend.android.BANNER_ID
import com.serj.recommend.android.RECOMMENDATION_ID
import com.serj.recommend.android.RecommendRoutes
import com.serj.recommend.android.common.ext.idFromParameter
import com.serj.recommend.android.model.Banner
import com.serj.recommend.android.model.Recommendation
import com.serj.recommend.android.model.items.RecommendationItem
import com.serj.recommend.android.services.LogService
import com.serj.recommend.android.services.StorageService
import com.serj.recommend.android.ui.screens.RecommendViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BannerViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    logService: LogService,
    private val storageService: StorageService
) : RecommendViewModel(logService) {

    val banner = mutableStateOf<Banner?>(null)
    val currentRecommendations = mutableStateListOf<MutableState<RecommendationItem?>>()
    val currentRecommendationsAmount = mutableIntStateOf(0)

    init {
        val bannerId = savedStateHandle.get<String>(BANNER_ID)
        if (bannerId != null) {
            launchCatching {
                val currentBanner = storageService
                    .getBannerById(bannerId.idFromParameter())
                banner.value = currentBanner

                if (banner.value?.recommendationIds?.size != null) {
                    currentRecommendationsAmount.intValue =
                        banner.value?.recommendationIds?.size!!
                }
                for (recommendationId in banner.value?.recommendationIds!!) {
                    val currentRecommendationItem = mutableStateOf(
                        storageService.getRecommendationItemById(recommendationId)
                    )
                    currentRecommendations.add(currentRecommendationItem)
                }
            }
        }
    }

    fun onRecommendationClick(openScreen: (String) -> Unit, recommendation: Recommendation) {
        openScreen("${RecommendRoutes.RecommendationScreen.name}?$RECOMMENDATION_ID={${recommendation.id}}")
    }
}