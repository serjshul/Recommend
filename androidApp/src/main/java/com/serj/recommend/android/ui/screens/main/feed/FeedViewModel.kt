package com.serj.recommend.android.ui.screens.main.feed

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import com.serj.recommend.android.RECOMMENDATION_ID
import com.serj.recommend.android.RecommendRoutes
import com.serj.recommend.android.model.Recommendation
import com.serj.recommend.android.model.items.RecommendationItem
import com.serj.recommend.android.model.service.AccountService
import com.serj.recommend.android.model.service.LogService
import com.serj.recommend.android.model.service.StorageService
import com.serj.recommend.android.ui.screens.RecommendViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(
    logService: LogService,
    private val storageService: StorageService,
    accountService: AccountService
) : RecommendViewModel(logService) {

    private val currentUser = accountService.currentUser
    val currentRecommendations = mutableStateListOf<MutableState<RecommendationItem>>()
    val currentRecommendationsAmount = mutableIntStateOf(0)

    init {
        launchCatching {
            currentUser.collect {user ->
                val followingRecommendationsIds = arrayListOf<Pair<String, Date>>()

                for (followingUid in user.following!!) {
                    followingRecommendationsIds.addAll(
                        storageService.getFollowingRecommendationsIds(followingUid)
                    )
                }
                currentRecommendationsAmount.intValue = followingRecommendationsIds.size
                followingRecommendationsIds.sortByDescending { it.second }

                for (recommendationId in followingRecommendationsIds) {
                    val currentRecommendationItem = mutableStateOf(
                        storageService.getRecommendationItemById(recommendationId.first)
                    )
                    currentRecommendations.add(currentRecommendationItem)

                    currentRecommendationItem.value.cover.value = currentRecommendationItem.value
                        .coverReference?.let { storageService.downloadImage(it) }
                }

                for (recommendationItem in currentRecommendations) {
                    val imageReference = recommendationItem.value.backgroundImageReference
                    if (imageReference != null) {
                        recommendationItem.value.backgroundImage.value =
                            storageService.downloadImage(imageReference)
                    }
                }
            }
        }
    }

    fun onRecommendationClick(openScreen: (String) -> Unit, recommendation: Recommendation) {
        openScreen("${RecommendRoutes.RecommendationScreen.name}?$RECOMMENDATION_ID={${recommendation.id}}")
    }
}