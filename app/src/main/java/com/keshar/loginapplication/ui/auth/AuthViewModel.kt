package com.keshar.loginapplication.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.keshar.loginapplication.data.network.Resource
import com.keshar.loginapplication.data.repository.AuthRepository
import com.keshar.loginapplication.data.responses.LoginResponse
import com.keshar.loginapplication.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class AuthViewModel(
    private val repository: AuthRepository
) : BaseViewModel(repository) {
    private val _loginResponse: MutableLiveData<Resource<LoginResponse>> = MutableLiveData()

    val loginResponse: LiveData<Resource<LoginResponse>>
        get() = _loginResponse

    fun login(
        email: String,
        password: String
    ) =
        viewModelScope.launch {
            _loginResponse.value = Resource.Loading
            _loginResponse.value = repository.login(email, password)
        }

    suspend fun saveAuthToken(token: String) = viewModelScope.launch {
        repository.saveAuthToken(token)
    }

}