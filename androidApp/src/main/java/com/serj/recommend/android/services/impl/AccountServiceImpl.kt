package com.serj.recommend.android.services.impl

import android.content.ContentValues
import android.util.Log
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import com.google.firebase.storage.FirebaseStorage
import com.serj.recommend.android.R
import com.serj.recommend.android.model.User
import com.serj.recommend.android.services.AccountService
import com.serj.recommend.android.services.model.trace
import com.serj.recommend.android.ui.components.snackbar.SnackbarManager
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AccountServiceImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
    private val storage: FirebaseStorage,
) : AccountService {

    override val currentUser: Flow<User>
        get() = callbackFlow {
            val currentUser = auth.currentUser?.let { getUser(it.uid) }
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

    override val currentUid: String
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

    override suspend fun linkAccount(email: String, password: String): Unit =
        trace(LINK_ACCOUNT_TRACE) {
            val credential = EmailAuthProvider
                .getCredential(email, password)

            auth
                .currentUser!!
                .linkWithCredential(credential)
                .await()
        }

    private suspend fun getUser(uid: String): User? {
        var currentUser: User? = null

        firestore
            .collection(USERS_COLLECTION)
            .document(uid)
            .get()
            .addOnSuccessListener {document ->
                val user = document.toObject<User>()
                if (user != null) {
                    currentUser = User(
                        uid = user.uid,
                        nickname = user.nickname,
                        name = user.name,
                        dateOfBirth = user.dateOfBirth,
                        photoUrl = user.photoUrl,
                        followers = user.followers,
                        following = user.following,
                        recommendationsIds = user.recommendationsIds,
                        likedIds = user.likedIds,
                        savedIds = user.savedIds
                    )
                }
            }.addOnFailureListener {
                // Handle any errors
                Log.v(ContentValues.TAG, "error getUserData()")
            }
            .await()

        currentUser?.photoReference = currentUser?.photoUrl?.let {
            storage.getReferenceFromUrl(it)
        }

        return currentUser
    }

    companion object {
        private const val LINK_ACCOUNT_TRACE = "linkAccount"

        private const val USERS_COLLECTION = "users"
    }
}