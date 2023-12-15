package com.serj.recommend.android.ui.screens.home

import android.graphics.Bitmap
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import com.serj.recommend.android.RECOMMENDATION_ID
import com.serj.recommend.android.RECOMMENDATION_SCREEN
import com.serj.recommend.android.model.CategoryItem
import com.serj.recommend.android.model.Recommendation
import com.serj.recommend.android.model.service.Banner
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
    val options = mutableStateOf<List<String>>(listOf())

    val categories = storageService.categories
    val categoriesBackgrounds = mutableStateMapOf<String?, Bitmap?>()
    val categoriesItems = mutableStateMapOf<String?, List<CategoryItem?>?>()
    val categoriesImages = mutableStateMapOf<String?, List<Bitmap?>?>()

    private val banners = storageService.banners
    val banner = mutableStateOf<Banner?>(null)
    val bannerBackground = mutableStateOf<Bitmap?>(null)
    val bannerItems = mutableListOf<CategoryItem?>()

    init {
        launchCatching {
            banners.collect { banners ->
                banner.value = banners.random()

                if (!banner.value!!.recommendationIds.isNullOrEmpty()) {
                    for (recommendationId in banner.value!!.recommendationIds!!) {
                        banner.value!!.coverType?.let {
                            storageService
                                .getCategoryItem(recommendationId, it)
                                .let { item ->
                                    bannerItems.add(item)
                                }
                        }
                    }
                }

                bannerBackground.value = banner
                    .value!!.background?.get("image")?.let {
                        storageService.downloadImage(it)
                    }
            }
        }

        launchCatching {
            categories.collect { categories ->
                for (category in categories) {
                    if (category.recommendationIds.isNotEmpty()) {
                        val currentItems = arrayListOf<CategoryItem?>()
                        for (recommendationId in category.recommendationIds) {
                            storageService
                                .getCategoryItem(
                                    recommendationId = recommendationId,
                                    coverType = category.coverType
                                ).let {
                                    currentItems.add(it)
                                }
                        }
                        categoriesItems[category.title] = currentItems
                            .sortedByDescending { item ->
                                item?.date
                            }
                    }
                }

                for (category in categories) {
                    if (category.recommendationIds.isNotEmpty()) {
                        val currentImages = arrayListOf<Bitmap?>()
                        for (item in categoriesItems[category.title]!!) {
                            item?.cover?.let { gsReference ->
                                storageService
                                    .downloadImage(gsReference)
                                    .let {
                                        currentImages.add(it)
                                    }
                            }
                        }
                        categoriesImages[category.title] = currentImages
                    }

                    if (category.background != null) {
                        categoriesBackgrounds[category.title] = category
                            .background["image"]?.let { reference ->
                            storageService.downloadImage(reference)
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