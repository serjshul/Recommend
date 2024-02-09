package com.serj.recommend.android.services

import com.serj.recommend.android.model.User
import kotlinx.coroutines.flow.Flow


interface AccountService {

    val currentUser: Flow<User>
    val currentUid: String
    val hasUser: Boolean

    suspend fun signUp(email: String, password: String)

    suspend fun signIn(email: String, password: String)

    suspend fun sendPasswordResetEmail(email: String)

    suspend fun linkAccount(email: String, password: String)
}