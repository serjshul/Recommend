package com.serj.recommend.android.ui.screens.main.home

import android.content.ContentValues.TAG
import android.content.ContentValues
import android.graphics.Bitmap
import android.util.Log
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import com.serj.recommend.android.BANNER_ID
import com.serj.recommend.android.CATEGORY_ID
import com.serj.recommend.android.RECOMMENDATION_ID
import com.serj.recommend.android.RecommendRoutes
import com.serj.recommend.android.model.Banner
import com.serj.recommend.android.model.CategoryItem
import com.serj.recommend.android.model.Recommendation
import com.serj.recommend.android.model.service.ConfigurationService
import com.serj.recommend.android.model.service.LogService
import com.serj.recommend.android.model.service.StorageService
import com.serj.recommend.android.ui.CATEGORY_PAGER
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

                bannerCover.value = banner.value!!.cover?.get("image")?.let {
                    storageService.downloadImage(it)
                }
            }
        }

        launchCatching {
            categories.collect { categories ->
                var currentRecommendations: List<String?>

                for (category in categories) {
                    if (category.recommendationIds.isNotEmpty()) {
                        categoriesItems[category.title] = arrayListOf()
                        categoriesImages[category.title] = arrayListOf()

                        if (category.background?.get("image") != null) {
                            getCategoryBackground(
                                gsReference = category.background["image"]!!,
                                title = category.title
                            )
                        }

                        currentRecommendations =
                            if (category.recommendationIds.size < 6 || category.type == CATEGORY_PAGER)
                                category.recommendationIds.shuffled()
                            else
                                category.recommendationIds.shuffled().subList(0, 5)

                        for (recommendationId in currentRecommendations) {
                            getCategoryItemData(
                                recommendationId = recommendationId,
                                coverType = category.coverType,
                                title = category.title
                            )
                        }
                    }
                }
            }
        }
    }

    private fun getCategoryItemData(recommendationId: String, coverType: String, title: String) {
        launchCatching {
            storageService
                .getCategoryItems(
                    recommendationId = recommendationId,
                    coverType = coverType)
                .let {
                    categoriesItems[title] = categoriesItems[title]?.plus(it)

                    if (it != null) {
                        it.cover?.let { gsReference ->
                            getCategoryItemCover(
                                gsReference = gsReference,
                                title = title
                            )
                        }
                    }
                }
        }
    }

    private fun getCategoryItemCover(gsReference: String, title: String) {
        launchCatching {
            storageService.downloadImage(gsReference).let {
                Log.v(ContentValues.TAG, it.toString())
                categoriesImages[title] = categoriesImages[title]?.plus(it)
            }
        }
    }

    private fun getCategoryBackground(gsReference: String, title: String) {
        launchCatching {
            categoriesBackgrounds[title] = storageService.downloadImage(gsReference)
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