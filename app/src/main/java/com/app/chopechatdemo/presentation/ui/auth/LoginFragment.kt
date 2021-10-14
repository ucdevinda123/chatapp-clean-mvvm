package com.app.chopechatdemo.presentation.ui.auth

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.app.chopechatdemo.R
import com.app.chopechatdemo.databinding.FragmentLoginBinding
import com.app.chopechatdemo.presentation.viewmodel.LoginViewModel
import com.app.chopechatdemo.util.Constants
import com.app.chopechatdemo.util.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.fragment_login) {

    private val loginViewModel: LoginViewModel by viewModels()
    private lateinit var fragmentLoginBinding: FragmentLoginBinding
    private val args by navArgs<LoginFragmentArgs>()

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentLoginBinding = FragmentLoginBinding.bind(view)
        initButtonListener()
        observeLogin()
    }

    private fun initButtonListener() {
        fragmentLoginBinding.apply {
            args.number?.let {
                edtMobileLogin.setText(it)
            }
            btnLogin.setOnClickListener {
                val mobile: String = edtMobileLogin.text.toString().trim()
                if (mobile.isNotEmpty()) {
                    lifecycleScope.launch {
                        loginViewModel.loginUser(mobile)
                    }
                } else {
                    Toast.makeText(context, "Please enter your mobile number", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }


    private fun observeLogin() {
        loginViewModel.loggedInUser.observe(viewLifecycleOwner, {
            when (it) {
                is Resource.Success -> {
                    it.data?.let { user ->
                        sharedPreferences.edit()
                            .putString(Constants.LOGGED_IN_USER, user.mobileNumber).apply()
                        sharedPreferences.edit().putString(Constants.LOGGED_IN_USER_NAME, user.name)
                            .apply()
                        val action =
                            LoginFragmentDirections.actionLoginFragmentToFriendsFragment(it.data)
                        findNavController().navigate(action)
                    }
                }

                is Resource.Failure -> {
                    Toast.makeText(context, it.throwable.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}