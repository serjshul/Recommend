package com.serj.recommend.android.model.service

import android.graphics.Bitmap
import com.serj.recommend.android.model.Banner
import com.serj.recommend.android.model.Category
import com.serj.recommend.android.model.Recommendation
import com.serj.recommend.android.model.items.RecommendationItem
import com.serj.recommend.android.model.items.RecommendationPreview
import com.serj.recommend.android.model.items.UserItem
import kotlinx.coroutines.flow.Flow


interface StorageService {

    val recommendations: Flow<List<Recommendation>>

    val banners: Flow<List<Banner>>

    val categories: Flow<List<Category>>

    suspend fun getRecommendationById(recommendationId: String): Recommendation?

    suspend fun getBannerById(bannerId: String): Banner?

    suspend fun getRecommendationItemById(recommendationId: String): RecommendationItem?

    suspend fun getRecommendationPreviewById(recommendationId: String): RecommendationPreview?

    suspend fun getUserItem(uid: String): UserItem?

    suspend fun getFeedData(followingUid: String): List<Recommendation>

    suspend fun downloadImage(gsReference: String): Bitmap?
}