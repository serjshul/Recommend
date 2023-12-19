package com.serj.recommend.android.ui.screens.common.category

import android.graphics.Bitmap
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import com.serj.recommend.android.CATEGORY_ID
import com.serj.recommend.android.RECOMMENDATION_ID
import com.serj.recommend.android.RecommendRoutes
import com.serj.recommend.android.common.ext.idFromParameter
import com.serj.recommend.android.model.Category
import com.serj.recommend.android.model.CategoryItem
import com.serj.recommend.android.model.Recommendation
import com.serj.recommend.android.model.service.LogService
import com.serj.recommend.android.model.service.StorageService
import com.serj.recommend.android.ui.screens.RecommendViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    logService: LogService,
    private val storageService: StorageService
) : RecommendViewModel(logService) {

    val category = mutableStateOf<Category?>(null)
    val categoryItems = mutableStateListOf<CategoryItem?>()
    val categoryImages = mutableStateMapOf<String?, Bitmap?>()

    init {
        val categoryId = savedStateHandle.get<String>(CATEGORY_ID)
        if (categoryId != null) {
            launchCatching {
                category.value = storageService
                    .getCategory(categoryId.idFromParameter()) ?: Category()

                if (category.value!!.recommendationIds.isNotEmpty()) {
                    for (recommendationId in category.value!!.recommendationIds) {
                        category.value?.coverType?.let {
                            getCategoryItemData(
                                recommendationId = recommendationId,
                                coverType = it
                            )
                        }
                    }
                }
            }
        }
    }

    private fun getCategoryItemData(recommendationId: String, coverType: String) {
        launchCatching {
            storageService
                .getCategoryItems(
                    recommendationId = recommendationId,
                    coverType = coverType)
                . let {
                    categoryItems.add(it)

                    if (it?.cover != null && it.title != null) {
                        getCategoryItemCover(
                            gsReference = it.cover,
                            title = it.title
                        )
                    }
                }
        }
    }

    private fun getCategoryItemCover(gsReference: String, title: String) {
        launchCatching {
            storageService.downloadImage(gsReference). let {
                categoryImages[title] = it
            }
        }
    }

    fun onRecommendationClick(openScreen: (String) -> Unit, recommendation: Recommendation) {
        openScreen("${RecommendRoutes.RecommendationScreen.name}?$RECOMMENDATION_ID={${recommendation.id}}")
    }
}