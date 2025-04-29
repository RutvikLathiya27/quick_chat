package com.example.quickchat.di

import com.example.quickchat.data.repository.repo.AuthRepository
import com.example.quickchat.data.repository.repo.ChatRepository
import com.example.quickchat.data.repository.repo.UserRepository
import com.example.quickchat.data.repository.repoimpl.AuthRepositoryImpl
import com.example.quickchat.data.repository.repoimpl.ChatRepositoryImpl
import com.example.quickchat.data.repository.repoimpl.UserRepositoryImpl
import com.example.quickchat.ui.screens.AuthenticationScreen.AuthViewModel
import com.example.quickchat.ui.screens.ChatScreen.ChatViewModel
import com.example.quickchat.ui.screens.HomeScreen.UserChatHistoryViewModel
import com.example.quickchat.ui.screens.SearchUserScreen.SearchScreenViewModel
import com.example.quickchat.ui.screens.SettingScreen.UserInfoViewModel
import com.example.quickchat.ui.screens.SplashScreen.SplashViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.firestore
import org.koin.dsl.module
import org.koin.core.module.dsl.viewModel
import org.koin.core.scope.get


val networkModule = module {

    val firestore = Firebase.firestore

    single<String> { Firebase.auth.currentUser?.uid ?: "" }

    single<AuthRepository> { AuthRepositoryImpl() }
    single<UserRepository> { UserRepositoryImpl(get(), firestore) }
    single<ChatRepository> { ChatRepositoryImpl(get(), firestore) }

    viewModel { AuthViewModel(get()) }
    viewModel { SplashViewModel(get()) }
    viewModel { SearchScreenViewModel(get()) }
    viewModel { ChatViewModel(get(), get(), get()) }
    viewModel { UserChatHistoryViewModel(get(), get()) }
    viewModel { UserInfoViewModel(get(), get()) }

}