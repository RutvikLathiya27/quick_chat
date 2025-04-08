package com.example.quickchat.data.models

import com.google.gson.annotations.SerializedName

data class UserModel(
    @SerializedName(value = "UID")
    val uId : String,
    @SerializedName(value = "email")
    val email : String,
    @SerializedName(value = "name")
    val name : String,
    @SerializedName(value = "profilePic")
    val profile : String
)
