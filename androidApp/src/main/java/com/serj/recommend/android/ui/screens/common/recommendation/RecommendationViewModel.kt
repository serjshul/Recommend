package com.serj.recommend.android.ui.screens.common.recommendation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import com.serj.recommend.android.common.Constants.RECOMMENDATION_ID
import com.serj.recommend.android.common.ext.idFromParameter
import com.serj.recommend.android.model.collections.Comment
import com.serj.recommend.android.model.collections.Recommendation
import com.serj.recommend.android.model.collections.User
import com.serj.recommend.android.model.items.UserItem
import com.serj.recommend.android.services.AccountService
import com.serj.recommend.android.services.GetRecommendationResponse
import com.serj.recommend.android.services.GetUserItemResponse
import com.serj.recommend.android.services.LogService
import com.serj.recommend.android.services.StorageService
import com.serj.recommend.android.services.model.Response
import com.serj.recommend.android.ui.components.interaction.InteractionSource
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

    val currentUser = mutableStateOf<User?>(null)

    val getRecommendationResponse = mutableStateOf<GetRecommendationResponse?>(null)
    val getUserItemResponse = mutableStateOf<GetUserItemResponse?>(null)
    private var currentRecommendationId by mutableStateOf<String?>(null)

    val topLikedComment = mutableStateOf<Comment?>(null)

    var commentInput by mutableStateOf("")
        private set
    var showCommentsBottomSheet by mutableStateOf(false)
        private set
    val bottomSheetComments = mutableStateMapOf<Comment, Boolean>()

    init {
        val recommendationId = savedStateHandle.get<String>(RECOMMENDATION_ID)
        if (recommendationId != null) {
            launchCatching {
                getRecommendationResponse.value = storageService
                    .getRecommendationById(
                        recommendationId.idFromParameter()
                    )

                if (getRecommendationResponse.value is Response.Success) {
                    val recommendationData = (getRecommendationResponse.value as
                            Response.Success<Recommendation?>).data
                    currentRecommendationId = recommendationData?.id
                    getUserItemResponse.value = recommendationData?.uid?.let {
                        storageService.getUserItemByUid(it)
                    }
                    if (recommendationData != null) {
                        topLikedComment.value = recommendationData.comments.maxBy { it.likedBy.size }
                    }
                }
            }
        }

        launchCatching {
            accountService.currentUser.collect { user ->
                currentUser.value = user
            }
        }
    }

    fun onLikeClick(isLiked: Boolean): Response<Boolean>? {
        var result: Response<Boolean>? = null

        launchCatching {
            result =
                if (!isLiked)
                    storageService.likeRecommendation(
                        userId = currentUser.value!!.uid!!,
                        recommendationId = currentRecommendationId!!,
                        date = Date(),
                        source = InteractionSource.recommendation.name
                    )
                else
                    storageService.unlikeRecommendation(
                        userId = currentUser.value!!.uid!!,
                        recommendationId = currentRecommendationId!!
                    )
        }

        return result
    }

    fun onCommentIconClick(comments: List<Comment>) {
        bottomSheetComments.putAll(comments.associateWith { false })
        showCommentsBottomSheet = true
    }

    fun onRepostClick(
        recommendationId: String,
        userId: String,
        isReposted: Boolean
    ) = storageService.repostOrUnrepostRecommendation(recommendationId, userId, isReposted)

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

    fun onUploadCommentClick() {
        launchCatching {
            accountService.currentUser.collect { user ->
                user.uid?.let {
                    currentRecommendationId?.let { it1 ->
                        storageService.uploadComment(
                            recommendationId = it1,
                            userId = user.uid,
                            text = commentInput
                        )
                    }

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
                        currentRecommendationId?.let { it1 ->
                            storageService.deleteComment(
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