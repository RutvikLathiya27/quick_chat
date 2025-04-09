package com.example.quickchat.ui.screens.SearchUserScreen


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.quickchat.data.models.UserModel
import com.example.quickchat.ui.screens.SearchUserScreen.models.SearchUserState
import org.koin.androidx.compose.koinViewModel


@Composable
fun SearchUserScreen(
    viewModel: SearchScreenViewModel = koinViewModel()
) {

    val searchQuery by viewModel.searchQuery.collectAsState()
//    val userPagingItems = viewModel.searchUsers.collectAsLazyPagingItems()

    Column(modifier = Modifier.fillMaxSize()) {

        // Search Bar - not scrollable
        OutlinedTextField(
            value = searchQuery,
            onValueChange = viewModel::onSearchQueryChanged,
            label = { Text(text = "Search Users") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )

        when(searchQuery){
            SearchUserState.Loading -> {}
            is SearchUserState.SearchFail -> {}
            is SearchUserState.SearchSuccess -> {}
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        ) {
            items()
        }


        // User List
//        LazyColumn(
//            modifier = Modifier
//                .fillMaxSize()
//        ) {
//            items(userPagingItems.itemCount) { user ->
//                UserItem(user = userPagingItems[user])
//            }
//
//            userPagingItems.apply {
//                when {
//                    loadState.refresh is LoadState.Loading -> {
//                        item {
//                            CircularProgressIndicator(
//                                modifier = Modifier
//                                    .fillMaxWidth()
//                                    .padding(16.dp)
//                                    .wrapContentWidth(Alignment.CenterHorizontally)
//                            )
//                        }
//                    }
//
//                    loadState.append is LoadState.Loading -> {
//                        item {
//                            CircularProgressIndicator(
//                                modifier = Modifier
//                                    .fillMaxWidth()
//                                    .padding(16.dp)
//                                    .wrapContentWidth(Alignment.CenterHorizontally)
//                            )
//                        }
//                    }
//
//                    loadState.refresh is LoadState.Error -> {
//                        val e = loadState.refresh as LoadState.Error
//                        item {
//                            Text(
//                                text = "Error: ${e.error.localizedMessage}",
//                                color = Color.Red,
//                                modifier = Modifier.padding(16.dp)
//                            )
//                        }
//                    }
//                }
//            }
//        }
    }
}

@Composable
fun UserItem(user: UserModel?) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = user?.name ?: "Unnamed User", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Text(text = user?.email ?: "", fontSize = 14.sp, color = Color.Gray)
        }
    }
}