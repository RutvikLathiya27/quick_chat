package com.example.quickchat.ui.screens

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.quickchat.ui.theme.QuickChatTheme
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

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

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding),
                        this,
                        signInLauncher
                    )
                }
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

//    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
//        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
//        try {
//            val account = task.getResult(ApiException::class.java)
//            val credential = GoogleAuthProvider.getCredential(account.idToken, null)
//            FirebaseAuth.getInstance().signInWithCredential(credential)
//                .addOnCompleteListener { task ->
//                    if (task.isSuccessful) {
//                        FirebaseAuth.getInstance().currentUser
//                    } else {
//                        Log.e("GoogleSignIn", "Sign-in failed", task.exception)
//                    }
//                }
//        } catch (e: ApiException) {
//            Log.e("GoogleSignIn", "Sign-in failed", e)
//        }
//    }

    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken("218678111788-hmij8r6qbrjcmk70uo9i7gsroroa4nfo.apps.googleusercontent.com") // Replace with actual Web Client ID
        .requestEmail()
        .build()

    val googleSignInClient = GoogleSignIn.getClient(mainActivity!!, gso)

    val signInIntent = googleSignInClient.signInIntent
    signInLauncher?.launch(signInIntent)


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