package com.serj.recommend.android

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import com.serj.recommend.IS_SHOW_ONBOARDING
import kotlinx.coroutines.flow.map

private val _NEED_TO_SHOW_ONBOARDING = booleanPreferencesKey(IS_SHOW_ONBOARDING)

class AndroidPreferences(
    private val dataStore: DataStore<Preferences>
) {
    val isNeedToShowOnboarding
        get() = dataStore.data.map {
            it[_NEED_TO_SHOW_ONBOARDING] ?: false
        }

    suspend fun setIsNeedToShowOnboarding(value: Boolean) {
        dataStore.edit {
            it[_NEED_TO_SHOW_ONBOARDING] = value
        }
    }
}