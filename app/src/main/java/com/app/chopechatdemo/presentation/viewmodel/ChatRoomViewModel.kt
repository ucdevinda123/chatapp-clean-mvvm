package com.app.chopechatdemo.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.chopechatdemo.data.dto.Message
import com.app.chopechatdemo.domain.usecases.UseCase
import com.app.chopechatdemo.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatRoomViewModel @Inject constructor(private val useCase: UseCase) :
    ViewModel() {

    var chatRoomMessages = MutableLiveData<Resource<MutableList<Message>>>()
    var chatRoomMessagesSubmission = MutableLiveData<Resource<Boolean>>()

    suspend fun sendChatMessage(message: Message) {
        var sentStatus = useCase.SendChatMessageUseCase(message)
        chatRoomMessagesSubmission.postValue(sentStatus)
    }

    suspend fun getChatRoomMessages(chatRoomId: String) {
        val chatMessages = useCase.GetChatMessages(chatRoomId)
        chatRoomMessages.postValue(chatMessages)
    }
}