package com.serj.recommend.android.services

import com.google.firebase.storage.StorageReference
import com.serj.recommend.android.model.Banner
import com.serj.recommend.android.model.Category
import com.serj.recommend.android.model.Recommendation
import com.serj.recommend.android.model.items.RecommendationItem
import com.serj.recommend.android.model.items.RecommendationPreviewItem
import com.serj.recommend.android.model.items.UserItem
import kotlinx.coroutines.flow.Flow
import java.util.Date

interface StorageService {

    val recommendations: Flow<List<Recommendation>>

    val banners: Flow<List<Banner>>

    val categories: Flow<List<Category>>

    suspend fun getRecommendationById(recommendationId: String): Recommendation?

    suspend fun getBannerById(bannerId: String): Banner?

    suspend fun getCategoryById(categoryId: String): Category?

    suspend fun getRecommendationItemById(recommendationId: String): RecommendationItem?

    suspend fun getRecommendationPreviewById(recommendationId: String): RecommendationPreviewItem?

    suspend fun getUserItemByUid(uid: String): UserItem?

    suspend fun getFollowingRecommendationsIds(followingUid: String): List<Pair<String, Date>>

    suspend fun getStorageReferenceFromUrl(url: String): StorageReference
}