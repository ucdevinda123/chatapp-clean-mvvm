package com.app.chopechatdemo.presentation.ui.adapter

import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.app.chopechatdemo.data.dto.Message
import com.app.chopechatdemo.databinding.ItemChatRoomMessageBinding
import com.app.chopechatdemo.util.Constants
import javax.inject.Inject

class ChatRoomAdapter @Inject constructor(val sharedPreferences: SharedPreferences) :
    ListAdapter<Message, ChatRoomAdapter.ChatRoomViewHolder>(ChatMessageDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatRoomViewHolder {
        var binding =
            ItemChatRoomMessageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ChatRoomViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChatRoomViewHolder, position: Int) {
        val media = getItem(position)
        if (media != null) {
            holder.bind(media)
        }
    }

    inner class ChatRoomViewHolder(private val itemChatRoomMessageBinding: ItemChatRoomMessageBinding) :
        RecyclerView.ViewHolder(itemChatRoomMessageBinding.root) {

        fun bind(message: Message) {
            val loggedInUser = sharedPreferences.getString(Constants.LOGGED_IN_USER, "").toString()
            itemChatRoomMessageBinding.apply {
                //Me
                if (message.toMobile == loggedInUser) {
                    hideAndShowReceiver(itemChatRoomMessageBinding, View.GONE)
                    hideAndShowMe(itemChatRoomMessageBinding, View.VISIBLE)
                    tvChatroomMessageMeContent.text = message.message
                } else {
                    hideAndShowReceiver(itemChatRoomMessageBinding, View.VISIBLE)
                    hideAndShowMe(itemChatRoomMessageBinding, View.GONE)
                    tvChatReceiverMessageContent.text = message.message
                    textGchatUserOther.text = message.name
                }
            }
        }
    }

    class ChatMessageDiffUtil : DiffUtil.ItemCallback<Message>() {
        override fun areItemsTheSame(oldItem: Message, newItem: Message): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Message, newItem: Message): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }


    private fun hideAndShowReceiver(
        itemChatRoomMessageBinding: ItemChatRoomMessageBinding,
        visibility: Int,
    ) {
        itemChatRoomMessageBinding.apply {
            cvChatReceiver.visibility = visibility
            tvChatReceiverTimestamp.visibility = visibility
            textGchatUserOther.visibility = visibility
            imageGchatProfileOther.visibility = visibility
            tvChatReceiverDate.visibility = visibility
        }
    }

    private fun hideAndShowMe(
        itemChatRoomMessageBinding: ItemChatRoomMessageBinding,
        visibility: Int,
    ) {
        itemChatRoomMessageBinding.apply {
            tvChatMeDate.visibility = visibility
            tvChatMeTime.visibility = visibility
            cvMessageMe.visibility = visibility
        }
    }
}