package com.serj.recommend.android.ui.screens.common.recommendation

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import com.serj.recommend.android.R
import com.serj.recommend.android.common.Constants.RECOMMENDATION_ID
import com.serj.recommend.android.common.ext.idFromParameter
import com.serj.recommend.android.model.collections.Recommendation
import com.serj.recommend.android.model.collections.User
import com.serj.recommend.android.model.items.UserItem
import com.serj.recommend.android.model.subcollections.Comment
import com.serj.recommend.android.model.subcollections.Like
import com.serj.recommend.android.model.subcollections.Repost
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
class RecommendationViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    logService: LogService,
    private val storageService: StorageService,
    private val accountService: AccountService
) : RecommendViewModel(logService) {
    val loadingStatus =
        mutableStateOf<Response<Boolean>>(Response.Loading(true))

    val currentUser = mutableStateOf<User?>(null)
    val userItem = mutableStateOf<UserItem?>(null)
    val recommendation =
        mutableStateOf<Recommendation?>(null)

    private var currentRecommendationId by mutableStateOf<String?>(null)
    private var currentLikeId by mutableStateOf<String?>(null)
    private var currentRepostId by mutableStateOf<String?>(null)

    var isLiked by mutableStateOf(false)
        private set
    var isCommented by mutableStateOf(false)
        private set
    var isReposted by mutableStateOf(false)
        private set

    var showCommentsBottomSheet by mutableStateOf(false)
        private set
    var showInsightsBottomSheet by mutableStateOf(false)
        private set

    var commentInput by mutableStateOf("")
        private set

    val bottomSheetComments = mutableStateMapOf<Comment, Boolean>()

    init {
        launchCatching {
            accountService.currentUser.collect { user ->
                currentUser.value = user
            }
        }

        val recommendationId = savedStateHandle.get<String>(RECOMMENDATION_ID)
        if (recommendationId != null) {
            launchCatching {
                val recommendationResponse = storageService.getRecommendationById(
                    recommendationId.idFromParameter()
                )
                loadingStatus.value =
                    if (recommendationResponse is Response.Failure)
                        Response.Failure(Exception())
                    else
                        Response.Loading(true)

                if (recommendationResponse is Response.Success) {
                    recommendation.value = recommendationResponse.data!!
                    currentRecommendationId = recommendationResponse.data.id

                    val userItemResponse = recommendationResponse.data
                        .uid?.let { storageService.getUserItemByUid(it) }
                    if (userItemResponse is Response.Success)
                        userItem.value = userItemResponse.data

                    for (like in recommendationResponse.data.likes) {
                        if (like.userId == currentUser.value?.uid) {
                            isLiked = true
                            currentLikeId = like.id
                            break
                        }
                    }
                    for (repost in recommendationResponse.data.reposts) {
                        if (repost.userId == currentUser.value?.uid) {
                            isReposted = true
                            currentRepostId = repost.id
                            break
                        }
                    }
                    bottomSheetComments.putAll(
                        recommendationResponse.data.comments
                            .sortedByDescending { it.date }
                            .associateWith { false }
                    )

                    loadingStatus.value = Response.Success(true)
                }
            }
        }
    }

    fun onLikeClick() {
        launchCatching {
            if (!isLiked) {
                isLiked = !isLiked
                if (currentUser.value != null && currentUser.value!!.uid != null &&
                    currentRecommendationId != null
                ) {
                    val like = Like(
                        userId = currentUser.value!!.uid,
                        recommendationId = currentRecommendationId,
                        date = Date(),
                        source = InteractionSource.recommendation.name
                    )

                    val likeResponse = storageService.like(like = like)
                    if (likeResponse is Response.Success) {
                        currentLikeId = likeResponse.data
                    } else {
                        SnackbarManager.showMessage(R.string.like_exception)
                        isLiked = !isLiked
                    }
                } else {
                    SnackbarManager.showMessage(R.string.like_exception)
                    isLiked = !isLiked
                }
            } else {
                isLiked = !isLiked
                if (currentUser.value != null && currentUser.value!!.uid != null &&
                    currentRecommendationId != null && currentLikeId != null
                ) {
                    val removeLikeResponse = storageService.removeLike(
                        userId = currentUser.value!!.uid!!,
                        recommendationId = currentRecommendationId!!,
                        likeId = currentLikeId!!
                    )
                    if (removeLikeResponse !is Response.Success) {
                        SnackbarManager.showMessage(R.string.like_exception)
                        isLiked = !isLiked
                    }
                } else {
                    SnackbarManager.showMessage(R.string.like_exception)
                    isLiked = !isLiked
                }
            }
        }
    }

    fun onCommentClick() {
        launchCatching {
            showCommentsBottomSheet = true
            isCommented = !isCommented
        }
    }

    fun onRepostClick() {
        launchCatching {
            if (!isReposted) {
                isReposted = !isReposted
                if (currentUser.value != null && currentUser.value!!.uid != null &&
                    currentRecommendationId != null
                ) {
                    val repost = Repost(
                        userId = currentUser.value!!.uid!!,
                        recommendationId = currentRecommendationId!!,
                        date = Date(),
                        source = InteractionSource.recommendation.name
                    )

                    val repostResponse = storageService.repost(repost)
                    if (repostResponse is Response.Success) {
                        currentRepostId = repostResponse.data
                    } else {
                        SnackbarManager.showMessage(R.string.repost_exception)
                        isReposted = !isReposted
                    }
                } else {
                    SnackbarManager.showMessage(R.string.repost_exception)
                    isReposted = !isReposted
                }
            } else {
                isReposted = !isReposted
                if (currentUser.value != null && currentUser.value!!.uid != null &&
                    currentRecommendationId != null && currentRepostId != null
                ) {
                    val removeRepostResponse = storageService.removeRepost(
                        userId = currentUser.value!!.uid!!,
                        recommendationId = currentRecommendationId!!,
                        repostId = currentRepostId!!
                    )
                    if (removeRepostResponse !is Response.Success) {
                        SnackbarManager.showMessage(R.string.repost_exception)
                        isReposted = !isReposted
                    }
                } else {
                    SnackbarManager.showMessage(R.string.repost_exception)
                    isReposted = !isReposted
                }
            }
        }
    }

    fun onInsightsClick() {
        showInsightsBottomSheet = true
    }

    fun onInsightsSheetDismissRequest() {
        showInsightsBottomSheet = false
    }

    fun onCommentInputValueChange(input: String) {
        commentInput = input
    }

    fun onCommentSheetDismissRequest() {
        showCommentsBottomSheet = false
        bottomSheetComments.clear()
    }

    fun onCommentItemClick(comment: Comment) {
        bottomSheetComments[comment] = true
    }

    fun onCommentItemDismissRequest(comment: Comment) {
        bottomSheetComments[comment] = false
    }

    fun onUploadCommentClick() {
        launchCatching {
            Log.v(TAG, currentUser.value?.uid.toString())

            if (currentUser.value?.uid != null) {
                storageService.comment(
                    userId = currentUser.value!!.uid!!,
                    recommendationId = currentRecommendationId!!,
                    repliedCommentId = null,
                    repliedUserId = null,
                    text = commentInput,
                    isReplied = false,
                    date = Date(),
                    source = InteractionSource.recommendation.name
                )

                val comment = Comment(
                    id = (currentUser.value?.uid!! + commentInput).hashCode().toString(),
                    userId = currentUser.value!!.uid!!,
                    recommendationId = "recommendationId",
                    repliedCommentId = null,
                    repliedUserId = null,
                    text = commentInput,
                    isReply = false,
                    date = Date(),
                    source = InteractionSource.recommendation.name,
                    userItem = UserItem(
                        uid = currentUser.value?.uid!!,
                        nickname = currentUser.value?.nickname,
                        photoReference = currentUser.value?.photoReference
                    )
                )

                commentInput = ""
                bottomSheetComments[comment] = false
            }
        }
    }

    fun onDeleteCommentClick(comment: Comment) {
        if (comment.id != null && comment.userId != null) {
            launchCatching {
                accountService.currentUser.collect { user ->
                    user.uid?.let {
                        currentRecommendationId?.let { it1 ->
                            storageService.removeComment(
                                recommendationId = it1,
                                userId = user.uid,
                                commentId = comment.id,
                                commentOwnerId = comment.userId
                            )
                        }
                        commentInput = ""
                        bottomSheetComments.remove(comment)
                    }
                }
            }
        }
    }
}