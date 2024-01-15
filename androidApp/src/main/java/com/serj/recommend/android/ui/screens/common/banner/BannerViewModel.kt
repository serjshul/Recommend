package com.serj.recommend.android.ui.screens.common.banner

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import com.serj.recommend.android.RecommendRoutes
import com.serj.recommend.android.common.Constants.BANNER_ID
import com.serj.recommend.android.common.Constants.RECOMMENDATION_ID
import com.serj.recommend.android.common.ext.idFromParameter
import com.serj.recommend.android.model.Banner
import com.serj.recommend.android.model.Recommendation
import com.serj.recommend.android.model.items.RecommendationItem
import com.serj.recommend.android.services.AccountService
import com.serj.recommend.android.services.GetBannerResponse
import com.serj.recommend.android.services.LogService
import com.serj.recommend.android.services.StorageService
import com.serj.recommend.android.services.model.Response
import com.serj.recommend.android.ui.screens.RecommendViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BannerViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    logService: LogService,
    private val storageService: StorageService,
    private val accountService: AccountService
) : RecommendViewModel(logService) {

    val getBannerResponse = mutableStateOf<GetBannerResponse?>(null)

    val currentRecommendations = mutableStateListOf<MutableState<RecommendationItem>>()
    val currentRecommendationsAmount = mutableIntStateOf(0)

    val currentUid = mutableStateOf<String?>(null)

    init {
        val bannerId = savedStateHandle.get<String>(BANNER_ID)
        if (bannerId != null) {
            launchCatching {
                accountService.currentUser.collect {user ->
                    currentUid.value = user.uid

                    getBannerResponse.value = storageService
                        .getBannerById(bannerId.idFromParameter())

                    if (getBannerResponse.value is Response.Success) {
                        val currentBanner = (getBannerResponse.value as Response.Success<Banner?>).data
                        if (currentBanner?.recommendationIds?.size != null) {
                            currentRecommendationsAmount.intValue =
                                currentBanner.recommendationIds.size
                            for (recommendationId in currentBanner.recommendationIds) {
                                val currentRecommendationItemResponse = storageService
                                    .getRecommendationItemById(recommendationId, user.likedIds)
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
            }
        }
    }

    fun onLikeClick(isLiked: Boolean, uid: String, recommendationId: String) =
        storageService.setLikeToRecommendation(isLiked, uid, recommendationId)

    fun onRecommendationClick(openScreen: (String) -> Unit, recommendation: Recommendation) {
        openScreen("${RecommendRoutes.RecommendationScreen.name}?$RECOMMENDATION_ID={${recommendation.id}}")
    }
}