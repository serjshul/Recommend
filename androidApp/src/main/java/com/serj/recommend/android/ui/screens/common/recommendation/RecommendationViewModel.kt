package com.serj.recommend.android.ui.screens.common.recommendation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import com.google.firebase.storage.StorageReference
import com.serj.recommend.android.R
import com.serj.recommend.android.common.Constants.RECOMMENDATION_ID
import com.serj.recommend.android.common.ext.idFromParameter
import com.serj.recommend.android.model.collections.Recommendation
import com.serj.recommend.android.model.subcollections.Comment
import com.serj.recommend.android.model.subcollections.Like
import com.serj.recommend.android.model.subcollections.Repost
import com.serj.recommend.android.model.subcollections.UserContent
import com.serj.recommend.android.services.AccountService
import com.serj.recommend.android.services.InteractionService
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
    private val interactionService: InteractionService,
    private val accountService: AccountService
) : RecommendViewModel(logService) {

    val recommendationResponse = mutableStateOf<Response<Recommendation?>>(Response.Loading(null))

    var currentUserId by mutableStateOf<String?>(null)
    var currentUserPhotoReference by mutableStateOf<StorageReference?>(null)
    private var recommendationId by mutableStateOf<String?>(null)
        private set
    private var likeId by mutableStateOf<String?>(null)
        private set
    private var repostId by mutableStateOf<String?>(null)
        private set

    var commentInput by mutableStateOf("")
        private set
    val bottomSheetComments = mutableStateMapOf<Comment, Boolean>()

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

    init {
        val recommendationId = savedStateHandle.get<String>(RECOMMENDATION_ID)
        if (recommendationId != null) {
            launchCatching {
                accountService.currentUser.collect { user ->
                    currentUserId = user.uid
                    currentUserPhotoReference = user.photoReference

                    if (currentUserId != null) {
                        recommendationResponse.value = storageService.getRecommendationById(
                            recommendationId = recommendationId.idFromParameter(),
                            userId = currentUserId!!
                        )
                        if (recommendationResponse.value is Response.Success) {
                            val data = (recommendationResponse.value as Response.Success<Recommendation?>).data
                            this@RecommendationViewModel.recommendationId = data!!.id
                            isLiked = data.isLiked
                            likeId = data.likeId
                            isReposted = data.isReposted
                            repostId = data.repostId
                            bottomSheetComments.putAll(
                                data.comments
                                    .sortedByDescending { it.date }
                                    .associateWith { false }
                            )
                        } else if (recommendationResponse.value is Response.Failure) {
                            recommendationResponse.value = Response.Failure((recommendationResponse.value as Response.Failure).e)
                        }
                    }
                }
            }
        }
    }

    fun onLikeClick() {
        launchCatching {
            if (!isLiked) {
                isLiked = !isLiked
                if (currentUserId != null && recommendationId != null) {
                    val like = Like(
                        id = generateLikeId(recommendationId!!, currentUserId!!),
                        userId = currentUserId,
                        recommendationId = recommendationId,
                        date = Date(),
                        source = InteractionSource.recommendation.name
                    )

                    val likeResponse = interactionService.like(like = like)
                    if (likeResponse is Response.Success) {
                        likeId = likeResponse.data
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
                if (currentUserId != null && recommendationId != null && likeId != null) {
                    val removeLikeResponse = interactionService.removeLike(
                        userId = currentUserId!!,
                        recommendationId = recommendationId!!,
                        likeId = likeId!!
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
                if (currentUserId != null && recommendationId != null) {
                    val repost = Repost(
                        id = generateRepostId(recommendationId!!, currentUserId!!),
                        userId = currentUserId!!,
                        recommendationId = recommendationId!!,
                        date = Date(),
                        source = InteractionSource.recommendation.name
                    )
                    val userContent = UserContent(
                        recommendationId = repost.recommendationId,
                        userId = repost.userId,
                        isReposted = true,
                        date = repost.date,
                        source = repost.source
                    )

                    val repostResponse = interactionService.repost(repost, userContent)
                    if (repostResponse is Response.Success) {
                        repostId = repostResponse.data
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
                if (currentUserId != null && recommendationId != null && repostId != null) {
                    val removeRepostResponse = interactionService.removeRepost(
                        userId = currentUserId!!,
                        recommendationId = recommendationId!!,
                        repostId = repostId!!
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
            if (currentUserId != null) {
                val comment = Comment(
                    id = generateCommentId(recommendationId!!, currentUserId!!),
                    userId = currentUserId!!,
                    recommendationId = recommendationId!!,
                    text = commentInput,
                    date = Date(),
                    source = InteractionSource.recommendation.name
                )
                val commentResponse = interactionService.comment(comment)
                if (commentResponse is Response.Success) {
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
                        recommendationId?.let { it1 ->
                            interactionService.removeComment(
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



    private fun generateLikeId(
        recommendationId: String,
        userId: String
    ): String = (recommendationId + userId + "like")
        .hashCode()
        .toString()

    private fun generateCommentId(
        recommendationId: String,
        userId: String
    ): String = (recommendationId + userId + "comment" + (0..1000).random())
        .hashCode()
        .toString()

    private fun generateRepostId(
        recommendationId: String,
        userId: String
    ): String = (recommendationId + userId + "repost")
        .hashCode()
        .toString()
}