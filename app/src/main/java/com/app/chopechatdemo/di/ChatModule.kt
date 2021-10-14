package com.app.chopechatdemo.di

import android.content.Context
import android.content.SharedPreferences
import com.app.chopechatdemo.data.repository.ChatRepositoryImpl
import com.app.chopechatdemo.domain.repository.ChatRepository
import com.app.chopechatdemo.domain.usecases.UseCase
import com.app.chopechatdemo.domain.usecases.auth.LoginUseCase
import com.app.chopechatdemo.domain.usecases.auth.RegisterUseCase
import com.app.chopechatdemo.domain.usecases.chatroom.GetChatRoomMessages
import com.app.chopechatdemo.domain.usecases.chatroom.SendChatRoomMessages
import com.app.chopechatdemo.domain.usecases.chatroom.UpdateChannelReference
import com.app.chopechatdemo.domain.usecases.chatroom.UpdateLatestMessage
import com.app.chopechatdemo.domain.usecases.friends.GetFriendList
import com.app.chopechatdemo.util.Constants
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ChatModule {

    @Provides
    @Singleton
    fun provideRealTimeDb(): FirebaseDatabase {
        return FirebaseDatabase.getInstance(Constants.FIREBASE_DB_URL)
    }

    @Singleton
    @Provides
    fun provideSharedPreference(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences(Constants.SHARED_PREF_NAME, Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideChatRepo(
        databaseReference: FirebaseDatabase,
        sharedPreferences: SharedPreferences,
    ): ChatRepository {
        return ChatRepositoryImpl(databaseReference, sharedPreferences)
    }

    @Provides
    @Singleton
    fun provideUseCases(chatRepository: ChatRepository): UseCase {
        return UseCase(
            GetFriendList(chatRepository),
            SendChatRoomMessages(chatRepository),
            GetChatRoomMessages(chatRepository),
            UpdateLatestMessage(chatRepository),
            RegisterUseCase(chatRepository),
            LoginUseCase(chatRepository),
            UpdateChannelReference(chatRepository)
        )
    }

}