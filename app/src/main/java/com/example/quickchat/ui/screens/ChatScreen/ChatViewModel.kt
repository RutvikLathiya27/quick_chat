package com.example.quickchat.ui.screens.ChatScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quickchat.data.models.MessageModel
import com.example.quickchat.data.models.UserModel
import com.example.quickchat.data.repository.repo.ChatRepository
import com.example.quickchat.data.repository.repo.UserRepository
import com.example.quickchat.ui.screens.ChatScreen.models.AllMessageLoadState
import com.example.quickchat.ui.screens.ChatScreen.models.ChatUserState
import com.example.quickchat.ui.utlis.errorLog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class ChatViewModel(
    private val chatRepository: ChatRepository,
    private val userRepository: UserRepository,
    private val userId : String
) : ViewModel() {

    val currentUserId = userId

    private val _lstAllMessages = MutableStateFlow<List<MessageModel?>>(emptyList())
    val lstAllMessage = _lstAllMessages.asStateFlow()

    private val _receiverUser = MutableStateFlow<UserModel?>(null)
    val receiverUser = _receiverUser.asStateFlow()

    fun loadOrCreateChat(selectedUserId: String) {
        viewModelScope.launch {
            val chat = chatRepository.getOrCreateChat(selectedUserId)
            chat.collect { it ->
                when (it) {
                    is ChatUserState.Error -> {}
                    ChatUserState.Loading -> {
                    }
                    is ChatUserState.SUCCESS -> {
                        getAllMessagesWithCurrentUser(it.chatModelId.chatId)
                    }
                }
            }
        }
    }

    fun sendMessage(chatId: String, message: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val messageModel = MessageModel(
                senderId = "", message = message
            )
            chatRepository.sendMessage(chatId, messageModel)
        }
    }


    fun getAllMessagesWithCurrentUser(chatId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            chatRepository.getAllChatWithCurrentUser(chatId).collect {
                if (it is AllMessageLoadState.SUCCESS) {
                    _lstAllMessages.value += it.lstMessages
                    errorLog("lst message 1 > ${lstAllMessage.value.size}")
                    val lastTimestamp =
                        if (it.lstMessages.isEmpty()) System.currentTimeMillis() else it.lstMessages.last()?.timestamp
                    lastTimestamp?.let { it1 ->
                        chatRepository.listenForNewMessages(chatId, it1).collect { newMessage ->
                            _lstAllMessages.value += newMessage
                        }
                    }
                }
            }
        }
    }

    fun getReceiverData(receiverId : String){
        viewModelScope.launch {
            userRepository.getReceiverUSer(receiverId).collect{
                _receiverUser.value = it
            }
        }
    }

}