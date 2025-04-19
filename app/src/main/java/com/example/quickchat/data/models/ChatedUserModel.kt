package com.example.quickchat.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ChatedUserModel(
    val lastChat : MessageModel,
    val user : UserModel,
    val chatId : String
) : Parcelable