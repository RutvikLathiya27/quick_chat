package com.example.quickchat.data.repository.repo

import com.example.quickchat.data.models.MessageModel
import com.example.quickchat.ui.screens.ChatScreen.models.AllMessageLoadState
import com.example.quickchat.ui.screens.ChatScreen.models.ChatUserState
import com.example.quickchat.ui.screens.ChatScreen.models.ChatedUserState
import kotlinx.coroutines.flow.Flow

interface ChatRepository {
    suspend fun getOrCreateChat(otherUserId: String): Flow<ChatUserState>
    fun listenForMessage(chatId: String): Flow<List<MessageModel>>
    suspend fun sendMessage(chatId: String, messageModel: MessageModel)
    suspend fun getUsersChattedWithCurrentUser(): Flow<ChatedUserState>
    suspend fun getAllChatWithCurrentUser(chatId: String): Flow<AllMessageLoadState>
    suspend fun listenForNewMessages(chatId :String, lastTimeStamp :Long) : Flow<List<MessageModel>>
}