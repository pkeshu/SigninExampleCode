package com.keshar.loginapplication.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.keshar.loginapplication.databinding.FragmentLoginBinding
import com.keshar.loginapplication.data.network.AuthApi
import com.keshar.loginapplication.data.network.Resource
import com.keshar.loginapplication.data.repository.AuthRepository
import com.keshar.loginapplication.ui.base.BaseFragment
import com.keshar.loginapplication.ui.enable
import com.keshar.loginapplication.ui.handleNetworkError
import com.keshar.loginapplication.ui.home.HomeActivity
import com.keshar.loginapplication.ui.startNewActivity
import com.keshar.loginapplication.ui.visible
import kotlinx.coroutines.launch

class LoginFragment : BaseFragment<AuthViewModel, FragmentLoginBinding, AuthRepository>() {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.progressBar.visible(false)
        binding.loginBtn.enable(false)

        viewModel.loginResponse.observe(viewLifecycleOwner, Observer {
            binding.progressBar.visible(it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
//                    Toast.makeText(requireContext(), it.toString(), Toast.LENGTH_SHORT).show()
                    lifecycleScope.launch {
                        viewModel.saveAuthToken(it.data.user.access_token!!)
                        requireActivity().startNewActivity(HomeActivity::class.java)
                    }

                }
                is Resource.Failure -> handleNetworkError(it) { login() }
            }
        })

        binding.apply {
            loginBtn.setOnClickListener {
                login()
            }

            passwordEdt.addTextChangedListener {
                val email = usernameEdt.text.toString().trim()
                loginBtn.enable(email.isNotEmpty() && it.toString().isNotEmpty())
            }
        }
    }

    private fun login() {
        binding.apply {
            val email = usernameEdt.text.toString().trim()
            val password = passwordEdt.text.toString().trim()
            viewModel.login(email, password)
        }

    }

    override fun getViewModel() = AuthViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentLoginBinding.inflate(inflater, container, false)

    override fun getFragmentRepository(): AuthRepository =
        AuthRepository(remoteDateSource.buildApi(AuthApi::class.java), userPreferences)

}