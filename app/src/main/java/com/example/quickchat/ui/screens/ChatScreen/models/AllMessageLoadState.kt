package com.example.quickchat.ui.screens.ChatScreen.models

import androidx.compose.runtime.Stable
import com.example.quickchat.data.models.MessageModel

@Stable
sealed class AllMessageLoadState {
    object Loading : AllMessageLoadState()
    data class SUCCESS(val lstMessages: ArrayList<MessageModel?>) : AllMessageLoadState()
    data class Error(val msgError: String) : AllMessageLoadState()

}