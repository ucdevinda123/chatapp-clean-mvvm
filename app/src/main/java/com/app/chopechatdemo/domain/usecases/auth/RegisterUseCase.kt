package com.app.chopechatdemo.domain.usecases.auth

import com.app.chopechatdemo.data.dto.User
import com.app.chopechatdemo.domain.repository.ChatRepository
import com.app.chopechatdemo.util.Resource
import javax.inject.Inject

class RegisterUseCase @Inject constructor(private val chatRepository: ChatRepository) {
    suspend operator fun invoke(user: User): Resource<Boolean> = chatRepository.register(user)
}