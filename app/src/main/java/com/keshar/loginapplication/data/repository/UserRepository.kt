package com.keshar.loginapplication.data.repository

import com.keshar.loginapplication.data.UserPreferences
import com.keshar.loginapplication.data.network.AuthApi
import com.keshar.loginapplication.data.network.UserApi

class UserRepository(
    private val api: UserApi
) : BaseRepository() {

    suspend fun getUser() = safeApiCall {
        api.getUser()
    }

}