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

typealias RecommendationResponse = Response<Recommendation?>
typealias BannerResponse = Response<Banner?>
typealias CategoryResponse = Response<Category?>
typealias RecommendationItemResponse = Response<RecommendationItem?>
typealias RecommendationPreviewResponse = Response<RecommendationPreview?>
typealias UserItemResponse = Response<UserItem?>
typealias FollowingRecommendationsIdsResponse = Response<List<String>>
typealias StorageReferenceFromUrlResponse = Response<StorageReference>

interface StorageService {

    val banners: Flow<List<Banner>>
    val categories: Flow<List<Category>>

    suspend fun getRecommendationById(recommendationId: String):
            RecommendationResponse
    suspend fun getBannerById(bannerId: String): BannerResponse
    suspend fun getCategoryById(categoryId: String): CategoryResponse

    suspend fun getRecommendationItemById(recommendationId: String):
            RecommendationItemResponse
    suspend fun getRecommendationPreviewById(recommendationId: String, coverType: String):
            RecommendationPreviewResponse
    suspend fun getUserItemByUid(uid: String): UserItemResponse

    suspend fun getFollowingRecommendationsIds(followingUids: List<String>):
            FollowingRecommendationsIdsResponse

    fun getStorageReferenceFromUrl(url: String): StorageReferenceFromUrlResponse
}