package com.keshar.loginapplication.ui.base

import androidx.lifecycle.ViewModel
import com.keshar.loginapplication.data.network.UserApi
import com.keshar.loginapplication.data.repository.BaseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

abstract class BaseViewModel(
    private val repository: BaseRepository
) : ViewModel(){

    suspend fun logout(api: UserApi)= withContext(Dispatchers.IO){
        repository.logout(api)
    }
}