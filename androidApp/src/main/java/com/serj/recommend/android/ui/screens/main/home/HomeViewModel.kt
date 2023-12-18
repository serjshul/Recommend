package com.serj.recommend.android.ui.screens.main.home

import android.graphics.Bitmap
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import com.serj.recommend.android.BANNER_ID
import com.serj.recommend.android.RECOMMENDATION_ID
import com.serj.recommend.android.RecommendRoutes
import com.serj.recommend.android.model.Banner
import com.serj.recommend.android.model.CategoryItem
import com.serj.recommend.android.model.Recommendation
import com.serj.recommend.android.model.service.ConfigurationService
import com.serj.recommend.android.model.service.LogService
import com.serj.recommend.android.model.service.StorageService
import com.serj.recommend.android.ui.screens.RecommendViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlin.collections.set

@HiltViewModel
class HomeViewModel @Inject constructor(
    logService: LogService,
    private val storageService: StorageService,
    private val configurationService: ConfigurationService
) : RecommendViewModel(logService) {
    val categories = storageService.categories
    val categoriesBackgrounds = mutableStateMapOf<String?, Bitmap?>()
    val categoriesItems = mutableStateMapOf<String?, List<CategoryItem?>?>()
    val categoriesImages = mutableStateMapOf<String?, List<Bitmap?>?>()

    private val banners = storageService.banners
    val banner = mutableStateOf<Banner?>(null)
    val bannerCover = mutableStateOf<Bitmap?>(null)

    init {
        launchCatching {
            banners.collect { banners ->
                banner.value = banners.random()

                bannerCover.value = banner.value!!.cover?.get("image")?. let {
                    storageService.downloadImage(it)
                }
            }
        }

        launchCatching {
            categories.collect { categories ->
                var currentItems: ArrayList<CategoryItem?>
                var currentRecommendations: List<String?>

                for (category in categories) {
                    if (category.recommendationIds.isNotEmpty()) {
                        currentItems = arrayListOf()
                        currentRecommendations =
                            if (category.recommendationIds.size < 6 || category.type == "pager")
                                category.recommendationIds.shuffled()
                            else
                                category.recommendationIds.shuffled().subList(0, 5)
                        for (i in currentRecommendations.indices) {
                            storageService
                                .getCategoryItem(
                                    recommendationId = currentRecommendations[i],
                                    coverType = category.coverType)
                                . let {
                                    currentItems.add(it)
                                }
                        }
                        categoriesItems[category.title] = currentItems
                    }
                }

                for (category in categories) {
                    if (category.recommendationIds.isNotEmpty()) {
                        val currentImages = arrayListOf<Bitmap?>()
                        for (item in categoriesItems[category.title]!!) {
                            item?.cover?.let { gsReference ->
                                storageService.downloadImage(gsReference). let {
                                    currentImages.add(it)
                                }
                            }
                        }
                        categoriesImages[category.title] = currentImages
                    }

                    if (category.background != null) {
                        categoriesBackgrounds[category.title] =
                            category.background["image"]?.let {reference ->
                                storageService.downloadImage(reference)
                            }
                    }
                }
            }
        }
    }

    fun onRecommendationClick(openScreen: (String) -> Unit, recommendation: Recommendation) {
        openScreen("${RecommendRoutes.RecommendationScreen.name}?$RECOMMENDATION_ID={${recommendation.id}}")
    }

    fun onBannerClick(openScreen: (String) -> Unit, banner: Banner) {
        openScreen("${RecommendRoutes.BannerScreen.name}?$BANNER_ID={${banner.id}}")
    }
}