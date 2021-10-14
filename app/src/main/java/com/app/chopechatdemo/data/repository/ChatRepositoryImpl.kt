package com.app.chopechatdemo.data.repository

import android.content.SharedPreferences
import com.app.chopechatdemo.data.dto.Message
import com.app.chopechatdemo.data.dto.User
import com.app.chopechatdemo.domain.repository.ChatRepository
import com.app.chopechatdemo.util.Constants
import com.app.chopechatdemo.util.Resource
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import kotlinx.coroutines.tasks.await
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(
    private val firebaseDatabase: FirebaseDatabase,
    val sharedPreferences: SharedPreferences,
) :
    ChatRepository {

    private var channelId: String = ""
    override suspend fun getListOfFriends(): Resource<MutableList<User>> {
        return try {
            val friendList = mutableListOf<User>()
            var childElements = firebaseDatabase.getReference("friends").get().await().children
            val loggedInMobile =
                sharedPreferences.getString(Constants.LOGGED_IN_USER, "").toString()
            childElements.forEach {
                val mobile = it.key.toString()
                if (loggedInMobile != mobile) {
                    val name = it.child("name").value.toString()
                    val id = it.child("id").value.toString()
                    val lastMessage = it.child("lastMessage").value.toString()
                    val channelId = it.child("channelId").value.toString()
                    friendList.add(User(id, mobile, name, lastMessage, channelId))
                }
            }
            Resource.Success(friendList)
        } catch (exception: Exception) {
            Resource.Failure(exception)
        }
    }

    override suspend fun getChatRoomMessages(chatRoomId: String): Resource<MutableList<Message>> {
        return try {
            channelId = chatRoomId
            val messageList = mutableListOf<Message>()
            val loggedInMobile =
                sharedPreferences.getString(Constants.LOGGED_IN_USER, "").toString()

            firebaseDatabase.getReference("chat")
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        dataSnapshot.child(channelId)
                            .child("messages").children.forEach { messages ->
                            val messageTimestamps = messages.key
                            val mobileNo = messages.child("mobile").value.toString()
                            val senderName = messages.child("senderName").value.toString()
                            val messageText = messages.child("msg").value.toString()
                            val timestamp = Timestamp(messageTimestamps!!.toLong())
                            val date = Date(timestamp.time)
                            val simpleTimeFormat =
                                SimpleDateFormat("hh:mm aa", Locale.getDefault())

                            messageList.add(Message(
                                messageTimestamps,
                                loggedInMobile,
                                mobileNo,
                                senderName,
                                messageText,
                                simpleTimeFormat.format(date)))
                        }

                        Resource.Success(messageList)
                    }

                    override fun onCancelled(error: DatabaseError) {}
                })
            Resource.Success(messageList)
        } catch (exception: Exception) {
            Resource.Failure(exception)
        }
    }


    override suspend fun sendChatMessage(message: Message): Resource<Boolean> {
        return try {
            if ((channelId == "null") or channelId.isEmpty()) {
                channelId = message.id + message.toMobile
            }
            val loggedInMobile =
                sharedPreferences.getString(Constants.LOGGED_IN_USER, "").toString()
            val loggedInUserName =
                sharedPreferences.getString(Constants.LOGGED_IN_USER_NAME, "").toString()
            firebaseDatabase.getReference("chat").child(channelId).child("user_1")
                .setValue(loggedInMobile)
            firebaseDatabase.getReference("chat").child(channelId).child("user_2")
                .setValue(message.toMobile)
            firebaseDatabase.getReference("chat").child(channelId).child("messages")
                .child(message.createdAt).child("msg").setValue(message.message)
            firebaseDatabase.getReference("chat").child(channelId).child("messages")
                .child(message.createdAt).child("mobile").setValue(loggedInMobile)
            firebaseDatabase.getReference("chat").child(channelId).child("messages")
                .child(message.createdAt).child("senderName").setValue(loggedInUserName)
            Resource.Success(true)
            updateLatestMessage(loggedInMobile, message.message)
            updateLatestMessage(message.toMobile, message.message)

            updateChannelReference(loggedInMobile, channelId)
            updateChannelReference(message.toMobile, channelId)
        } catch (exception: Exception) {
            Resource.Failure(exception)
        }
    }

    override suspend fun updateLatestMessage(id: String, latestMessage: String): Resource<Boolean> {
        return try {
            firebaseDatabase.getReference("friends")
                .child(id).child("lastMessage")
                .setValue(latestMessage)
            Resource.Success(true)
        } catch (exception: Exception) {
            Resource.Failure(exception)
        }
    }

    override suspend fun updateChannelReference(id: String, channelId: String): Resource<Boolean> {
        return try {
            firebaseDatabase.getReference("friends")
                .child(id).child("channelId")
                .setValue(channelId)
            Resource.Success(true)
        } catch (exception: Exception) {
            Resource.Failure(exception)
        }
    }

    override suspend fun register(user: User): Resource<Boolean> {
        return try {
            val rootNode = firebaseDatabase.getReference("friends").child(user.mobileNumber)
            rootNode.child("id").setValue(user.id)
            rootNode.child("name").setValue(user.name)
            rootNode.child("lastMessage").setValue(user.lastMessage)
            Resource.Success(true)
        } catch (exception: Exception) {
            Resource.Failure(exception)
        }
    }

    override suspend fun login(number: String): Resource<User> {
        return try {
            val userNode = firebaseDatabase.getReference("friends").child(number).get().await()
            val id = userNode.child("id").value.toString()
            val name = userNode.child("name").value.toString()
            val channelId = userNode.child("channelId").value.toString()
            Resource.Success(User(id, number, name, channelId = channelId))
        } catch (exception: Exception) {
            Resource.Failure(exception)
        }
    }
}