package com.example.quickchat.ui.screens.ChatScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.quickchat.data.models.ChatModel
import com.example.quickchat.data.repository.repo.ChatRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import androidx.lifecycle.viewModelScope
import com.example.quickchat.data.models.MessageModel
import com.example.quickchat.ui.screens.AuthenticationScreen.models.AuthState
import com.example.quickchat.ui.screens.ChatScreen.models.ChatUserState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ChatViewModel(
    private val chatRepository: ChatRepository
) : ViewModel() {

    private val _chat = MutableStateFlow<ChatUserState?>(ChatUserState.Loading)
    var chatModel : StateFlow<ChatUserState?> = _chat.asStateFlow()

    var chatData : ChatModel? = null

    fun loadOrCreateChat(selectedUserId : String){
        viewModelScope.launch {
            Log.e("TAG", "viewmode >>>>>>>>>>>>>>> launch")
            val chat = chatRepository.getOrCreateChat(selectedUserId)
            chat.collect{ it ->
                when(it){
                    is ChatUserState.Error -> {}
                    ChatUserState.Loading -> {_chat.value = it}
                    is ChatUserState.SUCCESS -> {
                        chatData = it.chatModelId
                        _chat.value = it
                    }
                }
            }
            Log.e("TAG", "viewmode >>>>>>>>>>>>>>> $chat , ${chatModel.value}")
        }
    }

    fun sendMessage(message : String){

        viewModelScope.launch(Dispatchers.IO) {
            val messageModel = MessageModel(
                senderId = "",
                message = message
            )
            chatRepository.sendMessage(chatData?.chatId ?: "", messageModel)
        }
    }

}