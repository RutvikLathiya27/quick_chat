package com.example.quickchat.ui.screens.ChatScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quickchat.data.models.MessageModel
import com.example.quickchat.ui.screens.HomeScreen.UserChatHistoryViewModel
import com.example.quickchat.ui.theme.colorGreenShade2
import com.example.quickchat.ui.theme.colorGreenShade3
import com.example.quickchat.ui.theme.colorLightGray
import com.example.quickchat.ui.theme.colorPrimary
import com.example.quickchat.ui.theme.colorTransparent
import com.example.quickchat.ui.theme.colorWhite
import com.example.quickchat.ui.utlis.commonCompose.CircularImageFromUrl
import com.example.quickchat.ui.utlis.convertLongIntoTime
import org.koin.androidx.compose.koinViewModel

@Composable
fun ChatScreen(
    user: String,
    chatViewModel: ChatViewModel = koinViewModel()
) {

    LaunchedEffect(Unit) {
        chatViewModel.getReceiverData( user)
        chatViewModel.loadOrCreateChat(user)
    }

    Scaffold(
        containerColor = colorPrimary,
    ) { paddingValue ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValue)
        ) {
            //ChatHistory
            ReceiverInfo(chatViewModel)
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                ChatHistory(chatViewModel, user)
            }

            //Chat Box
            ChatSendBox(receiverID = user, chatViewModel)
        }
    }
}

@Composable
fun ReceiverInfo(chatViewModel: ChatViewModel) {
    val receiverInfo by chatViewModel.receiverUser.collectAsState()

    if (receiverInfo != null) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(colorLightGray.copy(alpha = 0.3f))
                .padding(vertical = 8.dp, horizontal = 8.dp)
        ) {
            CircularImageFromUrl(receiverInfo?.profile ?: "", 45.dp)
            Text(
                modifier = Modifier
                    .padding(horizontal = 6.dp)
                    .align(Alignment.CenterVertically),
                text = receiverInfo?.name ?: "Fail",
                color = colorWhite
            )
        }
    }
}

@Composable
fun ChatHistory(chatViewModel: ChatViewModel, user: String) {
    val lstMessage by chatViewModel.lstAllMessage.collectAsState()
    val reversedList = lstMessage.reversed()
    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        reverseLayout = true
    ) {
        items(reversedList) { item ->
            ChatBubble(
                message = item?.message ?: "",
                item?.timestamp ?: System.currentTimeMillis(),
                if (user == item?.senderId) false else true
            )
        }
    }
}

@Composable
fun ChatBubble(message: String, time: Long, isSender: Boolean) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp),
        horizontalArrangement = if (isSender) Arrangement.End else Arrangement.Start
    ) {
        Column(
            modifier = Modifier
                .width(LocalConfiguration.current.screenWidthDp.dp * 0.7f)
                .background(
                    color = if (isSender) colorGreenShade3.copy(alpha = 0.7f) else colorWhite.copy(
                        alpha = 0.8f
                    ),
                    shape = RoundedCornerShape(
                        topStart = 16.dp,
                        topEnd = 16.dp,
                        bottomEnd = if (isSender) 0.dp else 16.dp,
                        bottomStart = if (isSender) 16.dp else 0.dp
                    )
                )
                .padding(8.dp)
        ) {
            Text(
                text = message,
                color = Color.Black,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = convertLongIntoTime(time),
                modifier = Modifier.align(Alignment.End),
                fontSize = 10.sp,
                style = TextStyle(
                    color = colorLightGray.copy(alpha = 0.7f),
                ),
                fontWeight = FontWeight.Normal,
            )
        }
    }
}

@Composable
fun ChatSendBox(receiverID: String, chatViewModel: ChatViewModel) {
    var input by remember { mutableStateOf("") }
    Row(
        modifier = Modifier
            .padding(horizontal = 10.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        TextField(
            value = input,
            onValueChange = { input = it },
            modifier = Modifier
                .weight(1f),
            shape = RoundedCornerShape(40.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = colorLightGray.copy(alpha = 0.3f),
                unfocusedContainerColor = colorLightGray.copy(alpha = 0.3f),
                disabledContainerColor = colorLightGray.copy(alpha = 0.3f),
                focusedIndicatorColor = colorTransparent,
                unfocusedIndicatorColor = colorTransparent,
                focusedTextColor = colorWhite
            ),
            placeholder = { Text("Type a message") },
        )

        Box(
            modifier = Modifier
                .padding(8.dp)
                .background(colorGreenShade2, CircleShape),
        ) {
            IconButton(onClick = {
                if (input.isNotBlank()) {
                    chatViewModel.sendMessage(chatId = receiverID, message = input)
                    input = ""
                }
            }) {
                Icon(Icons.Default.Send, contentDescription = "Send", tint = colorWhite)
            }
        }
    }
}
