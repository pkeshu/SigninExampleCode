package com.keshar.loginapplication.data

import android.content.Context
import androidx.datastore.DataStore
import androidx.datastore.preferences.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPreferences(
    context: Context
) {
    private val appContext = context.applicationContext
    private val dataStore: DataStore<Preferences>

    init {
        dataStore = appContext.createDataStore(
            "login_data_store"
        )
    }


    val authToken: Flow<String?>
        get() = dataStore.data.map {
            it[AUTH_KEY]
        }


    suspend fun saveAuthToken(authToken: String) {
        dataStore.edit {
            it[AUTH_KEY] = authToken
        }
    }

    suspend fun clearPref() {
        dataStore.edit {
            it.clear()
        }
    }

    companion object {

        private val AUTH_KEY = preferencesKey<String>("auth_key")

    }

}