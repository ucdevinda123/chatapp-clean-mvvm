package com.app.chopechatdemo.domain.usecases.chatroom

import com.app.chopechatdemo.domain.repository.ChatRepository
import com.app.chopechatdemo.util.Resource
import javax.inject.Inject

class UpdateChannelReference @Inject constructor(private val chatRepository: ChatRepository) {
    suspend operator fun invoke(id : String, channelId : String): Resource<Boolean> = chatRepository.updateChannelReference(id,channelId)
}