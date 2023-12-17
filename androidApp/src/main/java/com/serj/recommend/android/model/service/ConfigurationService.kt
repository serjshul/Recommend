package com.serj.recommend.android.model.service


interface ConfigurationService {

    val isAddRecommenderSystem: Boolean
    suspend fun fetchConfiguration(): Boolean
}