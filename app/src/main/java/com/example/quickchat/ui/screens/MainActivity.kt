package com.example.quickchat.ui.screens

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.quickchat.R
import com.example.quickchat.ui.screens.AuthenticationScreen.AuthViewModel
import com.example.quickchat.ui.screens.AuthenticationScreen.models.AuthEvent
import com.example.quickchat.ui.theme.QuickChatTheme
import com.example.quickchat.ui.utlis.navigations.AppNavHost
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {

//    218678111788-hmij8r6qbrjcmk70uo9i7gsroroa4nfo.apps.googleusercontent.com



    val signInLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            QuickChatTheme {
                AppNavHost()
            }
        }
    }

}



@Composable
fun Greeting(
    name: String,
    modifier: Modifier = Modifier,
    mainActivity: MainActivity?,
    signInLauncher: ActivityResultLauncher<Intent>?
) {

    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    QuickChatTheme {
        Greeting("Android", mainActivity = null, signInLauncher = null)
    }
}