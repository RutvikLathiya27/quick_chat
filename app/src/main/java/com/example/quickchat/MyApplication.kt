package com.example.quickchat

import android.app.Application
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthOptions
import java.util.concurrent.TimeUnit

class MyApplication : Application() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate() {
        super.onCreate()
    }




}