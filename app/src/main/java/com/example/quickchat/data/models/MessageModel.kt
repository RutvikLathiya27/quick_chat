package com.example.quickchat.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
@Parcelize
data class MessageModel(
    val senderId: String = "",
    val receiverId: String = "",
    val message: String = "",
    val timestamp: Long = System.currentTimeMillis()
) : Parcelable
