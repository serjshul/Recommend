package com.serj.recommend.android

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.emptyPreferences
import com.serj.recommend.IS_SHOW_ONBOARDING
import com.serj.recommend.IS_SMALL_FAB
import com.serj.recommend.UserPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.io.IOException

/**
 * Class that handles saving and retrieving user preferences
 */
class UserPreferencesRepository(private val dataStore: DataStore<Preferences>) {
    private val TAG = APP_TAG + "UserPreferencesRepository"

    private object PreferencesKeys {
        val isShowOnboarding = booleanPreferencesKey(IS_SHOW_ONBOARDING)
        val isSmallFAB = booleanPreferencesKey(IS_SMALL_FAB)
    }

    /**
     * Get the user preferences flow.
     */
    val userPreferencesFlow: Flow<UserPreferences> = dataStore.data
        .catch { exception ->
            // dataStore.data throws an IOException when an error is encountered when reading data
            if (exception is IOException) {
                Log.e(TAG, "Error reading preferences.", exception)
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            mapUserPreferences(preferences)
        }

    suspend fun fetchInitialPreferences() =
        mapUserPreferences(dataStore.data.first().toPreferences())

    private fun mapUserPreferences(
        preferences: Preferences
    ) = UserPreferences(
        isShowOnboarding = preferences[PreferencesKeys.isShowOnboarding] ?: false,
        isSmallFAB = preferences[PreferencesKeys.isSmallFAB] ?: false
    )
}