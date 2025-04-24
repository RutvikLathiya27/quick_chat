package com.example.quickchat.data.repository.repoimpl

import android.util.Log
import com.example.quickchat.data.models.ChatModel
import com.example.quickchat.data.models.ChatedUserModel
import com.example.quickchat.data.models.MessageModel
import com.example.quickchat.data.models.UserModel
import com.example.quickchat.data.repository.repo.ChatRepository
import com.example.quickchat.ui.screens.ChatScreen.models.AllMessageLoadState
import com.example.quickchat.ui.screens.ChatScreen.models.ChatUserState
import com.example.quickchat.ui.screens.ChatScreen.models.ChatedUserState
import com.example.quickchat.ui.utlis.COLLECTION_CHATS
import com.example.quickchat.ui.utlis.COLLECTION_MESSAGES
import com.example.quickchat.ui.utlis.COLLECTION_USERS
import com.example.quickchat.ui.utlis.INNER_COLLECTION_CHAT
import com.example.quickchat.ui.utlis.errorLog
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class ChatRepositoryImpl(
    private val userId: String,
    private val firestore: FirebaseFirestore
) : ChatRepository {

    override suspend fun getOrCreateChat(otherUserId: String): Flow<ChatUserState> = flow {

        emit(ChatUserState.Loading)

        val lstSortedIds = listOf(userId, otherUserId).sorted()
        errorLog("CREate >>>> $otherUserId")
        val chatId = "${lstSortedIds[0]}_${lstSortedIds[1]}"
        val docRef = firestore.collection(COLLECTION_CHATS).document(chatId)
        val snapshot = docRef.get().await()
        Log.e("TAG", "update >>>>>>>>>>>>>> ${snapshot.exists()}")
        if (snapshot.exists()) {
            emit(ChatUserState.SUCCESS(snapshot.toObject(ChatModel::class.java)!!))
        } else {
            val chat = ChatModel(
                chatId = chatId,
                users = lstSortedIds
            )
            docRef.set(chat).addOnFailureListener {
                Log.e("TAG", "update >>>>>>>>>>>>> Success")
            }.addOnSuccessListener {
                Log.e("TAG", "update >>>>>>>>>>>>> Fail")
            }.await()
            emit(ChatUserState.SUCCESS(chat))
        }
    }

    override fun listenForMessage(chatId: String): Flow<List<MessageModel>> {
        TODO("Not yet implemented")
    }

    override suspend fun sendMessage(senderId: String, messageModel: MessageModel) {
//        val lstUserIds = chatId.split("_")
//        val senderId = lstUserIds.toMutableList()
//        senderId.remove(userId)
        val lstSortedIds = listOf(userId, senderId).sorted()

        val message = messageModel.copy(
            senderId = userId,
            receiverId = senderId,
            timestamp = System.currentTimeMillis()
        )

        val messageDocRef = firestore.collection(COLLECTION_MESSAGES)
            .document("${lstSortedIds[0]}_${lstSortedIds[1]}")
            .collection(INNER_COLLECTION_CHAT)
            .document()

        messageDocRef.set(message)
            .await()

        firestore.collection(COLLECTION_CHATS)
            .document("${lstSortedIds[0]}_${lstSortedIds[1]}")
            .update("lastMessage", messageDocRef.id)
            .await()

    }

    override suspend fun getUsersChattedWithCurrentUser(): Flow<ChatedUserState> = flow {
        emit(ChatedUserState.Loading)
        try {
            val chatSnapShots = firestore.collection(COLLECTION_CHATS)
                .whereArrayContains("users", userId)
                .get()
                .await()

            val chatWithUsers = chatSnapShots.documents.mapNotNull { doc ->
                val chat = doc.toObject(ChatModel::class.java)

                errorLog("CHAT1 > $chat")

                chat?.let {

                    val message = firestore.collection(COLLECTION_MESSAGES)
                        .document(chat.chatId)
                        .collection(INNER_COLLECTION_CHAT)
                        .document(chat.lastMessage)
                        .get()
                        .await()

                    val messageModel: MessageModel? = message.toObject(MessageModel::class.java)

                    val otherUserId = it.users.firstOrNull { id -> id != userId }
                    if (otherUserId != null) {
                        val userSnapshot = firestore.collection(COLLECTION_USERS)
                            .document(otherUserId)
                            .get()
                            .await()

                        val user = userSnapshot.toObject(UserModel::class.java)
                        if (user != null && messageModel != null) {
                            ChatedUserModel(
                                lastChat = messageModel,
                                user = user,
                                chatId = chat.chatId
                            )
                        } else null
                    } else null
                }
            }

            emit(ChatedUserState.SUCCESS(chatWithUsers))

        } catch (e: Exception) {
            e.printStackTrace()
            emit(ChatedUserState.Error(e.message ?: "Fail"))
        }
    }

    override suspend fun getAllChatWithCurrentUser(chatId: String): Flow<AllMessageLoadState> =
        flow {
            emit(AllMessageLoadState.Loading)
            errorLog("Message load ")
            try {
                val lstMessages: ArrayList<MessageModel?> = arrayListOf()
                val messageSnapShot = firestore.collection(COLLECTION_MESSAGES)
                    .document(chatId)
                    .collection(INNER_COLLECTION_CHAT)
                    .orderBy("timestamp")
                    .get()
                    .await()

                val lstMessagesDocuments = messageSnapShot.documents.mapNotNull { doc ->
                    val message = doc.toObject(MessageModel::class.java)
                    lstMessages.add(message)
                }
                emit(AllMessageLoadState.SUCCESS(lstMessages))
                errorLog("Message succ ")
            } catch (e: Exception) {
                errorLog("Message fail ")
                emit(AllMessageLoadState.Error(e.message ?: "Fail to Load Chat"))
            }
        }

    override suspend fun listenForNewMessages(
        chatId: String,
        lastTimeStamp: Long
    ): Flow<List<MessageModel>> = callbackFlow {
        val listener = firestore.collection(COLLECTION_MESSAGES)
            .document(chatId)
            .collection(INNER_COLLECTION_CHAT)
            .whereGreaterThan("timestamp", lastTimeStamp)
            .orderBy("timestamp")
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                val newMessages = snapshot?.documents?.mapNotNull {
                    it.toObject(MessageModel::class.java)
                } ?: emptyList()
                trySend(newMessages)
            }
        awaitClose { listener.remove() }
    }


}