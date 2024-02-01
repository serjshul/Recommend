package com.serj.recommend.android.ui.screens.main.feed

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import com.serj.recommend.android.R
import com.serj.recommend.android.RecommendRoutes
import com.serj.recommend.android.common.Constants.RECOMMENDATION_ID
import com.serj.recommend.android.model.Comment
import com.serj.recommend.android.model.Recommendation
import com.serj.recommend.android.model.User
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

    var currentRecommendationId by mutableStateOf("")
        private set
    var commentInput by mutableStateOf("")
        private set
    var showCommentsBottomSheet by mutableStateOf(false)
        private set
    val bottomSheetComments = mutableStateListOf<Comment>()

    val isDropdownMenuExpanded = mutableStateOf(false)
    var offset by mutableStateOf(DpOffset.Zero)

    val currentUser = mutableStateOf<User?>(null)
    val currentRecommendations = mutableStateListOf<RecommendationItem>()
    val currentRecommendationsAmount = mutableIntStateOf(0)

    init {
        launchCatching {
            accountService.currentUser.collect { user ->
                currentUser.value = user

                val currentLikedIds = user.likedIds
                val followingRecommendationsIdsResponse = storageService
                    .getFollowingRecommendationsIds(user.following)

                if (followingRecommendationsIdsResponse is Response.Success &&
                    followingRecommendationsIdsResponse.data != null) {
                    currentRecommendationsAmount.intValue =
                        followingRecommendationsIdsResponse.data.size
                    for (followingId in followingRecommendationsIdsResponse.data) {
                        val recommendationItemResponse = storageService
                            .getRecommendationItemById(followingId, currentLikedIds)
                        if (recommendationItemResponse is Response.Success &&
                            recommendationItemResponse.data != null) {
                            currentRecommendations.add(recommendationItemResponse.data)
                        }
                    }
                } else {
                    SnackbarManager.showMessage(R.string.error_feed)
                }
            }
        }
    }

    fun onOffsetChangeValue(x: Dp, y: Dp) {
        offset = DpOffset(x, y)
    }

    fun onUploadCommentClick(text: String) {
        launchCatching {
            accountService.currentUser.collect { user ->
                user.uid?.let {
                    storageService.uploadComment(
                        recommendationId = currentRecommendationId,
                        userId = user.uid,
                        text = commentInput
                    )

                    commentInput = ""
                    bottomSheetComments.clear()

                    val getCommentResponse = storageService.getComments(currentRecommendationId)
                    if (getCommentResponse is Response.Success && getCommentResponse.data != null) {
                        bottomSheetComments.addAll(getCommentResponse.data)
                    }
                }
            }
        }
    }

    fun onLikeClick(isLiked: Boolean, uid: String, recommendationId: String) =
        storageService.likeOrUnlikeRecommendation(isLiked, uid, recommendationId)

    fun onCommentIconClick(recommendationId: String, comments: List<Comment>) {
        currentRecommendationId = recommendationId
        bottomSheetComments.addAll(comments)
        showCommentsBottomSheet = true
    }

    fun onCommentInputValueChange(input: String) {
        commentInput = input
    }

    fun onCommentSheetDismissRequest() {
        showCommentsBottomSheet = false
        bottomSheetComments.clear()
    }

    fun onCommentClick() {
        isDropdownMenuExpanded.value = true
    }

    fun onCommentDismissRequest() {
        isDropdownMenuExpanded.value = false
    }

    fun onRecommendationClick(openScreen: (String) -> Unit, recommendation: Recommendation) {
        openScreen("${RecommendRoutes.RecommendationScreen.name}?$RECOMMENDATION_ID={${recommendation.id}}")
    }
}