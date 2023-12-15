package com.serj.recommend.android.model.service

import com.serj.recommend.android.model.User
import kotlinx.coroutines.flow.Flow


interface AccountService {

    suspend fun signIn(email: String, password: String)



    val currentUserId: String
    val hasUser: Boolean
    val currentUser: Flow<User>

    suspend fun sendRecoveryEmail(email: String)
    suspend fun createAnonymousAccount()
    suspend fun linkAccount(email: String, password: String)
    suspend fun deleteAccount()
    suspend fun signOut()
}