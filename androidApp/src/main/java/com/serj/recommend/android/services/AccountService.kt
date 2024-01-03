package com.serj.recommend.android.services

import android.graphics.Bitmap
import com.serj.recommend.android.model.User
import kotlinx.coroutines.flow.Flow


interface AccountService {

    val currentUser: Flow<User>
    val currentUserId: String
    val hasUser: Boolean

    suspend fun signUp(email: String, password: String)

    suspend fun signIn(email: String, password: String)

    suspend fun sendPasswordResetEmail(email: String)

    suspend fun getUser(uid: String): User?

    suspend fun downloadImage(gsReference: String): Bitmap?
}