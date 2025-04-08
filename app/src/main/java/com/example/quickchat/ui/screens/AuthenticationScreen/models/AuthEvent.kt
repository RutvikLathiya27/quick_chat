package com.example.quickchat.ui.screens.AuthenticationScreen.models

import androidx.compose.runtime.Stable

@Stable
sealed class AuthEvent {
    data class Login(val emailId : String, val password : String) : AuthEvent()
    data class GoogleLogin(val userId : String) : AuthEvent()
}