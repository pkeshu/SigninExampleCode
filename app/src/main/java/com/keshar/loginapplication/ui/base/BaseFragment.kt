package com.keshar.loginapplication.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.keshar.loginapplication.data.UserPreferences
import com.keshar.loginapplication.data.network.RemoteDataSource
import com.keshar.loginapplication.data.network.UserApi
import com.keshar.loginapplication.data.repository.BaseRepository
import com.keshar.loginapplication.ui.auth.AuthActivity
import com.keshar.loginapplication.ui.startNewActivity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

abstract class BaseFragment<VM : BaseViewModel, B : ViewBinding, R : BaseRepository> : Fragment() {
    protected lateinit var binding: B
    protected lateinit var viewModel: VM
    protected val remoteDateSource = RemoteDataSource()
    protected lateinit var userPreferences: UserPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        userPreferences = UserPreferences(requireContext())
        binding = getFragmentBinding(inflater, container)

        val factory = ViewModelFactory(getFragmentRepository())
        viewModel = ViewModelProvider(this, factory).get(getViewModel())

        lifecycleScope.launch { userPreferences.authToken.first() }

        return binding.root
    }

    fun logout() = lifecycleScope.launch {
        val authToken = userPreferences.authToken.first()
        val api = remoteDateSource.buildApi(UserApi::class.java, authToken)
        viewModel.logout(api)
        userPreferences.clearPref()
        requireActivity().startNewActivity(AuthActivity::class.java)


    }

    abstract fun getViewModel(): Class<VM>
    abstract fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?): B
    abstract fun getFragmentRepository(): R
}