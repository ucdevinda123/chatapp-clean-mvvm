package com.app.chopechatdemo.presentation.ui.chatroom

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.chopechatdemo.R
import com.app.chopechatdemo.data.dto.Message
import com.app.chopechatdemo.databinding.FragmentChatRoomBinding
import com.app.chopechatdemo.presentation.ui.adapter.ChatRoomAdapter
import com.app.chopechatdemo.presentation.viewmodel.ChatRoomViewModel
import com.app.chopechatdemo.util.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ChatRoomFragment : Fragment(R.layout.fragment_chat_room) {

    private lateinit var fragmentChatRoomBinding: FragmentChatRoomBinding
    private val chatRoomViewModel: ChatRoomViewModel by viewModels()
    private val args by navArgs<ChatRoomFragmentArgs>()
    private lateinit var chatRoomId: String
    private lateinit var receiverName: String
    private lateinit var receiverMobileNumber: String
    private var latestMessageSent: String = ""

    @Inject
    lateinit var chatRoomAdapter: ChatRoomAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentChatRoomBinding = FragmentChatRoomBinding.bind(view)
        handleArgs()
        initSendButton()
        initChatRoomRecyclerView()
        getLatestMessages()
        observeChatRoomUpdates()
        observeChatSentStatus()
    }

    private fun observeChatRoomUpdates() {
        chatRoomViewModel.chatRoomMessages.observe(viewLifecycleOwner, {
            when (it) {
                is Resource.Loading -> {
                    print("Loading...")
                }

                is Resource.Success -> {
                    val chatList = it.data
                    chatRoomAdapter.submitList(chatList)
                }

                is Resource.Failure -> {
                    print(it.throwable.localizedMessage)
                }
            }
        })
    }


    private fun observeChatSentStatus() {
        chatRoomViewModel.chatRoomMessagesSubmission.observe(viewLifecycleOwner, {
            when (it) {
                is Resource.Loading -> {
                    print("Loading...")
                }

                is Resource.Success -> {
                    getLatestMessages()
                }

                is Resource.Failure -> {
                    print(it.throwable.localizedMessage)
                }
            }
        })
    }

    private fun getLatestMessages() {
        lifecycleScope.launch {
            chatRoomViewModel.getChatRoomMessages(args.user.channelId)
        }
    }

    private fun handleArgs() {
        receiverName = args.user.name
        chatRoomId = args.user.channelId
        receiverMobileNumber = args.user.mobileNumber
        fragmentChatRoomBinding.apply {
            toolbarGchannel.title = receiverName
        }
    }

    private fun initSendButton() {
        fragmentChatRoomBinding.apply {
            val messageText = etvMessageBox.text
            btnSend.setOnClickListener {
                // get current timestamps
                val currentTimestamp = System.currentTimeMillis().toString().substring(0, 10)
                latestMessageSent = messageText.toString()
                val message = Message(currentTimestamp,
                    "",
                    receiverMobileNumber,
                    receiverName,
                    latestMessageSent,
                    currentTimestamp)
                lifecycleScope.launch {
                    chatRoomViewModel.sendChatMessage(message)
                }
                etvMessageBox.text.clear()
                // rvChatList.scrollToPosition(chatRoomAdapter.itemCount - 1)
            }
        }
    }

    private fun initChatRoomRecyclerView() {
        fragmentChatRoomBinding.apply {
            rvChatList.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            rvChatList.adapter = chatRoomAdapter
        }
    }
}