package com.example.quickchat.data.repository.repo

import androidx.paging.PagingData
import com.example.quickchat.data.models.UserModel
import com.example.quickchat.ui.screens.SearchUserScreen.models.SearchUserState
import com.example.quickchat.ui.screens.SplashScreen.models.SignInState
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun fetchAllUser() : Flow<PagingData<UserModel>>

    suspend fun getSearchUser(query : String) : Flow<SearchUserState>
}