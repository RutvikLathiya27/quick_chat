package com.example.quickchat.ui.utlis.commonCompose

import android.util.Log
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import coil.compose.AsyncImage
import com.example.quickchat.R

@Composable
fun CircularImageFromUrl(imageUrl: String, size : Dp) {
    Log.e("TAG", "url >>>>>>>>>>>>> $imageUrl")
    AsyncImage(
        model = imageUrl,
        contentDescription = "Circular image",
        modifier = Modifier
            .size(size)
            .clip(CircleShape),
        contentScale = ContentScale.Crop,
        placeholder = painterResource(R.drawable.ic_launcher_foreground),
        error = painterResource(R.drawable.ic_launcher_background)
    )
}