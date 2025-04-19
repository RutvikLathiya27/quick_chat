package com.example.quickchat.ui.screens.HomeScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quickchat.data.repository.repo.ChatRepository
import com.example.quickchat.ui.screens.ChatScreen.models.ChatUserState
import com.example.quickchat.ui.screens.ChatScreen.models.ChatedUserState
import com.example.quickchat.ui.utlis.errorLog
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UserChatHistoryViewModel(
    private val chatRepository: ChatRepository
) : ViewModel() {

    private val _userChatHistory = MutableStateFlow<ChatedUserState>(ChatedUserState.Loading)
    var userChatHistory : StateFlow<ChatedUserState?> = _userChatHistory.asStateFlow()


    fun getAllPastChatUser() {
        viewModelScope.launch {
            val chat = chatRepository.getUsersChattedWithCurrentUser()
            chat.collect{ it ->
                when(it){
                    is ChatedUserState.Error -> {
                        _userChatHistory.value = it
                    }
                    is ChatedUserState.Loading -> {
                        _userChatHistory.value = it
                    }
                    is ChatedUserState.SUCCESS -> {
                        _userChatHistory.value = it
                        errorLog(">>>>>>>>>> ${it.lstUsersChat}")
                    }
                }
            }
        }
    }

}