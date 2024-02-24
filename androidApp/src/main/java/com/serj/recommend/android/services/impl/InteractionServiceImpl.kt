package com.serj.recommend.android.services.impl

import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import com.google.firebase.storage.FirebaseStorage
import com.serj.recommend.android.common.UserNotFoundException
import com.serj.recommend.android.model.items.UserItem
import com.serj.recommend.android.model.subcollections.Comment
import com.serj.recommend.android.model.subcollections.Like
import com.serj.recommend.android.model.subcollections.Repost
import com.serj.recommend.android.model.subcollections.UserContent
import com.serj.recommend.android.services.CommentResponse
import com.serj.recommend.android.services.InteractionService
import com.serj.recommend.android.services.LikeResponse
import com.serj.recommend.android.services.RemoveCommentResponse
import com.serj.recommend.android.services.RemoveLikeResponse
import com.serj.recommend.android.services.RemoveRepostResponse
import com.serj.recommend.android.services.RepostResponse
import com.serj.recommend.android.services.model.Response
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class InteractionServiceImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val storage: FirebaseStorage
) : InteractionService {

    override suspend fun like(
        like: Like
    ): LikeResponse {
        return try {
            val userLikeRef = firestore
                .collection(USERS_COLLECTION)
                .document(like.userId!!)
                .collection(LIKES_SUBCOLLECTION)
                .document(like.id!!)
            val recommendationLikeRef = firestore
                .collection(RECOMMENDATIONS_COLLECTION)
                .document(like.recommendationId!!)
                .collection(LIKES_SUBCOLLECTION)
                .document(like.id)
            var batchResult = false

            firestore.runBatch { batch ->
                batch.set(userLikeRef, like)
                batch.set(recommendationLikeRef, like)
            }.addOnSuccessListener {
                batchResult = true
                Log.d(TAG, "InteractionService: Like was added to firestore - ${like.id}")
            }.addOnFailureListener {
                batchResult = false
                Log.w(TAG, "InteractionService: Like wasn't added to firestore - $it")
            }.await()

            if (batchResult)
                Response.Success(like.id)
            else
                Response.Failure(Exception())
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }

    override suspend fun removeLike(
        userId: String,
        recommendationId: String,
        likeId: String
    ): RemoveLikeResponse {
        return try {
            val userLikeRef = firestore
                .collection(USERS_COLLECTION)
                .document(userId)
                .collection(LIKES_SUBCOLLECTION)
                .document(likeId)
            val recommendationLikeRef = firestore
                .collection(RECOMMENDATIONS_COLLECTION)
                .document(recommendationId)
                .collection(LIKES_SUBCOLLECTION)
                .document(likeId)
            var batchResult = false

            firestore.runBatch { batch ->
                batch.delete(userLikeRef)
                batch.delete(recommendationLikeRef)
            }.addOnSuccessListener {
                batchResult = true
                Log.d(TAG, "InteractionService: Like was removed from firestore - $likeId")
            }.addOnFailureListener {
                batchResult = false
                Log.w(TAG, "InteractionService: Like wasn't removed from firestore - $it")
            }.await()

            if (batchResult)
                Response.Success(true)
            else
                Response.Failure(Exception())
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }

    override suspend fun comment(
        comment: Comment
    ): CommentResponse {
        return try {
            val userCommentRef = firestore
                .collection(USERS_COLLECTION)
                .document(comment.userId!!)
                .collection(COMMENTS_SUBCOLLECTION)
                .document(comment.id!!)
            val recommendationCommentRef = firestore
                .collection(RECOMMENDATIONS_COLLECTION)
                .document(comment.recommendationId!!)
                .collection(COMMENTS_SUBCOLLECTION)
                .document(comment.id)
            var batchResult = false

            firestore.runBatch { batch ->
                batch.set(userCommentRef, comment)
                batch.set(recommendationCommentRef, comment)
            }.addOnSuccessListener {
                batchResult = true
                Log.d(TAG, "InteractionService: Comment was added to firestore - ${comment.id}")
            }.addOnFailureListener {
                batchResult = false
                Log.w(TAG, "InteractionService: Comment wasn't added to firestore - $it")
            }.await()

            if (batchResult) {
                val userItemResponse = getUserItemByUid(comment.userId)
                if (userItemResponse is Response.Success && userItemResponse.data != null) {
                    comment.userItem = userItemResponse.data
                    Response.Success(comment)
                } else
                    Response.Failure(Exception())
            } else
                Response.Failure(Exception())
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }

    override suspend fun removeComment(
        recommendationId: String,
        userId: String,
        commentId: String,
        commentOwnerId: String
    ): RemoveCommentResponse {
        return try {
            if (userId == commentOwnerId) {
                val userCommentRef = firestore
                    .collection(USERS_COLLECTION)
                    .document(userId)
                    .collection(COMMENTS_SUBCOLLECTION)
                    .document(commentId)
                val recommendationCommentRef = firestore
                    .collection(RECOMMENDATIONS_COLLECTION)
                    .document(recommendationId)
                    .collection(COMMENTS_SUBCOLLECTION)
                    .document(commentId)
                var batchResult = false

                firestore.runBatch { batch ->
                    batch.delete(userCommentRef)
                    batch.delete(recommendationCommentRef)
                }.addOnSuccessListener {
                    batchResult = true
                    Log.d(TAG, "InteractionService: Comment was removed from firestore - $commentId")
                }.addOnFailureListener {
                    batchResult = false
                    Log.w(TAG, "InteractionService: Comment wasn't removed from firestore - $it")
                }.await()

                if (batchResult)
                    Response.Success(true)
                else
                    Response.Failure(Exception())
            } else {
                Response.Failure(Exception())
            }
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }

    override suspend fun repost(
        repost: Repost
    ): RepostResponse {
        return try {
            var firstTransactionResult = false
            var secondTransactionResult = false
            var thirdTransactionResult = false

            var repostId: String? = null
            val userContent = UserContent(
                recommendationId = repost.recommendationId,
                userId = repost.userId,
                isReposted = true,
                date = repost.date,
                source = repost.source
            )

            firestore
                .collection(USERS_COLLECTION)
                .document(userContent.userId!!)
                .collection(CONTENT_SUBCOLLECTION)
                .add(userContent)
                .addOnCompleteListener {
                    firstTransactionResult = it.isSuccessful
                    if (it.isSuccessful) {
                        repostId = it.result.id
                        Log.d(TAG, "Repost added to User (${userContent.userId})")
                    } else
                        Log.d(TAG, "Repost wasn't added to User (${userContent.userId})")
                }
                .await()
            if (repostId != null) {
                firestore
                    .collection(USERS_COLLECTION)
                    .document(repost.userId!!)
                    .collection(REPOSTS_SUBCOLLECTION)
                    .document(repostId!!)
                    .set(repost)
                    .addOnCompleteListener {
                        secondTransactionResult = it.isSuccessful
                        if (it.isSuccessful)
                            Log.d(TAG, "Repost added to User (${repost.userId})")
                        else
                            Log.d(TAG, "Repost wasn't added to User (${repost.userId})")
                    }
                    .await()
                firestore
                    .collection(RECOMMENDATIONS_COLLECTION)
                    .document(repost.recommendationId!!)
                    .collection(REPOSTS_SUBCOLLECTION)
                    .document(repostId!!)
                    .set(repost)
                    .addOnCompleteListener {
                        thirdTransactionResult = it.isSuccessful
                        if (it.isSuccessful)
                            Log.d(
                                TAG,
                                "Repost added to Recommendation (${repost.recommendationId})"
                            )
                        else
                            Log.d(
                                TAG,
                                "Repost wasn't added to Recommendation (${repost.recommendationId})"
                            )
                    }
                    .await()
            }

            if (firstTransactionResult && secondTransactionResult && thirdTransactionResult)
                Response.Success(repostId)
            else
                Response.Failure(Exception())
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }

    override suspend fun removeRepost(
        userId: String,
        recommendationId: String,
        repostId: String
    ): RemoveRepostResponse {
        return try {
            var firstTransactionResult = false
            var secondTransactionResult = false
            var thirdTransactionResult = false

            firestore
                .collection(USERS_COLLECTION)
                .document(userId)
                .collection(CONTENT_SUBCOLLECTION)
                .document(repostId)
                .delete()
                .addOnCompleteListener {
                    firstTransactionResult = it.isSuccessful
                    if (it.isSuccessful)
                        Log.d(TAG, "Repost removed from User (${userId})")
                    else
                        Log.d(TAG, "Repost wasn't removed from User (${userId})")
                }
                .await()
            firestore
                .collection(USERS_COLLECTION)
                .document(userId)
                .collection(REPOSTS_SUBCOLLECTION)
                .document(repostId)
                .delete()
                .addOnCompleteListener {
                    secondTransactionResult = it.isSuccessful
                    if (it.isSuccessful)
                        Log.d(TAG, "Repost removed from User (${userId})")
                    else
                        Log.d(TAG, "Repost wasn't removed from User (${userId})")
                }
                .await()
            firestore
                .collection(RECOMMENDATIONS_COLLECTION)
                .document(recommendationId)
                .collection(REPOSTS_SUBCOLLECTION)
                .document(repostId)
                .delete()
                .addOnCompleteListener {
                    thirdTransactionResult = it.isSuccessful
                    if (it.isSuccessful)
                        Log.d(TAG, "Repost removed from Recommendation (${recommendationId})")
                    else
                        Log.d(TAG, "Repost wasn't removed from Recommendation (${recommendationId})")
                }
                .await()

            if (firstTransactionResult && secondTransactionResult && thirdTransactionResult)
                Response.Success(true)
            else
                Response.Failure(Exception())
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }

    private suspend fun getUserItemByUid(
        uid: String
    ): Response<UserItem> {
        return try {
            val recommendationPreviewSnapshot = firestore
                .collection(USERS_COLLECTION)
                .document(uid)
                .get()
                .await()
            val data = recommendationPreviewSnapshot.toObject<UserItem>()

            if (data != null) {
                data.photoReference = data.photoUrl?.let {
                    storage.getReferenceFromUrl(it)
                }
                Response.Success(data)
            } else {
                Response.Failure(UserNotFoundException())
            }
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }

    companion object {
        private const val USERS_COLLECTION = "users"
        private const val RECOMMENDATIONS_COLLECTION = "recommendations"

        private const val CONTENT_SUBCOLLECTION = "content"
        private const val LIKES_SUBCOLLECTION = "likes"
        private const val COMMENTS_SUBCOLLECTION = "comments"
        private const val REPOSTS_SUBCOLLECTION = "reposts"
    }
}