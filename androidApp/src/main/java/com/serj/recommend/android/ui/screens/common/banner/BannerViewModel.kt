package com.serj.recommend.android.ui.screens.common.banner

import android.graphics.Bitmap
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import com.serj.recommend.android.BANNER_ID
import com.serj.recommend.android.RECOMMENDATION_ID
import com.serj.recommend.android.RecommendRoutes
import com.serj.recommend.android.common.ext.idFromParameter
import com.serj.recommend.android.model.Banner
import com.serj.recommend.android.model.BannerItem
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
    val bannerBackground = mutableStateOf<Bitmap?>(null)
    val bannerItems = mutableStateListOf<BannerItem?>()
    val bannerImages = mutableStateMapOf<String?, Bitmap?>()

    init {
        val bannerId = savedStateHandle.get<String>(BANNER_ID)
        if (bannerId != null) {
            launchCatching {
                banner.value = storageService
                    .getBanner(bannerId.idFromParameter()) ?: Banner()

                banner.value!!.background?.get("image")?.let {
                    getBannerBackground(
                        gsReference = it,
                    )
                }

                if (!banner.value!!.recommendationIds.isNullOrEmpty()) {
                    for (recommendationId in banner.value!!.recommendationIds!!) {
                        banner.value?.coverType?.let {
                            getBannerItemData(
                                recommendationId = recommendationId,
                                coverType = it
                            )
                        }
                    }
                }
            }
        }
    }

    private fun getBannerItemData(recommendationId: String, coverType: String) {
        launchCatching {
            storageService
                .getBannerItem(
                    recommendationId = recommendationId,
                    coverType = coverType)
                . let {
                    bannerItems.add(it)

                    if (it?.cover != null && it.title != null) {
                        getBannerItemCover(
                            gsReference = it.cover,
                            title = it.title
                        )
                    }
                }
        }
    }

    private fun getBannerItemCover(gsReference: String, title: String) {
        launchCatching {
            storageService.downloadImage(gsReference). let {
                bannerImages[title] = it
            }
        }
    }

    private fun getBannerBackground(gsReference: String) {
        launchCatching {
            bannerBackground.value = storageService.downloadImage(gsReference)
        }
    }

    fun onRecommendationClick(openScreen: (String) -> Unit, recommendation: Recommendation) {
        openScreen("${RecommendRoutes.RecommendationScreen.name}?$RECOMMENDATION_ID={${recommendation.id}}")
    }
}