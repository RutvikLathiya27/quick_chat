package com.example.quickchat.di

import com.example.quickchat.data.repository.repo.AuthRepository
import com.example.quickchat.data.repository.repoimpl.AuthRepositoryImpl
import com.example.quickchat.ui.screens.AuthenticationScreen.AuthViewModel
import com.example.quickchat.ui.screens.SplashScreen.SplashViewModel
import org.koin.dsl.module
import org.koin.androidx.viewmodel.dsl.viewModel


val networkModule = module {

    single<AuthRepository> { AuthRepositoryImpl() }

    viewModel { AuthViewModel(get()) }

    viewModel { SplashViewModel(get()) }

}