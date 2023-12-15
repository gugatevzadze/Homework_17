package com.example.homework_17.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.homework_17.App
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

object DataStoreUtil {

    val EMAIL = stringPreferencesKey("email")

    suspend fun saveUserEmail(email: String) {
        App.application.applicationContext.dataStore.edit { settings -> settings[EMAIL] = email }
    }

    fun getUserEmail(): Flow<String> {
        return App.application.dataStore.data.map { preferences ->
                preferences[EMAIL] ?: ""
            }
    }
}




