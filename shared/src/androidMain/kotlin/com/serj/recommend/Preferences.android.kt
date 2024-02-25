package com.serj.recommend

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

private val isShowOnboarding = booleanPreferencesKey(IS_SHOW_ONBOARDING)

class AndroidStorage(
    private val dataStore: DataStore<Preferences>,
    private val scope: CoroutineScope
) {
    var isNeedToShowOnboarding
        get() = dataStore.data.map {
            it[isShowOnboarding] ?: false
        }
        set(value) {
            scope.launch(Dispatchers.IO) {
                dataStore.edit {
                    it[isShowOnboarding] = value.first()
                }
            }
        }
}

actual fun getStorage(): Storage {
    TODO("Not yet implemented")
}