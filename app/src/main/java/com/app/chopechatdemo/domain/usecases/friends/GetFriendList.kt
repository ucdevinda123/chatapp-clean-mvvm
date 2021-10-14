package com.app.chopechatdemo.domain.usecases.friends

import com.app.chopechatdemo.data.dto.User
import com.app.chopechatdemo.domain.repository.ChatRepository
import com.app.chopechatdemo.util.Resource
import javax.inject.Inject

class GetFriendList @Inject constructor(private val chatRepository: ChatRepository) {
    suspend operator fun invoke(): Resource<MutableList<User>> = chatRepository.getListOfFriends()
}