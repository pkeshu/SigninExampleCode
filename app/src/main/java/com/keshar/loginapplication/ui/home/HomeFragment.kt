package com.keshar.loginapplication.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import com.keshar.loginapplication.R
import com.keshar.loginapplication.data.network.Resource
import com.keshar.loginapplication.data.network.UserApi
import com.keshar.loginapplication.data.repository.UserRepository
import com.keshar.loginapplication.data.responses.User
import com.keshar.loginapplication.databinding.FragmentHomeBinding
import com.keshar.loginapplication.ui.base.BaseFragment
import com.keshar.loginapplication.ui.visible
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class HomeFragment : BaseFragment<HomeViewModel, FragmentHomeBinding, UserRepository>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.progressBar.visible(false)

        viewModel.getUser()
        viewModel.user.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Success -> {
                    binding.progressBar.visible(false)
                    updateUI(it.data.user)
                }
                is Resource.Failure -> {
                    binding.progressBar.visible(false)
                    Toast.makeText(requireContext(), it.errorBody.toString(), Toast.LENGTH_SHORT)
                        .show()
                }
                is Resource.Loading -> {
                    binding.progressBar.visible(true)

                }
            }
        })

        binding.logoutBtn.setOnClickListener {
            logout()
        }
    }

    private fun updateUI(user: User) {
        binding.apply {
            txtvId.text = user.id.toString()
            nameTxV.text = user.name
            emailTxtV.text = user.email
        }
    }

    override fun getViewModel() = HomeViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentHomeBinding.inflate(inflater, container, false)

    override fun getFragmentRepository(): UserRepository {
        val token = runBlocking { userPreferences.authToken.first() }
        val api = remoteDateSource.buildApi(UserApi::class.java, token)
        return UserRepository(api)
    }

}