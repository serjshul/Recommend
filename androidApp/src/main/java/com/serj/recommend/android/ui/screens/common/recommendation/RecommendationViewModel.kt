package com.serj.recommend.android.ui.screens.common.recommendation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import com.serj.recommend.android.common.Constants.RECOMMENDATION_ID
import com.serj.recommend.android.common.ext.idFromParameter
import com.serj.recommend.android.model.Recommendation
import com.serj.recommend.android.services.GetRecommendationResponse
import com.serj.recommend.android.services.GetUserItemResponse
import com.serj.recommend.android.services.LogService
import com.serj.recommend.android.services.StorageService
import com.serj.recommend.android.services.model.Response
import com.serj.recommend.android.ui.screens.RecommendViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RecommendationViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    logService: LogService,
    private val storageService: StorageService
) : RecommendViewModel(logService) {

    val getRecommendationResponse = mutableStateOf<GetRecommendationResponse?>(null)
    val getUserItemResponse = mutableStateOf<GetUserItemResponse?>(null)

    init {
        val recommendationId = savedStateHandle.get<String>(RECOMMENDATION_ID)
        if (recommendationId != null) {
            launchCatching {
                getRecommendationResponse.value = storageService
                    .getRecommendationById(
                        recommendationId.idFromParameter()
                    )

                if (getRecommendationResponse.value is Response.Success) {
                    getUserItemResponse.value =
                        (getRecommendationResponse.value as Response.Success<Recommendation?>)
                            .data?.uid?.let { storageService.getUserItemByUid(it)
                    }
                }
            }
        }
    }
}