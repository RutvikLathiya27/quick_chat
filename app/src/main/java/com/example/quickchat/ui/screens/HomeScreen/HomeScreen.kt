package com.example.quickchat.ui.screens.HomeScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.FloatingActionButtonElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import com.example.quickchat.ui.theme.colorGreenShade2
import com.example.quickchat.ui.theme.colorGreenShade3
import com.example.quickchat.ui.theme.colorPrimary
import com.example.quickchat.ui.theme.colorWhite

@Composable
fun HomeScreen(
    onNavigationToSearch: () -> Unit
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

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValue),
            contentAlignment = Alignment.Center
        ) {
            Text("welcome to quick chat")
        }
    }

}