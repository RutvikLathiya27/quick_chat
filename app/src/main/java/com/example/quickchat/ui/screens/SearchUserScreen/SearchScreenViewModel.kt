package com.example.quickchat.ui.screens.SearchUserScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.quickchat.data.models.UserModel
import com.example.quickchat.data.repository.repo.UserRepository
import com.example.quickchat.ui.screens.SearchUserScreen.models.SearchUserState
import com.example.quickchat.ui.screens.SplashScreen.models.SignInState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch

class SearchScreenViewModel(private val userRepository: UserRepository) : ViewModel() {

//    val users: Flow<PagingData<UserModel>> = userRepository.fetchAllUser()
//            .cachedIn(viewModelScope)

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _userList = MutableStateFlow<SearchUserState>(SearchUserState.Loading)
    val userList= _userList.asStateFlow()


    fun onSearchQueryChanged(query: String) {
        searchUsers(query)
    }

    private fun searchUsers(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.getSearchUser(query).collect{ state ->
                Log.e("TAG", "search state :::::: $state")

                when(state){
                    SearchUserState.Loading -> {
                        _isLoading.value = true
                    }
                    is SearchUserState.SearchFail -> {
                        _userList.value = state
                        _isLoading.value = false
                    }
                    is SearchUserState.SearchSuccess -> {
                        _userList.value = state
                        _isLoading.value = false
                    }
                }

            }
        }
    }


}