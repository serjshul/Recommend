package com.serj.recommend.android.services.impl

import com.google.firebase.BuildConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.get
import com.google.firebase.remoteconfig.remoteConfigSettings
import com.serj.recommend.android.services.ConfigurationService
import com.serj.recommend.android.services.model.trace
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import com.serj.recommend.android.R.xml as AppConfig

class ConfigurationServiceImpl @Inject constructor(
    private val remoteConfig: FirebaseRemoteConfig
) : ConfigurationService {

    init {
        if (BuildConfig.DEBUG) {
            val configSettings = remoteConfigSettings {
                minimumFetchIntervalInSeconds = 0
            }
            remoteConfig.setConfigSettingsAsync(configSettings)
        }

        remoteConfig.setDefaultsAsync(AppConfig.remote_config_defaults)
    }

    override suspend fun fetchConfiguration(): Boolean =
        trace(FETCH_CONFIG_TRACE) {
            remoteConfig.fetchAndActivate().await()
        }

    override val isAddRecommenderSystem: Boolean
        get() = remoteConfig[ADD_RECOMMENDER_SYSTEM_KEY].asBoolean()

    companion object {
        private const val ADD_RECOMMENDER_SYSTEM_KEY = "add_recommender_system"
        private const val FETCH_CONFIG_TRACE = "fetchConfig"
    }
}