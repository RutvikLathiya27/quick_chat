package com.example.quickchat.ui.utlis.navigations

import com.example.quickchat.data.models.UserModel

sealed class Screen(val route : String) {
    object Splash : Screen("splash")
    object Authentication : Screen("authentication")
    object Home : Screen("home")
    object Search : Screen("Search")
    object Chat : Screen("chat/{userId}"){
        fun createRoute(userId : String) = "chat/$userId"
    }
    object Setting : Screen("setting")
}