package com.example.quickchat.data.repository.repo

import com.example.quickchat.data.models.UserModel
import com.example.quickchat.ui.screens.AuthenticationScreen.models.AuthState
import com.example.quickchat.ui.screens.SplashScreen.models.SignInState
import com.google.android.gms.auth.api.Auth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun isUserLogIn() : Flow<SignInState>
    suspend fun signInWithGoogle(idToken : String) : Flow<AuthState?>
    suspend fun createUserInFireStore(user: UserModel): Flow<AuthState>
}