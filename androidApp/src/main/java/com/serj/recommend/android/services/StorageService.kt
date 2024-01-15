package com.serj.recommend.android.services

import com.google.firebase.storage.StorageReference
import com.serj.recommend.android.model.Banner
import com.serj.recommend.android.model.Category
import com.serj.recommend.android.model.Recommendation
import com.serj.recommend.android.model.items.RecommendationItem
import com.serj.recommend.android.model.items.RecommendationPreview
import com.serj.recommend.android.model.items.UserItem
import com.serj.recommend.android.services.model.Response
import kotlinx.coroutines.flow.Flow

typealias GetRecommendationResponse = Response<Recommendation?>
typealias GetBannerResponse = Response<Banner?>
typealias GetCategoryResponse = Response<Category?>
typealias GetRecommendationItemResponse = Response<RecommendationItem?>
typealias GetRecommendationPreviewResponse = Response<RecommendationPreview?>
typealias GetUserItemResponse = Response<UserItem?>
typealias GetFollowingRecommendationsIdsResponse = Response<List<String>>
typealias GetStorageReferenceFromUrlResponse = Response<StorageReference>
typealias SetLikeToRecommendationResponse = Response<Boolean>

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

    fun setLikeToRecommendation(isLiked: Boolean, uid: String, recommendationId: String):
            SetLikeToRecommendationResponse

    suspend fun getFollowingRecommendationsIds(followingUids: List<String>):
            GetFollowingRecommendationsIdsResponse

    fun getStorageReferenceFromUrl(url: String): GetStorageReferenceFromUrlResponse
}