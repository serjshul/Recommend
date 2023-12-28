package com.serj.recommend.android.ui.screens.main.home

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import com.serj.recommend.android.BANNER_ID
import com.serj.recommend.android.CATEGORY_ID
import com.serj.recommend.android.RECOMMENDATION_ID
import com.serj.recommend.android.RecommendRoutes
import com.serj.recommend.android.model.Banner
import com.serj.recommend.android.model.Category
import com.serj.recommend.android.model.Recommendation
import com.serj.recommend.android.model.service.ConfigurationService
import com.serj.recommend.android.model.service.LogService
import com.serj.recommend.android.model.service.StorageService
import com.serj.recommend.android.ui.screens.RecommendViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    logService: LogService,
    private val storageService: StorageService,
    private val configurationService: ConfigurationService
) : RecommendViewModel(logService) {

    private val categories = storageService.categories
    val currentCategories = mutableStateListOf<Category>()

    private val banners = storageService.banners
    val currentBanner = mutableStateOf<Banner?>(null)

    init {
        launchCatching {
            banners.collect { banners ->
                currentBanner.value = banners.random().id?.let { storageService.getBanner(it) }
            }
        }

        launchCatching {
            categories.collect { categories ->
                for (category in categories) {
                    storageService.getCategory(category.id)?.let { currentCategories.add(it) }
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
}