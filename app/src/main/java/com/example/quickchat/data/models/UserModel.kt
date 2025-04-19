package com.example.quickchat.data.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserModel(
    @SerializedName(value = "uid")
    val uid : String = "",
    @SerializedName(value = "email")
    val email : String = "",
    @SerializedName(value = "name")
    val name : String = "",
    @SerializedName(value = "profile")
    val profile : String = ""
) : Parcelable
