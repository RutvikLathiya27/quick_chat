package com.example.quickchat.ui.screens.SettingScreen

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.quickchat.data.models.UserModel
import com.example.quickchat.ui.theme.colorGreenShade3
import com.example.quickchat.ui.theme.colorLightGray
import com.example.quickchat.ui.theme.colorPrimary
import com.example.quickchat.ui.theme.colorTransparent
import com.example.quickchat.ui.theme.colorWhite
import com.example.quickchat.ui.utlis.commonCompose.CircularImageFromUrl
import com.google.firebase.auth.UserInfo
import org.koin.androidx.compose.koinViewModel

@Composable
fun SettingScreen(
    userViewModel: UserInfoViewModel = koinViewModel(),
) {

    Scaffold(
        containerColor = colorPrimary,
    ) { paddingValues ->
        SetToolbar(paddingValues)
        ShowUserInfo(paddingValues, userViewModel)
    }

}

@Composable
fun ShowUserInfo(paddingValues: PaddingValues, userViewModel: UserInfoViewModel) {

    val chatUserState by userViewModel.userInfo.collectAsState()
    var userName by remember { mutableStateOf("") }

    LaunchedEffect(chatUserState) {
        userViewModel.getUserProfileInfo()
        chatUserState?.let {
            userName = chatUserState?.name!!
        }
    }


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp)
            .padding(top = 140.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        chatUserState?.let {
            CircularImageFromUrl(
                it.profile, 100.dp
            )
        }

        Box(
            modifier = Modifier
                .height(20.dp)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = colorLightGray,
                    shape = RoundedCornerShape(percent = 15)
                ),
            contentAlignment = Alignment.CenterStart
        ) {
            TextField(
                value = userName,
                onValueChange = {
                    userName = it
                },
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text(text = "Enter User Name")
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    cursorColor = colorPrimary
                ),
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(
                    onSearch = { }
                ),
                textStyle = LocalTextStyle.current.copy(color = colorWhite)
            )
        }
        Box(
            modifier = Modifier
                .height(30.dp)
        )
        Button(
            modifier = Modifier
                .background(
                    color = colorGreenShade3,
                    shape = RoundedCornerShape(percent = 50)
                ), onClick = {},
            colors = ButtonDefaults.buttonColors(
                containerColor = colorTransparent,
                contentColor = colorLightGray
            )
        ) {
            Text(
                "Save",
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium
            )
        }

    }
}

@Composable
fun SetToolbar(paddingValues: PaddingValues) {
    Box(
        modifier = Modifier.padding(
            paddingValues
        )
    ) {
        IconButton(onClick = {

        }) {
            Icon(Icons.Default.ArrowBack, contentDescription = "back", tint = colorWhite)
        }
    }
}
