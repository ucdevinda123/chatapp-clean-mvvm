package com.app.chopechatdemo.domain.usecases.auth

import com.app.chopechatdemo.data.dto.User
import com.app.chopechatdemo.domain.repository.ChatRepository
import com.app.chopechatdemo.util.Resource
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val chatRepository: ChatRepository) {
    suspend operator fun invoke(number : String): Resource<User> = chatRepository.login(number)
}