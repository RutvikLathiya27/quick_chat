package com.example.quickchat.ui.screens.SplashScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quickchat.data.repository.repo.AuthRepository
import com.example.quickchat.ui.screens.SplashScreen.models.SignInState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SplashViewModel(private val repository: AuthRepository) : ViewModel() {

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _isSignInstate = MutableStateFlow<SignInState>(SignInState.Loading)
    val isSIgnInState= _isSignInstate.asStateFlow()


    init {
        checkUserSignIn()
    }

    private fun checkUserSignIn(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.isUserLogIn().collect{
                when(it){
                    is SignInState.Loading -> {
                        _isLoading.value = true
                    }
                    is SignInState.SignIn -> {
                        _isLoading.value = false
                        _isSignInstate.value = it
                    }
                    is SignInState.SignOut -> {
                        _isLoading.value = false
                        _isSignInstate.value = it
                    }
                }
            }
        }
    }

}