package com.example.quickchat.ui.screens.SearchUserScreen


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.quickchat.R
import com.example.quickchat.data.models.UserModel
import com.example.quickchat.ui.screens.SearchUserScreen.models.SearchUserState
import com.example.quickchat.ui.theme.colorGreenShade2
import com.example.quickchat.ui.theme.colorGreenShade3
import com.example.quickchat.ui.theme.colorLightGray
import com.example.quickchat.ui.theme.colorPrimary
import com.example.quickchat.ui.theme.colorWhite
import org.koin.androidx.compose.koinViewModel


@Composable
fun SearchUserScreen(
    viewModel: SearchScreenViewModel = koinViewModel(),
    onNavigationToChat: (String) -> Unit
) {

    val searchQuery by viewModel.searchQuery.collectAsState()
    val searchState by viewModel.userList.collectAsState()
//    val userPagingItems = viewModel.searchUsers.collectAsLazyPagingItems()

    Scaffold(
        containerColor = colorPrimary
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 8.dp)
            ) {
                searchField(viewModel = viewModel)
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .background(colorLightGray)
                    .align(Alignment.CenterHorizontally)
            ) {
                showSearchResult(searchState, onNavigationToChat)
            }

        }
    }
}

@Composable
fun showSearchResult(searchState: SearchUserState, onNavigationToChat: (String) -> Unit) {
    when (searchState) {
        SearchUserState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.Center)
                )
            }
        }

        is SearchUserState.SearchFail -> {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    "Fail to search",
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }

        is SearchUserState.SearchSuccess -> {
            createSearchUserList(
                searchUser = (searchState as SearchUserState.SearchSuccess).searchUser,
                onNavigationToChat
            )
        }
    }

}

@Composable
fun searchField(modifier: Modifier = Modifier, viewModel: SearchScreenViewModel) {
    var searchText by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = colorGreenShade3,
                shape = RoundedCornerShape(percent = 50)
            )
            .padding(horizontal = 8.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        TextField(
            value = searchText,
            onValueChange = {
                searchText = it
                viewModel.onSearchQueryChanged(searchText)
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
                onSearch = { viewModel.onSearchQueryChanged(searchText) }
            )

        )
    }

}

@Composable
fun createSearchUserList(searchUser: List<UserModel>, onNavigationToChat: (String) -> Unit) {

    val composition by rememberLottieComposition(
        LottieCompositionSpec.Asset("no_search_found.json")
    )

    if (searchUser.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize()) {
            LottieAnimation(
                composition = composition,
                iterations = LottieConstants.IterateForever,
                modifier = Modifier.size(150.dp)
                    .align(Alignment.Center)
            )
            Text("no user found", modifier = Modifier.align(Alignment.Center))
        }
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        ) {
            items(searchUser.size) { itemCount ->
                UserItem(searchUser[itemCount], onNavigationToChat)
            }
        }
    }
}

@Composable
fun UserItem(user: UserModel?, onNavigationToChat: (String) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable {
                onNavigationToChat(user?.name ?: "fail")
            },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = user?.name ?: "Unnamed User",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Text(text = user?.email ?: "", fontSize = 14.sp, color = Color.Gray)
        }
    }
}