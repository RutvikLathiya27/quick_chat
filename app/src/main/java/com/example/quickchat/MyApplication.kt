package com.example.quickchat

import android.app.Application
import com.example.quickchat.di.networkModule
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import org.koin.core.context.startKoin

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        startKoin {
            modules(networkModule)
        }
    }

}