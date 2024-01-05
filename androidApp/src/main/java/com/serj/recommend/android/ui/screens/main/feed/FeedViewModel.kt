package com.serj.recommend.android.ui.screens.main.feed

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import com.serj.recommend.android.RecommendRoutes
import com.serj.recommend.android.common.Constants.RECOMMENDATION_ID
import com.serj.recommend.android.model.Recommendation
import com.serj.recommend.android.model.items.RecommendationItem
import com.serj.recommend.android.services.AccountService
import com.serj.recommend.android.services.LogService
import com.serj.recommend.android.services.StorageService
import com.serj.recommend.android.services.model.Response
import com.serj.recommend.android.ui.screens.RecommendViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(
    private val logService: LogService,
    private val storageService: StorageService,
    private val accountService: AccountService
) : RecommendViewModel(logService) {

    private val currentUser = accountService.currentUser
    val currentRecommendations = mutableStateListOf<MutableState<RecommendationItem>>()
    val currentRecommendationsAmount = mutableIntStateOf(0)

    init {
        launchCatching {
            currentUser.collect {user ->
                val followingRecommendationsIds = arrayListOf<Pair<String, Date>>()

                if (user.following != null) {
                    for (followingUid in user.following) {
                        val followingRecommendationsIdsResponse =
                            storageService.getFollowingRecommendationsIds(followingUid)
                        if (followingRecommendationsIdsResponse is Response.Success &&
                            followingRecommendationsIdsResponse.data != null) {
                            followingRecommendationsIds.addAll(
                                followingRecommendationsIdsResponse.data
                            )
                        }
                    }
                }
                currentRecommendationsAmount.intValue = followingRecommendationsIds.size
                followingRecommendationsIds.sortByDescending { it.second }

                for (recommendationId in followingRecommendationsIds) {
                    val currentRecommendationItemResponse = storageService
                        .getRecommendationItemById(recommendationId.first)
                    if (currentRecommendationItemResponse is Response.Success &&
                        currentRecommendationItemResponse.data != null) {
                        currentRecommendations.add(
                            mutableStateOf(currentRecommendationItemResponse.data)
                        )
                    }
                }
            }
        }
    }

    fun onRecommendationClick(openScreen: (String) -> Unit, recommendation: Recommendation) {
        openScreen("${RecommendRoutes.RecommendationScreen.name}?$RECOMMENDATION_ID={${recommendation.id}}")
    }
}