package com.serj.recommend.android.ui.screens.main.feed

import android.graphics.Bitmap
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import com.serj.recommend.android.RECOMMENDATION_ID
import com.serj.recommend.android.RecommendRoutes
import com.serj.recommend.android.model.Post
import com.serj.recommend.android.model.Recommendation
import com.serj.recommend.android.model.RecommendationItem
import com.serj.recommend.android.model.UserItem
import com.serj.recommend.android.model.service.AccountService
import com.serj.recommend.android.model.service.ConfigurationService
import com.serj.recommend.android.model.service.LogService
import com.serj.recommend.android.model.service.StorageService
import com.serj.recommend.android.ui.screens.RecommendViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(
    logService: LogService,
    private val storageService: StorageService,
    private val accountService: AccountService,
    private val configurationService: ConfigurationService
) : RecommendViewModel(logService) {

    val currentUser = accountService.currentUser

    val posts = mutableStateListOf<Post?>()
    val users = mutableStateMapOf<String?, UserItem?>()
    val postsRecommendations = mutableStateMapOf<String?, RecommendationItem?>()

    val usersPhotos = mutableStateMapOf<String?, Bitmap?>()
    val postsImages = mutableStateMapOf<String?, Bitmap?>()

    init {
        launchCatching {
            currentUser.collect {user ->
                for (followingUid in user.following!!) {
                    val currentPosts = storageService.getFollowingPosts(followingUid)
                    for (post in currentPosts) {
                        post.uid?.let {
                            users[it] = storageService.getUserItem(it)
                            usersPhotos[it] = users[it]?.userPhoto?.let { it1 ->
                                storageService.downloadImage(
                                    it1
                                )
                            }
                        }

                        postsRecommendations[post.id] = post.recommendationId?.let {
                            storageService.getRecommendationItem(
                                it
                            )
                        }

                        postsImages[post.id] = postsRecommendations[post.id]?.background?.get("image")
                            ?.let {
                            storageService.downloadImage(
                                it
                            )
                        }

                        posts.add(post)
                    }
                }
            }
        }
    }

    fun onRecommendationClick(openScreen: (String) -> Unit, recommendation: Recommendation) {
        openScreen("${RecommendRoutes.RecommendationScreen.name}?$RECOMMENDATION_ID={${recommendation.id}}")
    }
}