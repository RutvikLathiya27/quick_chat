package com.example.quickchat.ui.screens.SplashScreen.models

import androidx.compose.runtime.Stable

@Stable
sealed class SignInState {
    object Loading : SignInState()
    object SignOut : SignInState()
    data class SignIn(val userId: String) : SignInState()
}