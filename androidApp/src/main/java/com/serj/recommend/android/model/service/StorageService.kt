package com.serj.recommend.android.model.service

import android.net.Uri
import com.serj.recommend.android.model.Category
import com.serj.recommend.android.model.Recommendation
import kotlinx.coroutines.flow.Flow


interface StorageService {
    val recommendations: Flow<List<Recommendation>>
    val categories: Flow<List<Category>>

    suspend fun getRecommendation(recommendationId: String): Recommendation?

    suspend fun downloadImage(gsReference: String): String

    suspend fun uploadImage(uri: Uri, folderName: String, fileName: String): String

}