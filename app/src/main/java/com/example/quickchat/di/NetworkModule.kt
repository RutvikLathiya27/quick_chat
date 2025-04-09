package com.example.quickchat.di

import com.example.quickchat.data.repository.repo.AuthRepository
import com.example.quickchat.data.repository.repo.UserRepository
import com.example.quickchat.data.repository.repoimpl.AuthRepositoryImpl
import com.example.quickchat.data.repository.repoimpl.UserRepositoryImpl
import com.example.quickchat.ui.screens.AuthenticationScreen.AuthViewModel
import com.example.quickchat.ui.screens.SearchUserScreen.SearchScreenViewModel
import com.example.quickchat.ui.screens.SplashScreen.SplashViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import org.koin.dsl.module
import org.koin.core.module.dsl.viewModel


val networkModule = module {

    val userId = Firebase.auth.currentUser?.uid ?: ""
    val firestore = Firebase.firestore

    single<AuthRepository> { AuthRepositoryImpl() }
    single<UserRepository> { UserRepositoryImpl(userId, firestore) }

    viewModel { AuthViewModel(get()) }

    viewModel { SplashViewModel(get()) }

    viewModel { SearchScreenViewModel(get()) }



}