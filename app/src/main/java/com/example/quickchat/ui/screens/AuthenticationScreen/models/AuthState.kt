package com.example.quickchat.ui.screens.AuthenticationScreen.models

import androidx.compose.runtime.Stable
import com.google.android.gms.auth.api.Auth

@Stable
sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    data class Success(val userId: String) : AuthState()
    data class Error(val msgError: String) :AuthState()
}