package com.example.quickchat.ui.screens.ChatScreen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.quickchat.data.models.UserModel

@Composable
fun ChatScreen(
    user: String,
) {

    Text("User : $user")
}