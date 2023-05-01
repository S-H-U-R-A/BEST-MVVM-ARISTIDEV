package com.shura.mvvmaris.data.local.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject


class UserPreferences @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : DataPreference<String> {

    companion object PreferencesKeys {
        val KEY_NAME = stringPreferencesKey("key_name")
    }

    override val data: Flow<String>
        get() = dataStore.data
            .catch {
                if (it is IOException) {
                    it.printStackTrace()
                    emit(emptyPreferences())
                } else throw it
            }.map { preferences ->
                preferences[KEY_NAME] ?: ""
            }

    override suspend fun saveData( name: String) {
        dataStore.edit { mutablePreference ->
            mutablePreference[KEY_NAME] = name
        }
    }
}