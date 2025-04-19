package com.example.quickchat.ui.screens.AuthenticationScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quickchat.data.models.UserModel
import com.example.quickchat.data.repository.repo.AuthRepository
import com.example.quickchat.ui.screens.AuthenticationScreen.models.AuthEffect
import com.example.quickchat.ui.screens.AuthenticationScreen.models.AuthEvent
import com.example.quickchat.ui.screens.AuthenticationScreen.models.AuthState
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AuthViewModel(private val repository: AuthRepository) : ViewModel() {

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _userData = MutableStateFlow<AuthState>(AuthState.Idle)
    val userData = _userData.asStateFlow()

    init {
        checkUser()
    }

    private fun checkUser() {
        val user = FirebaseAuth.getInstance().currentUser
        _isLoading.value = true
        if (user != null) {
            _isLoading.value = false
        } else {
            _isLoading.value = false
        }
    }


    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.d("RUTVIK", "error in AuthViewModel >>>>>>>>>>>>>>>>> " + throwable.message)
    }

    fun onEvent(event: AuthEvent) {
        Log.e("TAG", "on event call >>>>>>>>>>>>>> ")
        when (event) {
            is AuthEvent.Login -> TODO()
            is AuthEvent.GoogleLogin -> {
                viewModelScope.launch(Dispatchers.IO) {
                    val result = repository.signInWithGoogle(event.userId)
                    result.collect {
                        Log.e("TAG", "collect data >>>>>>>>>>>>>> $it")
                        when (it) {
                            is AuthState.Loading -> {
                                _isLoading.value = true
                            }
                            is AuthState.Error -> {
                                _isLoading.value = false
                                _userData.value = it
                            }
                            AuthState.Idle -> {}
                            is AuthState.Success -> {
                                _isLoading.value = false
                                _userData.value = it
                                createAuthUserOnDatabase(it.userId)
                            }
                            null -> TODO()
                        }
                    }
                }
            }
        }
    }

    private suspend fun createAuthUserOnDatabase(uId : String){
        val userData = UserModel(
            uid = uId,
            email = "",
            name = "",
            profile = ""
        )
        repository.createUserInFireStore(userData).collect { state ->
            _isLoading.value = false

            when (state) {
                is AuthState.Loading -> {
                    _isLoading.value = true
                }
                is AuthState.Error -> {
                    _isLoading.value = false
                    _userData.value = state
                    Log.e("RUTVIK", "fail to create user >>>>>>>>>>>>")

                }
                AuthState.Idle -> {}
                is AuthState.Success -> {
                    _isLoading.value = false
                    _userData.value = state
                    Log.e("RUTVIK", "create user >>>>>>>>>>>>")
                }
                null -> TODO()
            }
        }
    }


}