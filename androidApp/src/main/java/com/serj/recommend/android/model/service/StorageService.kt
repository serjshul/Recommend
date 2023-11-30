package com.serj.recommend.android.model.service

import com.serj.recommend.android.model.Article
import com.serj.recommend.android.model.Category
import kotlinx.coroutines.flow.Flow

interface StorageService {
    val articles: Flow<List<Article>>
    val categories: Flow<List<Category>>
    suspend fun getArticle(articleId: String): Article?
    suspend fun saveArticle(article: Article): String
    suspend fun updateArticle(article: Article)
    suspend fun deleteArticle(articleId: String)

    suspend fun getCategory(categoryId: String): Category?
}