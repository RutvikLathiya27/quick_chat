package com.example.quickchat.data.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.quickchat.data.models.UserModel
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class SearchUserPagingSource(
    private val excludeUid: String
) : PagingSource<String, UserModel>() {

    private var lastVisibleDocument: DocumentSnapshot? = null

    override fun getRefreshKey(state: PagingState<String, UserModel>): String? = null

    override suspend fun load(params: LoadParams<String>): LoadResult<String, UserModel> {
        return try {
            var query = Firebase.firestore.collection("users")
                .whereNotEqualTo("uid", excludeUid)
                .limit(params.loadSize.toLong())

            params.key?.let {
                query = query.startAfter(it)
            }

            val snapshot = query.get().await()
            val users = snapshot.documents.mapNotNull { it.toObject(UserModel::class.java) }

            Log.e("TAG", "user paging ::::::::::::::::: $users")

            lastVisibleDocument = snapshot.documents.lastOrNull()

            LoadResult.Page(
                data = users,
                prevKey = null,
                nextKey = lastVisibleDocument?.getString("uid")
            )
        } catch (e: Exception) {
            Log.e("TAG", "user paging ::::::::::::::::: ${e.message}")
            LoadResult.Error(e)
        }
    }


}