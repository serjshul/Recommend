package com.serj.recommend.android.services

interface ConfigurationService {
    val isAddRecommenderSystem: Boolean
    suspend fun fetchConfiguration(): Boolean
}