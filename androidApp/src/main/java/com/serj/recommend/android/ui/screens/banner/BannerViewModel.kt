package com.serj.recommend.android.ui.screens.banner

import android.graphics.Bitmap
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import com.serj.recommend.android.BANNER_ID
import com.serj.recommend.android.RECOMMENDATION_ID
import com.serj.recommend.android.RECOMMENDATION_SCREEN
import com.serj.recommend.android.common.ext.idFromParameter
import com.serj.recommend.android.model.Banner
import com.serj.recommend.android.model.CategoryItem
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
    val bannerItems = mutableListOf<CategoryItem?>()
    val bannerImages = mutableStateListOf<Bitmap?>()

    init {
        val bannerId = savedStateHandle.get<String>(BANNER_ID)
        if (bannerId != null) {
            launchCatching {
                banner.value = storageService
                    .getBanner(bannerId.idFromParameter()) ?: Banner()
                for (recommendationId in banner.value!!.recommendationIds!!) {
                    banner.value!!.coverType?.let {item ->
                        storageService
                            .getCategoryItem(
                                recommendationId = recommendationId,
                                coverType = item
                            )
                            . let {
                                bannerItems.add(it)
                            }
                    }
                }

                bannerBackground.value = banner.value!!.background?.get("image")
                    ?.let { gsReference ->
                    storageService.downloadImage(gsReference)
                }

                for (item in bannerItems) {
                    item?.cover?.let { gsReference ->
                        storageService.downloadImage(gsReference).let {
                            bannerImages.add(it)
                        }
                    }
                }
            }
        }
    }

    fun onRecommendationClick(openScreen: (String) -> Unit, recommendation: Recommendation) {
        openScreen("$RECOMMENDATION_SCREEN?$RECOMMENDATION_ID={${recommendation.id}}")
    }
}