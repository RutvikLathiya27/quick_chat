package com.example.quickchat.ui.utlis.navigations

sealed class Screen(val route : String) {
    object Splash : Screen("splash")
    object Authentication : Screen("authentication")
    object Home : Screen("home")
    object Search : Screen("Search")
}