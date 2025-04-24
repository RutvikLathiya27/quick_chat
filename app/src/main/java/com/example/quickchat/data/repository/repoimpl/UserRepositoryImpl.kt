package com.example.quickchat.data.repository.repoimpl

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.quickchat.data.models.UserModel
import com.example.quickchat.data.paging.SearchUserPagingSource
import com.example.quickchat.data.repository.repo.UserRepository
import com.example.quickchat.ui.screens.SearchUserScreen.models.SearchUserState
import com.example.quickchat.ui.utlis.COLLECTION_USERS
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class UserRepositoryImpl(
    private val userId: String,
    private val firestore: FirebaseFirestore
) : UserRepository {

    override fun fetchAllUser(): Flow<PagingData<UserModel>> = Pager(
        config = PagingConfig(pageSize = 10),
        pagingSourceFactory = { SearchUserPagingSource(userId) }
    ).flow

    override suspend fun getSearchUser(query: String): Flow<SearchUserState> = flow {
        emit(SearchUserState.Loading)
        try {
            val snapshot = Tasks.await(
                firestore.collection("users")
                    .orderBy("name")
                    .startAt(query)
                    .endAt(query + "\uf8ff")
                    .get()
            )

            val filtered = snapshot.documents.mapNotNull {
                it.toObject(UserModel::class.java)
            }.filter { it.uid != userId }

            emit(SearchUserState.SearchSuccess(filtered))

        } catch (e: Exception) {
            emit(SearchUserState.SearchFail(e.message ?: "Something went wrong"))
        }
    }

    override suspend fun getReceiverUSer(receiverId: String): Flow<UserModel?> = flow {
        val snapshot = firestore.collection(COLLECTION_USERS)
            .document(
                receiverId
            ).get()
            .await()

        if(snapshot.exists()){
            emit(snapshot.toObject(UserModel::class.java))
        }else{
            emit(null)
        }

    }
}