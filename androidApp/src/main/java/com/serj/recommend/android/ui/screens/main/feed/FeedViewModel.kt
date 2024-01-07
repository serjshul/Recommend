package com.serj.recommend.android.ui.screens.main.feed

import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import com.serj.recommend.android.R
import com.serj.recommend.android.RecommendRoutes
import com.serj.recommend.android.common.Constants.RECOMMENDATION_ID
import com.serj.recommend.android.model.Recommendation
import com.serj.recommend.android.model.items.RecommendationItem
import com.serj.recommend.android.services.AccountService
import com.serj.recommend.android.services.LogService
import com.serj.recommend.android.services.StorageService
import com.serj.recommend.android.services.model.Response
import com.serj.recommend.android.ui.components.snackbar.SnackbarManager
import com.serj.recommend.android.ui.screens.RecommendViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(
    logService: LogService,
    private val storageService: StorageService,
    private val accountService: AccountService
) : RecommendViewModel(logService) {

    val currentRecommendations = mutableStateListOf<RecommendationItem>()
    val currentRecommendationsAmount = mutableIntStateOf(0)

    init {
        launchCatching {
            accountService.currentUser.collect {user ->
                if (user.following != null) {
                    val followingRecommendationsIdsResponse =
                        storageService.getFollowingRecommendationsIds(user.following)
                    when (followingRecommendationsIdsResponse) {
                        is Response.Success -> {
                            if (followingRecommendationsIdsResponse.data != null) {
                                currentRecommendationsAmount.intValue =
                                    followingRecommendationsIdsResponse.data.size
                                for (followingId in followingRecommendationsIdsResponse.data) {
                                    val recommendationItemResponse = storageService
                                        .getRecommendationItemById(followingId)
                                    if (recommendationItemResponse is Response.Success &&
                                        recommendationItemResponse.data != null) {
                                        currentRecommendations.add(
                                            recommendationItemResponse.data
                                        )
                                    }
                                }
                            }
                        }
                        else -> {
                            SnackbarManager.showMessage(R.string.error_feed)
                        }
                    }
                }
            }
        }
    }

    fun onRecommendationClick(openScreen: (String) -> Unit, recommendation: Recommendation) {
        openScreen("${RecommendRoutes.RecommendationScreen.name}?$RECOMMENDATION_ID={${recommendation.id}}")
    }
}