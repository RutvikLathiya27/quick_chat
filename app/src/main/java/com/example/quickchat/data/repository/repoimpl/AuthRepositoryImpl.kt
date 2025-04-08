package com.example.quickchat.data.repository.repoimpl

import com.example.quickchat.data.models.UserModel
import com.example.quickchat.data.repository.repo.AuthRepository
import com.example.quickchat.ui.screens.AuthenticationScreen.models.AuthState
import com.example.quickchat.ui.screens.SplashScreen.models.SignInState
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class AuthRepositoryImpl : AuthRepository {

    private val auth = FirebaseAuth.getInstance()

    override suspend fun isUserLogIn(): Flow<SignInState> = flow {
        emit(SignInState.Loading)
        val user = auth.currentUser
        if(user == null) {
            emit(SignInState.SignOut)
        } else {
            emit(SignInState.SignIn(user.uid))
        }
    }

    override suspend fun signInWithGoogle(idToken: String): Flow<AuthState?> = flow {
        emit(AuthState.Loading)
        try {
            val credential = GoogleAuthProvider.getCredential(idToken, null)
            val result = auth.signInWithCredential(credential).await()
            Result.success(result.user)
            emit(result.user?.uid?.let { AuthState.Success(it) })
            if(result.user == null){
                emit(AuthState.Error("User Id fail"))
            } else {
                emit(AuthState.Success(result.user!!.uid))
            }
        } catch (e: Exception) {
            emit(AuthState.Error(e.message ?: "Fail to sign in, Please try again"))
        }

    }

    override suspend fun createUserInFireStore(user: UserModel): Flow<AuthState> = flow {
        emit(AuthState.Loading)
        try {
            Firebase.firestore.collection("users")
                .document(user.uId)
                .set(user)
                .await()
            emit(AuthState.Success(user.uId))
        } catch (e : Exception) {
            emit(AuthState.Error(e.message ?: "Failed to create user in Firestore"))
        }




    }
}