package com.serj.recommend.android.ui.screens.main.feed

import android.graphics.Bitmap
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import com.serj.recommend.android.RECOMMENDATION_ID
import com.serj.recommend.android.RecommendRoutes
import com.serj.recommend.android.model.Recommendation
import com.serj.recommend.android.model.items.RecommendationItem
import com.serj.recommend.android.model.items.UserItem
import com.serj.recommend.android.model.service.AccountService
import com.serj.recommend.android.model.service.LogService
import com.serj.recommend.android.model.service.StorageService
import com.serj.recommend.android.ui.screens.RecommendViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(
    logService: LogService,
    private val storageService: StorageService,
    private val accountService: AccountService
) : RecommendViewModel(logService) {

    private val currentUser = accountService.currentUser

    val posts = mutableStateListOf<Recommendation?>()
    val users = mutableStateMapOf<String?, UserItem?>()
    val postsRecommendations = mutableStateMapOf<String?, RecommendationItem?>()

    val usersPhotos = mutableStateMapOf<String?, Bitmap?>()
    val postsImages = mutableStateMapOf<String?, Bitmap?>()

    init {
        launchCatching {
            currentUser.collect {user ->
                for (followingUid in user.following!!) {
                    val currentPosts = storageService.getFeedData(followingUid)

                }
            }
        }
    }

    fun onRecommendationClick(openScreen: (String) -> Unit, recommendation: Recommendation) {
        openScreen("${RecommendRoutes.RecommendationScreen.name}?$RECOMMENDATION_ID={${recommendation.id}}")
    }
}