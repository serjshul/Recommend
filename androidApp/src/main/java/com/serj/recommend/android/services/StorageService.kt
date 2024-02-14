package com.serj.recommend.android.services

import android.content.Context
import android.net.Uri
import com.google.firebase.storage.StorageReference
import com.serj.recommend.android.model.collections.Banner
import com.serj.recommend.android.model.collections.Category
import com.serj.recommend.android.model.collections.Recommendation
import com.serj.recommend.android.model.items.RecommendationItem
import com.serj.recommend.android.model.items.RecommendationPreview
import com.serj.recommend.android.model.items.UserItem
import com.serj.recommend.android.model.subcollections.Comment
import com.serj.recommend.android.model.subcollections.Like
import com.serj.recommend.android.model.subcollections.Repost
import com.serj.recommend.android.services.model.Response
import kotlinx.coroutines.flow.Flow
import java.util.Date

typealias GetRecommendationResponse = Response<Recommendation?>
typealias GetBannerResponse = Response<Banner?>
typealias GetCategoryResponse = Response<Category?>
typealias GetRecommendationItemResponse = Response<RecommendationItem?>
typealias GetRecommendationPreviewResponse = Response<RecommendationPreview?>
typealias GetUserItemResponse = Response<UserItem?>
typealias GetRecommendationCommentsResponse = Response<List<Comment>>
typealias GetFollowingRecommendationsIdsResponse = Response<List<String>>
typealias GetStorageReferenceFromUrlResponse = Response<StorageReference>
typealias LikeRecommendationResponse = Response<String>
typealias RemoveLikeRecommendationResponse = Response<Boolean>
typealias RepostRecommendationResponse = Response<String>
typealias RemoveRepostRecommendationResponse = Response<Boolean>
typealias UploadCommentResponse = Response<Boolean>
typealias DeleteCommentResponse = Response<Boolean>
typealias UploadRecommendationResponse = Response<String>

interface StorageService {

    val banners: Flow<List<Banner>>
    val categories: Flow<List<Category>>

    suspend fun getRecommendationById(recommendationId: String):
            GetRecommendationResponse
    suspend fun getBannerById(bannerId: String): GetBannerResponse
    suspend fun getCategoryById(categoryId: String): GetCategoryResponse

    suspend fun getRecommendationItemById(
        recommendationId: String, currentUserLikedIds: ArrayList<String>
    ): GetRecommendationItemResponse

    suspend fun getRecommendationPreviewById(recommendationId: String, coverType: String):
            GetRecommendationPreviewResponse
    suspend fun getUserItemByUid(uid: String): GetUserItemResponse

    suspend fun getCommentsFromRecommendation(recommendationId: String): GetRecommendationCommentsResponse

    suspend fun getFollowingRecommendationsIds(followingUids: List<String>):
            GetFollowingRecommendationsIdsResponse

    fun getStorageReferenceFromUrl(url: String): GetStorageReferenceFromUrlResponse

    suspend fun like(
        like: Like
    ): LikeRecommendationResponse

    suspend fun removeLike(
        userId: String,
        recommendationId: String,
        likeId: String
    ): RemoveLikeRecommendationResponse

    suspend fun comment(
        userId: String,
        recommendationId: String,
        repliedCommentId: String?,
        repliedUserId: String?,
        text: String,
        isReplied: Boolean,
        date: Date,
        source: String
    ): UploadCommentResponse

    suspend fun removeComment(recommendationId: String, userId: String, commentId: String,
                      commentOwnerId: String):
            DeleteCommentResponse

    suspend fun repost(
        repost: Repost
    ): RepostRecommendationResponse

    suspend fun removeRepost(
        userId: String,
        recommendationId: String,
        repostId: String
    ): RemoveRepostRecommendationResponse

    suspend fun uploadRecommendation(
        recommendation: Recommendation,
        currentUserId: String,
        isReposted: Boolean
    ): UploadRecommendationResponse

    suspend fun uploadBackgroundImage(recommendationId: String, uri: Uri, context: Context)

    suspend fun uploadCoverImage(
        recommendationId: String, uri: Uri, coverType: String, context: Context
    )
}