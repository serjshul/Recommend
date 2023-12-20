package com.serj.recommend.android.model.service.impl

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import com.serj.recommend.android.R
import com.serj.recommend.android.model.User
import com.serj.recommend.android.model.service.AccountService
import com.serj.recommend.android.ui.components.snackbar.SnackbarManager
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AccountServiceImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : AccountService {

    override val currentUser: Flow<User>
        get() = callbackFlow {
            val currentUser = auth.currentUser?.let { getUserData(it.uid) }
            val listener = FirebaseAuth.AuthStateListener { auth ->
                this.trySend(auth.currentUser?.let {
                    currentUser
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
                if (!task.isSuccessful) {
                    SnackbarManager.showMessage(R.string.error_sign_in)
                }
            }.await()
    }

    override suspend fun signUp(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (!task.isSuccessful) {
                    SnackbarManager.showMessage(R.string.error_sign_up)
                }
            }.await()
    }

    override suspend fun sendPasswordResetEmail(email: String) {
        auth.sendPasswordResetEmail(email).await()
    }

    override suspend fun getUserData(uid: String): User? =
        firestore
            .collection(USERS_COLLECTION)
            .document(uid)
            .get()
            .await()
            .toObject()

    companion object {
        private const val USERS_COLLECTION = "users"
    }
}