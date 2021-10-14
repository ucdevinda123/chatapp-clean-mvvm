package com.app.chopechatdemo.presentation.ui.auth

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.app.chopechatdemo.R
import com.app.chopechatdemo.data.dto.User
import com.app.chopechatdemo.databinding.FragmentRegisterBinding
import com.app.chopechatdemo.presentation.viewmodel.RegisterViewModel
import com.app.chopechatdemo.util.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterUserFragment : Fragment(R.layout.fragment_register) {

    private val registerViewModel: RegisterViewModel by viewModels()
    private lateinit var fragmentRegisterBinding: FragmentRegisterBinding
    private lateinit var mobileNumber: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentRegisterBinding = FragmentRegisterBinding.bind(view)
        initButtonListener()
        observeRegistration()
    }

    private fun initButtonListener() {
        fragmentRegisterBinding.apply {
            btnRegister.setOnClickListener {
                val name: String = edtName.text.toString()
                mobileNumber = edtMobile.text.toString()
                if (name.isNotEmpty() && mobileNumber.isNotEmpty()) {
                    lifecycleScope.launch {
                        val user = User(mobileNumber, mobileNumber, name)
                        registerViewModel.registerUser(user)
                    }
                } else {
                    Toast.makeText(context, "Fields Can not be empty", Toast.LENGTH_SHORT).show()
                }
            }
            btnLoginAlreadyRegistered.setOnClickListener {
                mobileNumber = ""
                navigateToLogin()
            }
        }
    }


    private fun observeRegistration() {
        registerViewModel.registrationStatus.observe(viewLifecycleOwner, {
            when (it) {
                is Resource.Success -> {
                    if (it.data) {
                        navigateToLogin()
                    }
                }

                is Resource.Failure -> {
                    Toast.makeText(context, it.throwable.localizedMessage, Toast.LENGTH_SHORT)
                        .show()
                }
            }
        })
    }


    private fun navigateToLogin() {
        val action =
            RegisterUserFragmentDirections.actionRegisterUserFragmentToLoginFragment(mobileNumber)
        findNavController().navigate(action)
    }

}