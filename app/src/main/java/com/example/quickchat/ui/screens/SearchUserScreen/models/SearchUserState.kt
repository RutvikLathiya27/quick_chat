package com.example.quickchat.ui.screens.SearchUserScreen.models

import androidx.compose.runtime.Stable
import com.example.quickchat.data.models.UserModel

@Stable
sealed class SearchUserState {
    object Loading : SearchUserState()
    data class SearchFail(val failMsg : String) : SearchUserState()
    data class SearchSuccess(val searchUser : List<UserModel>) : SearchUserState()
}