package com.example.quickchat.ui.utlis.navigations

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.quickchat.data.models.UserModel
import com.example.quickchat.ui.screens.AuthenticationScreen.AuthenticationScreen
import com.example.quickchat.ui.screens.ChatScreen.ChatScreen
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
            SearchUserScreen(
                onNavigationToChat = { selectedUserId ->
                    navController.navigate(Screen.Chat.createRoute(selectedUserId)){
                        popUpTo(Screen.Search.route){inclusive = true}
                    }
                },
            )
        }

        composable(
            route = Screen.Chat.route,
            arguments = listOf(navArgument("userId") { type = NavType.StringType })
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId") ?: ""

            ChatScreen(
                user = userId,
            )
        }

    }

}

class UserModelNavType : NavType<UserModel>(isNullableAllowed = false) {
    override fun get(bundle: Bundle, key: String): UserModel? {
        return bundle.getParcelable(key)
    }
    override fun put(bundle: Bundle, key: String, value: UserModel) {
        bundle.putParcelable(key, value)
    }
    override fun parseValue(value: String): UserModel {
        throw UnsupportedOperationException("Parcelable can't be parsed from String!")
    }
}