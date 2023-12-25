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

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preferences")

object DataStoreUtil {

    private val EMAIL = stringPreferencesKey("user_email")
    private val TOKEN = stringPreferencesKey("user_token")

    suspend fun saveUserToken(token: String) {
        App.application.applicationContext.dataStore.edit { preferences ->
            preferences[TOKEN] = token
        }
    }

    fun getUserToken(): Flow<String> {
        return App.application.dataStore.data.map { preferences ->
            preferences[TOKEN] ?: ""
        }
    }

    suspend fun saveUserEmail(email: String) {
        App.application.applicationContext.dataStore.edit { preferences ->
            preferences[EMAIL] = email
        }
    }

    fun getUserEmail(): Flow<String> {
        return App.application.dataStore.data.map { preferences ->
            preferences[EMAIL] ?: ""
        }
    }

    suspend fun clearUserData() {
        App.application.applicationContext.dataStore.edit { preferences ->
            preferences[EMAIL] = ""
            preferences[TOKEN] = ""
        }
    }
}
