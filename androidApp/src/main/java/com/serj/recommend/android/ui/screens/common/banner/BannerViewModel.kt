package com.serj.recommend.android.ui.screens.common.banner

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import com.serj.recommend.android.BANNER_ID
import com.serj.recommend.android.RECOMMENDATION_ID
import com.serj.recommend.android.RecommendRoutes
import com.serj.recommend.android.common.ext.idFromParameter
import com.serj.recommend.android.model.Banner
import com.serj.recommend.android.model.Recommendation
import com.serj.recommend.android.model.service.LogService
import com.serj.recommend.android.model.service.StorageService
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

    init {
        val bannerId = savedStateHandle.get<String>(BANNER_ID)
        if (bannerId != null) {
            launchCatching {
                banner.value = storageService
                    .getBanner(bannerId.idFromParameter()) ?: Banner()
            }
        }
    }

    fun onRecommendationClick(openScreen: (String) -> Unit, recommendation: Recommendation) {
        openScreen("${RecommendRoutes.RecommendationScreen.name}?$RECOMMENDATION_ID={${recommendation.id}}")
    }
}