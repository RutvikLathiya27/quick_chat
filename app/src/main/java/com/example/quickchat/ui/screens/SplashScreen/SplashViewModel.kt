package com.example.quickchat.ui.screens.SplashScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quickchat.data.repository.repo.AuthRepository
import com.example.quickchat.ui.screens.SplashScreen.models.SignInState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SplashViewModel(private val repository: AuthRepository) : ViewModel() {

    private val _isSignInstate = MutableStateFlow<SignInState>(SignInState.Loading)
    val isSIgnInState= _isSignInstate.asStateFlow()

    init {
        checkUserSignIn()
    }

    fun checkUserSignIn(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.isUserLogIn().collect{
                when(it){
                    is SignInState.Loading -> {
                        _isSignInstate.value = it
                    }
                    is SignInState.SignIn -> {
                        _isSignInstate.value = it
                    }
                    is SignInState.SignOut -> {
                        _isSignInstate.value = it
                    }
                }
            }
        }
    }

}