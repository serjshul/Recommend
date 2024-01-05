package com.serj.recommend.android.ui.screens.main.home

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import com.serj.recommend.android.RecommendRoutes
import com.serj.recommend.android.common.Constants.BANNER_ID
import com.serj.recommend.android.common.Constants.CATEGORY_ID
import com.serj.recommend.android.common.Constants.RECOMMENDATION_ID
import com.serj.recommend.android.model.Banner
import com.serj.recommend.android.model.Category
import com.serj.recommend.android.model.Recommendation
import com.serj.recommend.android.services.LogService
import com.serj.recommend.android.services.StorageService
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
    val currentCategoriesAmount = mutableIntStateOf(0)

    private val banners = storageService.banners
    val currentBanner = mutableStateOf<Banner?>(null)

    init {
        launchCatching {
            banners.collect { banners ->
                val randomBanner = banners.random()
                randomBanner.coverReference = randomBanner.coverUrl?.let {
                    storageService.getStorageReferenceFromUrl(it)
                }
                currentBanner.value = randomBanner
            }
        }

        launchCatching {
            categories.collect { categories ->
                val shuffledCategories = categories.shuffled()
                currentCategoriesAmount.intValue = shuffledCategories.size

                for (category in shuffledCategories) {
                    val currentCategory = mutableStateOf(category)
                    currentCategories.add(currentCategory)

                    val shuffledRecommendationIds = currentCategory.value
                        .recommendationIds.shuffled()
                    for (i in shuffledRecommendationIds.indices) {
                        if (i < AMOUNT_THRESHOLD) {
                            storageService
                                .getRecommendationPreviewById(shuffledRecommendationIds[i])
                                ?.let {
                                    currentCategory.value.content.add(it)
                                }
                        } else break
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
        const val AMOUNT_THRESHOLD = 6
    }
}