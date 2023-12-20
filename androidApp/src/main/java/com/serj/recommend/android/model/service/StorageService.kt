package com.serj.recommend.android.model.service

import android.graphics.Bitmap
import android.net.Uri
import com.serj.recommend.android.model.Banner
import com.serj.recommend.android.model.BannerItem
import com.serj.recommend.android.model.Category
import com.serj.recommend.android.model.CategoryItem
import com.serj.recommend.android.model.Post
import com.serj.recommend.android.model.Recommendation
import com.serj.recommend.android.model.RecommendationItem
import com.serj.recommend.android.model.UserItem
import kotlinx.coroutines.flow.Flow


interface StorageService {
    val recommendations: Flow<List<Recommendation>>

    val banners: Flow<List<Banner>>

    val categories: Flow<List<Category>>

    suspend fun getRecommendation(recommendationId: String): Recommendation?

    suspend fun getRecommendationItem(recommendationId: String): RecommendationItem?

    suspend fun getBanner(bannerId: String): Banner?

    suspend fun getBannerItem(recommendationId: String, coverType: String): BannerItem?

    suspend fun getCategory(categoryId: String): Category?

    suspend fun getCategoryItems(recommendationId: String, coverType: String): CategoryItem?

    suspend fun getFollowingPosts(followingUid: String): List<Post>

    suspend fun getUserItem(uid: String): UserItem?

    suspend fun downloadImage(gsReference: String): Bitmap?

    suspend fun uploadImage(uri: Uri, folderName: String, fileName: String): String
}