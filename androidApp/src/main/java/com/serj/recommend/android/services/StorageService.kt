package com.serj.recommend.android.services

import android.content.Context
import android.net.Uri
import com.google.firebase.storage.StorageReference
import com.serj.recommend.android.model.collections.Banner
import com.serj.recommend.android.model.collections.Category
import com.serj.recommend.android.model.collections.Recommendation
import com.serj.recommend.android.model.collections.User
import com.serj.recommend.android.model.items.Post
import com.serj.recommend.android.model.items.RecommendationPreview
import com.serj.recommend.android.model.items.UserItem
import com.serj.recommend.android.model.subcollections.Comment
import com.serj.recommend.android.model.subcollections.Like
import com.serj.recommend.android.model.subcollections.Repost
import com.serj.recommend.android.services.model.Response
import kotlinx.coroutines.flow.Flow

typealias GetRecommendationResponse = Response<Recommendation?>
typealias GetBannerResponse = Response<Banner?>
typealias GetCategoryResponse = Response<Category?>
typealias GetPostResponse = Response<Post?>
typealias GetRecommendationPreviewResponse = Response<RecommendationPreview?>
typealias GetUserItemResponse = Response<UserItem?>
typealias GetFollowingRecommendationsIdsResponse = Response<List<String>>
typealias GetStorageReferenceFromUrlResponse = Response<StorageReference>
typealias UploadRecommendationResponse = Response<String>
typealias UploadUserResponse = Response<Boolean>
typealias UploadUserPhotoResponse = Response<Boolean>
typealias uploadFollowersResponse = Response<List<String>>
typealias uploadFollowingResponse = Response<List<String>>
typealias GetLikesResponse = Response<List<Like>>
typealias GetCommentsResponse = Response<List<Comment>>
typealias GetRepostsResponse = Response<List<Repost>>

interface StorageService {

    val banners: Flow<List<Banner>>
    val categories: Flow<List<Category>>

    suspend fun getRecommendationById(
        recommendationId: String,
        userId: String,
    ): GetRecommendationResponse

    suspend fun getBannerById(
        bannerId: String
    ): GetBannerResponse

    suspend fun getCategoryById(
        categoryId: String
    ): GetCategoryResponse

    suspend fun getPostById(
        recommendationId: String,
        currentUserLikedIds: ArrayList<String>
    ): GetPostResponse

    suspend fun getRecommendationPreviewById(
        recommendationId: String,
        coverType: String
    ): GetRecommendationPreviewResponse

    suspend fun getUserItemByUid(
        uid: String
    ): GetUserItemResponse

    suspend fun getFollowingRecommendationsIds(
        followingUids: List<String>
    ): GetFollowingRecommendationsIdsResponse

    fun getStorageReferenceFromUrl(
        url: String
    ): GetStorageReferenceFromUrlResponse

    suspend fun uploadRecommendation(
        recommendation: Recommendation,
        currentUserId: String,
        isReposted: Boolean
    ): UploadRecommendationResponse

    suspend fun uploadBackgroundImage(
        recommendationId: String,
        uri: Uri,
        context: Context
    )

    suspend fun uploadCoverImage(
        recommendationId: String,
        uri: Uri,
        coverType: String,
        context: Context
    )

    suspend fun uploadUser(
        user: User
    ): UploadUserResponse

    suspend fun getFollowers(
        userId: String
    ): uploadFollowersResponse

    suspend fun getFollowing(
        userId: String
    ): uploadFollowingResponse

    suspend fun uploadUserPhoto(
        userId: String,
        uri: Uri,
        context: Context
    ): UploadUserPhotoResponse

    suspend fun getLikes(
        recommendationId: String
    ): GetLikesResponse

    suspend fun getComments(
        recommendationId: String
    ): GetCommentsResponse

    suspend fun getReposts(
        recommendationId: String
    ): GetRepostsResponse
}