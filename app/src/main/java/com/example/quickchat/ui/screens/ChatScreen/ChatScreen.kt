package com.example.quickchat.ui.screens.ChatScreen

import android.util.Log
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import com.example.quickchat.data.models.UserModel
import com.example.quickchat.ui.screens.ChatScreen.models.ChatUserState
import org.koin.androidx.compose.koinViewModel

@Composable
fun ChatScreen(
    user: String,
    chatViewModel: ChatViewModel = koinViewModel()
) {
    val chatStatusCollect = chatViewModel.chatModel.collectAsState()
    LaunchedEffect(Unit) {
        chatViewModel.loadOrCreateChat(user)
    }

    LaunchedEffect(chatStatusCollect.value) {
        Log.e("TAG", "screen >>>>>>>>>>>>>>> ${chatStatusCollect.value is ChatUserState.SUCCESS}")
        if(chatStatusCollect.value is ChatUserState.SUCCESS) {
            chatViewModel.sendMessage("Hello sam, Ted this side.")
        }
    }

    Text("User : $user")
}