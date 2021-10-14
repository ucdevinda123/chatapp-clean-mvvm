package com.app.chopechatdemo.domain.usecases

import com.app.chopechatdemo.domain.usecases.auth.LoginUseCase
import com.app.chopechatdemo.domain.usecases.auth.RegisterUseCase
import com.app.chopechatdemo.domain.usecases.chatroom.GetChatRoomMessages
import com.app.chopechatdemo.domain.usecases.chatroom.SendChatRoomMessages
import com.app.chopechatdemo.domain.usecases.chatroom.UpdateChannelReference
import com.app.chopechatdemo.domain.usecases.chatroom.UpdateLatestMessage
import com.app.chopechatdemo.domain.usecases.friends.GetFriendList

class UseCase(
    var GetFriendsListUseCase: GetFriendList,
    var SendChatMessageUseCase: SendChatRoomMessages,
    var GetChatMessages: GetChatRoomMessages,
    var UpdateLatestMessageUseCase : UpdateLatestMessage,
    var RegistrationUseCase : RegisterUseCase,
    var LoginUseCase : LoginUseCase,
    var UpdateChannelReference : UpdateChannelReference
)
