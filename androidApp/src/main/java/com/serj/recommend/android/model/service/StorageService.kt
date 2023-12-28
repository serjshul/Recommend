package com.serj.recommend.android.model.service

import android.graphics.Bitmap
import com.serj.recommend.android.model.Banner
import com.serj.recommend.android.model.Category
import com.serj.recommend.android.model.Recommendation
import com.serj.recommend.android.model.items.CategoryItem
import com.serj.recommend.android.model.items.RecommendationItem
import com.serj.recommend.android.model.items.UserItem
import kotlinx.coroutines.flow.Flow


interface StorageService {

    val recommendations: Flow<List<Recommendation>>

    val banners: Flow<List<Banner>>

    val categories: Flow<List<Category>>

    suspend fun getRecommendation(recommendationId: String): Recommendation?

    suspend fun getCategory(categoryId: String): Category?

    suspend fun getBanner(bannerId: String): Banner?

    suspend fun getRecommendationItem(recommendationId: String): RecommendationItem?

    suspend fun getCategoryItem(recommendationId: String): CategoryItem?

    suspend fun getUserItem(uid: String): UserItem?

    suspend fun getFollowingRecommendations(followingUid: String): List<Recommendation>

    suspend fun downloadImage(gsReference: String): Bitmap?
}