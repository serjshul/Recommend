package com.serj.recommend.android.ui.screens.main.home

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import com.serj.recommend.android.BANNER_ID
import com.serj.recommend.android.CATEGORY_ID
import com.serj.recommend.android.RECOMMENDATION_ID
import com.serj.recommend.android.RecommendRoutes
import com.serj.recommend.android.model.Banner
import com.serj.recommend.android.model.Category
import com.serj.recommend.android.model.Recommendation
import com.serj.recommend.android.model.service.LogService
import com.serj.recommend.android.model.service.StorageService
import com.serj.recommend.android.ui.screens.RecommendViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    logService: LogService,
    private val storageService: StorageService
) : RecommendViewModel(logService) {

    private val categories = storageService.categories
    val currentCategories = mutableStateListOf<MutableState<Category>>()

    private val banners = storageService.banners
    val currentBanner = mutableStateOf<Banner?>(null)

    init {
        launchCatching {
            banners.collect { banners ->
                currentBanner.value = banners.random()

                currentBanner.value!!.cover = currentBanner.value!!
                    .coverReference?.let {
                        storageService.downloadImage(it)
                    }
            }
        }

        launchCatching {
            categories.collect { categories ->
                val shuffledCategories = categories.shuffled()

                for (category in shuffledCategories) {
                    val currentCategory = mutableStateOf(category)
                    currentCategories.add(currentCategory)

                    val shuffledRecommendationIds = currentCategory.value
                        .recommendationIds.shuffled()
                    for (i in shuffledRecommendationIds.indices) {
                        if (i < AMOUNT_THRESHOLD) {
                            storageService.getRecommendationPreviewById(shuffledRecommendationIds[i])
                                ?.let { currentCategory.value.content!!.add(it) }
                        } else {
                            break
                        }
                    }

                    // TODO: move in second thread for the parallel downloading
                    for (item in currentCategory.value.content!!) {
                        item.cover = item.coverReference?.let {
                            storageService.downloadImage(it)
                        }
                    }
                }
            }
        }
    }

    fun onRecommendationClick(openScreen: (String) -> Unit, recommendation: Recommendation) {
        openScreen("${RecommendRoutes.RecommendationScreen.name}?$RECOMMENDATION_ID={${recommendation.id}}")
    }

    fun onBannerClick(openScreen: (String) -> Unit, bannerId: String) {
        openScreen("${RecommendRoutes.BannerScreen.name}?$BANNER_ID={${bannerId}}")
    }

    fun onCategoryClick(openScreen: (String) -> Unit, categoryId: String) {
        openScreen("${RecommendRoutes.CategoryScreen.name}?$CATEGORY_ID={${categoryId}}")
    }

    companion object {
        private const val AMOUNT_THRESHOLD = 6
    }
}