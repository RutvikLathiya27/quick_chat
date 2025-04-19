package com.example.quickchat.ui.screens.ChatScreen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import com.example.quickchat.ui.screens.ChatScreen.models.ChatUserState
import com.example.quickchat.ui.utlis.errorLog
import org.koin.androidx.compose.koinViewModel

@Composable
fun ChatScreen(
    user: String,
    chatViewModel: ChatViewModel = koinViewModel()
) {
    val chatStatusCollect = chatViewModel.chatModel.collectAsState()
    LaunchedEffect(Unit) {
//        chatViewModel.loadOrCreateChat(user)
    }

    val messagesState = chatViewModel.lstMessageWithCurrent.collectAsState()
    LaunchedEffect(messagesState.value) {
        errorLog("messages >>>>>>>>>> ${messagesState.value}")
        chatViewModel.getAllMessagesWithCurrentUser(user)
    }

    Text("User : $user")
}