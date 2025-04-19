package com.example.quickchat.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ChatModel(
    val chatId :String = "",
    val users : List<String> = emptyList(),
    val lastMessage : String = ""
) : Parcelable

