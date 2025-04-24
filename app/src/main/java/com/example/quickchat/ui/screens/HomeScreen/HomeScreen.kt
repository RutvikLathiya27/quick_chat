package com.example.quickchat.ui.screens.HomeScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quickchat.data.models.ChatedUserModel
import com.example.quickchat.ui.screens.ChatScreen.models.ChatedUserState
import com.example.quickchat.ui.theme.colorGreenShade1
import com.example.quickchat.ui.theme.colorGreenShade2
import com.example.quickchat.ui.theme.colorGreenShade3
import com.example.quickchat.ui.theme.colorLightGray
import com.example.quickchat.ui.theme.colorPrimary
import com.example.quickchat.ui.theme.colorWhite
import com.example.quickchat.ui.utlis.commonCompose.CircularImageFromUrl
import com.example.quickchat.ui.utlis.convertLongIntoTime
import com.example.quickchat.ui.utlis.errorLog
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    userChatHistoryViewModel: UserChatHistoryViewModel = koinViewModel(),
    onNavigationToSearch: () -> Unit,
    onNavigationToChat: (String) -> Unit,
    onNavigationToSetting : () -> Unit
) {

    Scaffold(
        containerColor = colorPrimary,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onNavigationToSearch()
                },
                shape = CircleShape,
                containerColor = colorGreenShade2,
                elevation = FloatingActionButtonDefaults.elevation(
                    defaultElevation = 8.dp
                )
            ) {
                Icon(
                    Icons.Default.Search, contentDescription = "Search Users",
                    tint = colorWhite
                )
            }
        }
    ) { paddingValue ->
        Column(
            modifier = Modifier
                .padding(paddingValue)
        ) {
            HomeHeader(onNavigationToSetting)
            SearchField()
            ChatedUser(userChatHistoryViewModel, paddingValue, onNavigationToChat)
        }
    }

}

@Composable
fun SearchField() {
    var searchText by remember { mutableStateOf("") }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = colorGreenShade1.copy(alpha = 0.4f)
            )
            .padding(horizontal = 10.dp, vertical = 5.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = colorLightGray,
                    shape = RoundedCornerShape(percent = 50)
                )
                .padding(horizontal = 10.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            TextField(
                value = searchText,
                onValueChange = {
                    searchText = it
                },
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text(text = "Search friends")
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
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search",
                        tint = colorWhite,
                        modifier = Modifier
                            .size(40.dp)
                            .fillMaxHeight(1f)
                            .background(
                                color = colorGreenShade2,
                                shape = CircleShape
                            )
                            .padding(10.dp)
                    )
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(
                    onSearch = { }
                )

            )
        }
    }
}

@Composable
fun HomeHeader(onNavigationToSetting: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = colorGreenShade1.copy(alpha = 0.4f))
            .padding(horizontal = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Quick Chat",
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium,
            color = colorWhite,
        )
        Spacer(
            modifier = Modifier.weight(1f)
        )
        IconButton(onClick = {
            onNavigationToSetting()
        }) {
            Icon(Icons.Default.Settings, contentDescription = "setting", tint = colorWhite)
        }
    }
}

@Composable
fun ChatedUser(
    userChatHistoryViewModel: UserChatHistoryViewModel,
    paddingValue: PaddingValues,
    onNavigationToChat: (String) -> Unit
) {
    val chatUserState by userChatHistoryViewModel.userChatHistory.collectAsState()
    val lstUser = mutableListOf<ChatedUserModel>()

    errorLog("CHAT >>>>>>>>>> $chatUserState, $lstUser")

    when (chatUserState) {
        is ChatedUserState.Error -> {}
        ChatedUserState.Loading -> {}
        is ChatedUserState.SUCCESS -> {
            (chatUserState as ChatedUserState.SUCCESS).lstUsersChat.let { lstUser.addAll(it) }
            ChatedUserList(userChatHistoryViewModel.currentUserId, lstUser, onNavigationToChat)
        }

        null -> {}
    }
}

@Composable
fun ChatedUserList(
    currentUser: String,
    lstUser: MutableList<ChatedUserModel> = mutableListOf<ChatedUserModel>(),
    onNavigationToChat: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        items(lstUser.size) { itemCount ->
            UserItem(currentUser, lstUser[itemCount], onNavigationToChat)
            if (itemCount != lstUser.size - 1) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(0.7.dp)
                        .background(
                            color = colorGreenShade3
                        )
                )
            }
        }
    }
}

@Composable
fun UserItem(currentUser: String, user: ChatedUserModel, onNavigationToChat: (String) -> Unit) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .clickable {
            errorLog("Navigate >>>>>>>>>>>>>>>>>> ")
            onNavigationToChat(if (currentUser == user.lastChat.receiverId) user.lastChat.senderId else user.lastChat.receiverId)
        }
        .padding(horizontal = 12.dp, vertical = 10.dp)) {
        CircularImageFromUrl(user.user.profile, 45.dp)
        Column(
            modifier = Modifier
                .padding(start = 12.dp)
                .align(Alignment.CenterVertically)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = user.user.name,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorWhite
                )
                Text(
                    text = convertLongIntoTime(user.lastChat.timestamp),
                    fontSize = 10.sp,
                    style = TextStyle(
                        color = colorGreenShade3.copy(alpha = 0.7f),
                    ),
                    fontWeight = FontWeight.Normal,
                )
            }
            Text(
                text = user.lastChat.message,
                fontSize = 16.sp,
                maxLines = 1,
                style = TextStyle(
                    color = colorWhite.copy(alpha = 0.7f),
                ),
                fontWeight = FontWeight.Medium,
            )
        }
    }
}
