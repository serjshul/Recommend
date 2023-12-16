package com.serj.recommend.android.model.service.impl

import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.serj.recommend.android.R
import com.serj.recommend.android.common.snackbar.SnackbarManager
import com.serj.recommend.android.model.User
import com.serj.recommend.android.model.service.AccountService
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AccountServiceImpl @Inject constructor(
    private val auth: FirebaseAuth
) : AccountService {

    override val currentUser: Flow<User>
        get() = callbackFlow {
            val listener = FirebaseAuth.AuthStateListener { auth ->
                this.trySend(auth.currentUser?.let {
                    User(it.uid, it.isAnonymous)
                } ?: User())
            }
            auth.addAuthStateListener(listener)
            awaitClose {
                auth.removeAuthStateListener(listener)
            }
        }

    override val currentUserId: String
        get() = auth.currentUser?.uid.orEmpty()

    override val hasUser: Boolean
        get() = auth.currentUser != null

    override suspend fun signIn(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    SnackbarManager.showMessage(R.string.success_sign_in)
                } else {
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    SnackbarManager.showMessage(R.string.error_sign_in)
                }
            }.await()
    }

    override suspend fun signUp(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    SnackbarManager.showMessage(R.string.success_sign_up)
                } else {
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    SnackbarManager.showMessage(R.string.error_sign_up)
                }
            }.await()
    }

    override suspend fun sendPasswordResetEmail(email: String) {
        auth.sendPasswordResetEmail(email).await()
    }
}