package com.serj.recommend.android.ui.screens.common.category

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import com.serj.recommend.android.CATEGORY_ID
import com.serj.recommend.android.RECOMMENDATION_ID
import com.serj.recommend.android.RecommendRoutes
import com.serj.recommend.android.common.ext.idFromParameter
import com.serj.recommend.android.model.Category
import com.serj.recommend.android.model.Recommendation
import com.serj.recommend.android.model.items.RecommendationItem
import com.serj.recommend.android.repository.LogService
import com.serj.recommend.android.repository.StorageService
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
    val currentRecommendations = mutableStateListOf<MutableState<RecommendationItem>>()
    val currentRecommendationsAmount = mutableIntStateOf(0)

    init {
        val categoryId = savedStateHandle.get<String>(CATEGORY_ID)
        if (categoryId != null) {
            launchCatching {
                val currentCategory = storageService
                    .getCategoryById(categoryId.idFromParameter())
                category.value = currentCategory

                currentRecommendationsAmount.intValue =
                    category.value?.recommendationIds!!.size

                for (recommendationId in category.value?.recommendationIds!!) {
                    val currentRecommendationItem = mutableStateOf(
                        storageService.getRecommendationItemById(recommendationId)
                    )
                    currentRecommendations.add(currentRecommendationItem)

                    currentRecommendationItem.value.cover.value = currentRecommendationItem.value
                        .coverReference?.let { storageService.getImageUrlFromFirestoreResponse(it) }
                }

                for (recommendationItem in currentRecommendations) {
                    val imageReference = recommendationItem.value.backgroundImageReference
                    if (imageReference != null) {
                        recommendationItem.value.backgroundImage.value =
                            storageService.getImageUrlFromFirestoreResponse(imageReference)
                    }
                }
            }
        }
    }

    fun onRecommendationClick(openScreen: (String) -> Unit, recommendation: Recommendation) {
        openScreen("${RecommendRoutes.RecommendationScreen.name}?$RECOMMENDATION_ID={${recommendation.id}}")
    }
}