package com.example.quickchat.ui.screens.SplashScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.quickchat.ui.screens.SplashScreen.models.SignInState
import org.koin.androidx.compose.koinViewModel


@Composable
fun SplashScreen(
    onNavigationToHome :  () -> Unit,
    onNavigationToAuthentication :  () -> Unit,
    viewModel: SplashViewModel = koinViewModel(),

) {

    val status = viewModel.isSIgnInState.collectAsState().value
    when (status) {
        is SignInState.Loading -> {}
        is SignInState.SignIn -> onNavigationToHome()
        is SignInState.SignOut -> onNavigationToAuthentication()
    }


    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Spash")
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}