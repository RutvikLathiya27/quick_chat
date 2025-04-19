package com.example.quickchat.ui.utlis

import android.util.Log
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


fun errorLog(message: String){
    Log.e("CHATAPP", message)
}

fun convertLongIntoTime(timeInLong : Long) : String {
    val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
    return sdf.format(Date(timeInLong))
}