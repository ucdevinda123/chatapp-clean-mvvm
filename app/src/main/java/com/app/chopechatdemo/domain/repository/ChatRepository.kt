package com.app.chopechatdemo.domain.repository

import com.app.chopechatdemo.data.dto.Message
import com.app.chopechatdemo.data.dto.User
import com.app.chopechatdemo.util.Resource

interface ChatRepository {

    suspend fun getListOfFriends(): Resource<MutableList<User>>

    suspend fun getChatRoomMessages(chatRoomId : String): Resource<MutableList<Message>>

    suspend fun sendChatMessage(message: Message): Resource<Boolean>

    suspend fun updateLatestMessage(id: String, latestMessage: String): Resource<Boolean>

    suspend fun updateChannelReference(id: String, channelId: String): Resource<Boolean>

    suspend fun register(user: User): Resource<Boolean>

    suspend fun login(number: String): Resource<User>

}