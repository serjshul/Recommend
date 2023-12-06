package com.serj.recommend.android.ui.screens.home

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import com.serj.recommend.android.RECOMMENDATION_ID
import com.serj.recommend.android.RECOMMENDATION_SCREEN
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
    private val configurationService: ConfigurationService,
    savedStateHandle: SavedStateHandle
) : RecommendViewModel(logService) {
    val options = mutableStateOf<List<String>>(listOf())

    val categories = storageService.categories

    val image = mutableStateOf("")

    fun download() {
        launchCatching {
            image.value =
                storageService.downloadImage("gs://recommend-27827.appspot.com/recommendations/covers/cover_Peggy_gou_Nanana.jpg")
        }
    }

    fun onRecommendationClick(openScreen: (String) -> Unit, recommendation: Recommendation) {
        openScreen("$RECOMMENDATION_SCREEN?$RECOMMENDATION_ID={${recommendation.id}}")
    }
}