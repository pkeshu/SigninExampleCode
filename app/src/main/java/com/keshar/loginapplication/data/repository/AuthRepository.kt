package com.keshar.loginapplication.data.repository

import com.keshar.loginapplication.data.UserPreferences
import com.keshar.loginapplication.data.network.AuthApi

class AuthRepository(
    private val api: AuthApi,
    private val preferences: UserPreferences
) : BaseRepository() {

    suspend fun login(
        email: String,
        password: String
    ) = safeApiCall {
        api.login(email, password)
    }

    suspend fun saveAuthToken(authToken: String) {
        preferences.saveAuthToken(authToken)
    }

}