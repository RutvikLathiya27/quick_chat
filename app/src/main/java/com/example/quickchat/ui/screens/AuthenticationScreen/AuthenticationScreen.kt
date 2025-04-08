package com.example.quickchat.ui.screens.AuthenticationScreen

import android.app.Activity
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.quickchat.R
import com.example.quickchat.ui.screens.AuthenticationScreen.models.AuthEvent
import com.example.quickchat.ui.screens.AuthenticationScreen.models.AuthState
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import org.koin.androidx.compose.koinViewModel

@Composable
fun AuthenticationScreen(
    onNavigationToHome : () -> Unit,
    viewModel: AuthViewModel = koinViewModel()
) {

    val userLoginStatusData = viewModel.userData.collectAsState()
    val context = LocalContext.current
    val activity = context as Activity

    when(userLoginStatusData.value){
        is AuthState.Loading -> {}
        is AuthState.Error -> {
            Log.e("RUTVIK", "login fail")
        }
        AuthState.Idle -> {}
        is AuthState.Success -> {
            Log.e("RUTVIK", "login Done")
            onNavigationToHome()
        }
    }

    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)

            try {
                val account = task.getResult(ApiException::class.java)
                val idToken = account.idToken
                if (idToken != null) {
                    viewModel.onEvent(AuthEvent.GoogleLogin(idToken))
                }
                Log.e("TAG", "Login call try >>>>>>>>>>>>>>>>>")
            } catch (e: ApiException) {
                Log.e("TAG", "Login call try catch >>>>>>>>>>>>>>>>> ${e.message}")
                Toast.makeText(context, "Google Sign-In Failed", Toast.LENGTH_SHORT).show()
            }
        }

    val googleClient = remember {
        GoogleSignIn.getClient(
            context,
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(activity.getString(R.string.web_client_id))
                .requestEmail()
                .build()
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "AuthScreen")
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                launcher.launch(googleClient.signInIntent)
            }) {
                Text(text = "Get Started")
            }
        }
    }
}