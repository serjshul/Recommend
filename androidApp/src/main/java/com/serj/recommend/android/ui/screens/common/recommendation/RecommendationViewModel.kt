package com.serj.recommend.android.ui.screens.common.recommendation

import android.graphics.Bitmap
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import com.serj.recommend.android.RECOMMENDATION_ID
import com.serj.recommend.android.common.ext.idFromParameter
import com.serj.recommend.android.model.Recommendation
import com.serj.recommend.android.model.service.LogService
import com.serj.recommend.android.model.service.StorageService
import com.serj.recommend.android.ui.screens.RecommendViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RecommendationViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    logService: LogService,
    private val storageService: StorageService
) : RecommendViewModel(logService) {
    val recommendation = mutableStateOf(Recommendation())
    val backgroundImage = mutableStateOf<Bitmap?>(null)
    val paragraphsImages = mutableStateMapOf<Int?, Bitmap?>(null to null)

    init {
        val recommendationId = savedStateHandle.get<String>(RECOMMENDATION_ID)
        if (recommendationId != null) {
            launchCatching {
                recommendation.value = storageService
                    .getRecommendation(recommendationId.idFromParameter()) ?: Recommendation()
                backgroundImage.value = recommendation.value.background["image"]?.let {
                    storageService.downloadImage(it)
                }
                for (i in recommendation.value.paragraphs.indices) {
                    recommendation.value.paragraphs[i]["image"]?.let {
                        paragraphsImages[i] = storageService.downloadImage(it)
                    }
                }
            }
        }
    }
}