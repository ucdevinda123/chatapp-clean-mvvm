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
class FriendsViewModel @Inject constructor(private val useCase: UseCase) : ViewModel() {
    var chatRoomFriends = MutableLiveData<Resource<MutableList<User>>>()

    suspend fun getFriendsList() {
        viewModelScope.launch {
            val friends = useCase.GetFriendsListUseCase()
            chatRoomFriends.postValue(friends)
        }
    }
}