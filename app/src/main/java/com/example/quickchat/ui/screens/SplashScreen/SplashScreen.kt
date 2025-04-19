package com.example.quickchat.ui.screens.SplashScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.quickchat.R
import com.example.quickchat.ui.screens.SplashScreen.models.SignInState
import com.example.quickchat.ui.theme.colorPrimary
import com.example.quickchat.ui.theme.colorWhite
import com.example.quickchat.ui.utlis.errorLog
import org.koin.androidx.compose.koinViewModel


@Composable
fun SplashScreen(
    onNavigationToHome: () -> Unit,
    onNavigationToAuthentication: () -> Unit,
    viewModel: SplashViewModel = koinViewModel(),
) {

    val status = viewModel.isSIgnInState.collectAsState()

    LaunchedEffect(status) {
        when (status.value) {
            is SignInState.Loading -> {
            }
            is SignInState.SignIn -> {
                onNavigationToHome()
            }
            is SignInState.SignOut -> onNavigationToAuthentication()
        }
    }

    Scaffold(
        containerColor = colorPrimary
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.app_name_splash),
                    color = colorWhite,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 24.sp
                )
            }
        }
    }
}