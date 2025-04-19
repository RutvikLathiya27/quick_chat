package com.example.quickchat.ui.screens.ChatScreen.models

import androidx.compose.runtime.Stable
import com.example.quickchat.data.models.ChatModel
import com.example.quickchat.data.models.ChatedUserModel

@Stable
sealed class ChatedUserState {
    object Loading : ChatedUserState()
    data class SUCCESS(val lstUsersChat : List<ChatedUserModel> ) : ChatedUserState()
    data class Error(val msgError: String) : ChatedUserState()
}