package com.example.quickchat.ui.screens.ChatScreen.models

import androidx.compose.runtime.Stable
import com.example.quickchat.data.models.ChatModel

@Stable
sealed class ChatUserState {
    object Loading : ChatUserState()
    data class SUCCESS(val chatModelId : ChatModel) : ChatUserState()
    data class Error(val msgError: String) : ChatUserState()

}