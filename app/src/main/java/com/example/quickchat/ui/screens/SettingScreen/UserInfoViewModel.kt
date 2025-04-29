package com.example.quickchat.ui.screens.SettingScreen

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


class UserInfoViewModel(
    private val userRepository: UserRepository,
    private val userId: String
) : ViewModel() {

    private val _userInfo = MutableStateFlow<UserModel?>(null)
    val userInfo = _userInfo.asStateFlow()

    fun getUserProfileInfo(){
        viewModelScope.launch {
            userRepository.getReceiverUSer(userId).collect {
                _userInfo.value = it
            }
        }
    }

}