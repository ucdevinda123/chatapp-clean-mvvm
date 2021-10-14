package com.app.chopechatdemo.domain.usecases.chatroom

import com.app.chopechatdemo.data.dto.Message
import com.app.chopechatdemo.domain.repository.ChatRepository
import com.app.chopechatdemo.util.Resource
import javax.inject.Inject

class GetChatRoomMessages @Inject constructor(private val chatRepository: ChatRepository) {
    suspend operator fun invoke(chatRoomId : String): Resource<MutableList<Message>> = chatRepository.getChatRoomMessages(chatRoomId)
}