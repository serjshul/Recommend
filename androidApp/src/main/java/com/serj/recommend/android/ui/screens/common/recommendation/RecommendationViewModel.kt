package com.serj.recommend.android.ui.screens.common.recommendation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import com.serj.recommend.android.RECOMMENDATION_ID
import com.serj.recommend.android.common.ext.idFromParameter
import com.serj.recommend.android.model.Recommendation
import com.serj.recommend.android.repository.LogService
import com.serj.recommend.android.repository.StorageService
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

    init {
        val recommendationId = savedStateHandle.get<String>(RECOMMENDATION_ID)
        if (recommendationId != null) {
            launchCatching {
                recommendation.value = storageService
                    .getRecommendationById(recommendationId.idFromParameter()) ?: Recommendation()
            }
        }
    }
}