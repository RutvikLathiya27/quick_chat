package com.example.quickchat.ui.utlis.navigations

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.quickchat.ui.screens.AuthenticationScreen.AuthenticationScreen
import com.example.quickchat.ui.screens.HomeScreen.HomeScreen
import com.example.quickchat.ui.screens.SearchUserScreen.SearchUserScreen
import com.example.quickchat.ui.screens.SplashScreen.SplashScreen

@Composable
fun AppNavHost(
    navController : NavHostController = rememberNavController()
) {

    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        composable(Screen.Splash.route) {
            SplashScreen(
                onNavigationToHome = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                },
                onNavigationToAuthentication = {
                    navController.navigate(Screen.Authentication.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.Authentication.route){
            AuthenticationScreen(
                onNavigationToHome = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                },
            )
        }

        composable(Screen.Home.route){
            HomeScreen(
                onNavigationToSearch = {
                    navController.navigate(Screen.Search.route)
                },
            )
        }

        composable(Screen.Search.route){
            SearchUserScreen()
        }
    }

}