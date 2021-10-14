package com.app.chopechatdemo.domain.usecases.chatroom

import com.app.chopechatdemo.domain.repository.ChatRepository
import com.app.chopechatdemo.util.Resource
import javax.inject.Inject

class UpdateLatestMessage @Inject constructor(private val chatRepository: ChatRepository) {
    suspend operator fun invoke(id : String, latestMessage : String): Resource<Boolean> = chatRepository.updateLatestMessage(id,latestMessage)
}