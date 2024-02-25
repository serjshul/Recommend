package com.serj.recommend.android.ui.screens.main.feed

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.serj.recommend.android.R
import com.serj.recommend.android.RecommendRoutes
import com.serj.recommend.android.common.Constants.RECOMMENDATION_ID
import com.serj.recommend.android.model.collections.Recommendation
import com.serj.recommend.android.model.collections.User
import com.serj.recommend.android.model.items.RecommendationItem
import com.serj.recommend.android.model.items.UserItem
import com.serj.recommend.android.model.subcollections.Comment
import com.serj.recommend.android.services.AccountService
import com.serj.recommend.android.services.LogService
import com.serj.recommend.android.services.StorageService
import com.serj.recommend.android.services.model.Response
import com.serj.recommend.android.ui.components.interaction.InteractionSource
import com.serj.recommend.android.ui.components.snackbar.SnackbarManager
import com.serj.recommend.android.ui.screens.RecommendViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(
    logService: LogService,
    private val storageService: StorageService,
    private val accountService: AccountService
) : RecommendViewModel(logService) {

    val currentUser = mutableStateOf<User?>(null)
    val currentRecommendations = mutableStateListOf<RecommendationItem>()
    val currentRecommendationsAmount = mutableIntStateOf(0)


    var currentRecommendationId by mutableStateOf("")
        private set

    var commentInput by mutableStateOf("")
        private set

    var showCommentsBottomSheet by mutableStateOf(false)
        private set
    val bottomSheetComments = mutableStateMapOf<Comment, Boolean>()

    init {
        launchCatching {
            accountService.currentUser.collect { user ->
                currentUser.value = user

                //val currentLikedIds = user.likedIds
                val followingRecommendationsIdsResponse = storageService
                    .getFollowingRecommendationsIds(user.following)

                if (followingRecommendationsIdsResponse is Response.Success &&
                    followingRecommendationsIdsResponse.data != null) {
                    currentRecommendationsAmount.intValue =
                        followingRecommendationsIdsResponse.data.size
                    for (followingId in followingRecommendationsIdsResponse.data) {
                        val recommendationItemResponse = storageService
                            .getRecommendationItemById(followingId, arrayListOf())
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

    fun onUploadCommentClick() {
        launchCatching {
            accountService.currentUser.collect { user ->
                user.uid?.let {
                    storageService.comment(
                        userId = user.uid,
                        recommendationId = currentRecommendationId,
                        repliedCommentId = null,
                        repliedUserId = null,
                        text = commentInput,
                        isReplied = false,
                        date = Date(),
                        source = InteractionSource.recommendation.name
                    )

                    val comment = Comment(
                        id = (user.uid + commentInput).hashCode().toString(),
                        userId = user.uid,
                        repliedCommentId = null,
                        text = commentInput,
                        date = Date(),
                        likedBy = arrayListOf(),
                        userItem = UserItem(
                            uid = user.uid,
                            nickname = user.nickname,
                            photoReference = user.photoReference
                        )
                    )

                    commentInput = ""
                    bottomSheetComments[comment] = false
                }
            }
        }
    }

    fun onDeleteCommentClick(comment: Comment) {
        if (comment.id != null && comment.userId != null) {
            launchCatching {
                accountService.currentUser.collect { user ->
                    user.uid?.let {
                        storageService.removeComment(
                            recommendationId = currentRecommendationId,
                            userId = user.uid,
                            commentId = comment.id,
                            commentOwnerId = comment.userId
                        )
                        commentInput = ""
                        bottomSheetComments.remove(comment)
                    }
                }
            }
        }
    }

    fun onLikeClick(isLiked: Boolean, uid: String, recommendationId: String) = Response.Success(true)
        /*
        storageService.likeOrUnlikeRecommendation(
            isLiked = isLiked,
            userId = uid,
            recommendationId = recommendationId,
            source = InteractionSource.feed.name
        )

         */

    fun onCommentIconClick(recommendationId: String, comments: List<Comment>) {
        currentRecommendationId = recommendationId
        bottomSheetComments.putAll(comments.associateWith { false })
        showCommentsBottomSheet = true
    }

    fun onCommentInputValueChange(input: String) {
        commentInput = input
    }

    fun onCommentSheetDismissRequest() {
        showCommentsBottomSheet = false
        bottomSheetComments.clear()
    }

    fun onCommentClick(comment: Comment) {
        bottomSheetComments[comment] = true
    }

    fun onCommentDismissRequest(comment: Comment) {
        bottomSheetComments[comment] = false
    }

    fun onRecommendationClick(openScreen: (String) -> Unit, recommendation: Recommendation) {
        openScreen("${RecommendRoutes.RecommendationScreen.name}?$RECOMMENDATION_ID={${recommendation.id}}")
    }
}