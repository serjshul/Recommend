package com.serj.recommend.android.services

import com.google.firebase.storage.StorageReference
import com.serj.recommend.android.model.Banner
import com.serj.recommend.android.model.Category
import com.serj.recommend.android.model.Recommendation
import com.serj.recommend.android.model.items.RecommendationItem
import com.serj.recommend.android.model.items.RecommendationPreviewItem
import com.serj.recommend.android.model.items.UserItem
import com.serj.recommend.android.services.model.Response
import kotlinx.coroutines.flow.Flow
import java.util.Date

typealias GetRecommendationByIdResponse = Response<Recommendation?>
typealias GetBannerByIdResponse = Response<Banner?>
typealias GetCategoryByIdResponse = Response<Category?>
typealias GetRecommendationItemByIdResponse = Response<RecommendationItem?>
typealias GetRecommendationPreviewByIdResponse = Response<RecommendationPreviewItem?>
typealias GetUserItemByUidResponse = Response<UserItem?>
typealias GetFollowingRecommendationsIdsResponse = Response<List<Pair<String, Date>>>
typealias GetStorageReferenceFromUrlResponse = Response<StorageReference>

interface StorageService {

    val recommendations: Flow<List<Recommendation>>
    val banners: Flow<List<Banner>>
    val categories: Flow<List<Category>>

    suspend fun getRecommendationById(recommendationId: String): GetRecommendationByIdResponse
    suspend fun getBannerById(bannerId: String): GetBannerByIdResponse
    suspend fun getCategoryById(categoryId: String): GetCategoryByIdResponse

    suspend fun getRecommendationItemById(recommendationId: String): GetRecommendationItemByIdResponse
    suspend fun getRecommendationPreviewById(recommendationId: String): GetRecommendationPreviewByIdResponse
    suspend fun getUserItemByUid(uid: String): GetUserItemByUidResponse

    suspend fun getFollowingRecommendationsIds(followingUid: String): GetFollowingRecommendationsIdsResponse

    fun getStorageReferenceFromUrl(url: String): GetStorageReferenceFromUrlResponse
}