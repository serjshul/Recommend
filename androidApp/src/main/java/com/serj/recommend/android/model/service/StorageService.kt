package com.serj.recommend.android.model.service

import android.graphics.Bitmap
import android.net.Uri
import com.serj.recommend.android.model.Category
import com.serj.recommend.android.model.CategoryItem
import com.serj.recommend.android.model.Recommendation
import kotlinx.coroutines.flow.Flow


interface StorageService {
    val recommendations: Flow<List<Recommendation>>
    val categories: Flow<List<Category>>

    suspend fun getRecommendation(recommendationId: String): Recommendation?

    suspend fun getCategoryItem(recommendationId: String): CategoryItem?

    suspend fun downloadImage(gsReference: String): Bitmap?

    suspend fun uploadImage(uri: Uri, folderName: String, fileName: String): String

}