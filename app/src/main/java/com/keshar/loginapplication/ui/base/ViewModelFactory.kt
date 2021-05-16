package com.keshar.loginapplication.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.keshar.loginapplication.data.repository.AuthRepository
import com.keshar.loginapplication.data.repository.BaseRepository
import com.keshar.loginapplication.data.repository.UserRepository
import com.keshar.loginapplication.ui.auth.AuthViewModel
import com.keshar.loginapplication.ui.home.HomeViewModel
import java.lang.IllegalArgumentException

class ViewModelFactory(
    private val respository: BaseRepository
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(AuthViewModel::class.java) -> {
                AuthViewModel(respository as AuthRepository) as T
            }
            modelClass.isAssignableFrom(HomeViewModel::class.java)->HomeViewModel(respository as UserRepository) as T
            else -> {
                throw IllegalArgumentException("View Model class not found!")
            }
        }
    }
}