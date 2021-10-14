package com.app.chopechatdemo.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.chopechatdemo.data.dto.User
import com.app.chopechatdemo.domain.usecases.UseCase
import com.app.chopechatdemo.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val useCase: UseCase,
) : ViewModel() {

    var loggedInUser = MutableLiveData<Resource<User>>()

    suspend fun loginUser(mobileNumber: String) {
        viewModelScope.launch {
            val currentUser = useCase.LoginUseCase(mobileNumber)
            loggedInUser.postValue(currentUser)
        }
    }
}