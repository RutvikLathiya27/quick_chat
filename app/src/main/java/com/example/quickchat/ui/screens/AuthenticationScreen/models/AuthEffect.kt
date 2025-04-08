package com.example.quickchat.ui.screens.AuthenticationScreen.models

sealed class AuthEffect {
    data class ShowToast(val message: String) : AuthEffect()
    object NavigateToHome : AuthEffect()
}
