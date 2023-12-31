package com.serj.recommend.android.repository


interface ConfigurationService {

    val isAddRecommenderSystem: Boolean
    suspend fun fetchConfiguration(): Boolean
}