package com.serj.recommend.android.ui.screens.home

import android.content.ContentValues.TAG
import android.graphics.Bitmap
import android.util.Log
import androidx.compose.runtime.mutableStateMapOf
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
    val categoriesImages = mutableStateMapOf<String?, List<Bitmap?>?>(null to null)

    init {
        launchCatching {
            categories.collect { categories ->
                for (category in categories) {
                    val images = arrayListOf<Bitmap?>()
                    for (item in category.content) {
                        images.add(
                            item["coverReference"]?.let { storageService.downloadImage(it) }
                        )
                    }
                    categoriesImages[category.title] = images
                    Log.v(TAG, images.joinToString())
                }
            }
        }
    }

    fun onRecommendationClick(openScreen: (String) -> Unit, recommendation: Recommendation) {
        openScreen("$RECOMMENDATION_SCREEN?$RECOMMENDATION_ID={${recommendation.id}}")
    }
}