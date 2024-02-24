package com.serj.recommend.android.services.impl

import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
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
import java.util.Date
import javax.inject.Inject

class InteractionServiceImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : InteractionService {

    override suspend fun like(
        like: Like
    ): LikeResponse {
        return try {
            val likeId = generateLikeId(like.recommendationId!!, like.userId!!)
            val userLikeRef = firestore
                .collection(USERS_COLLECTION)
                .document(like.userId)
                .collection(USER_LIKES_SUBCOLLECTION)
                .document(likeId)
            val recommendationLikeRef = firestore
                .collection(RECOMMENDATIONS_COLLECTION)
                .document(like.recommendationId)
                .collection(RECOMMENDATION_LIKES_SUBCOLLECTION)
                .document(likeId)
            var batchResult = false

            firestore.runBatch { batch ->
                batch.set(userLikeRef, like)
                batch.set(recommendationLikeRef, like)
            }.addOnSuccessListener {
                batchResult = true
                Log.d(TAG, "InteractionService: Like was added to firestore - $likeId")
            }.addOnFailureListener {
                batchResult = false
                Log.w(TAG, "InteractionService: Like wasn't added to firestore - $it")
            }.await()

            if (batchResult)
                Response.Success(likeId)
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
                .collection(USER_LIKES_SUBCOLLECTION)
                .document(likeId)
            val recommendationLikeRef = firestore
                .collection(RECOMMENDATIONS_COLLECTION)
                .document(recommendationId)
                .collection(RECOMMENDATION_LIKES_SUBCOLLECTION)
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
        userId: String,
        recommendationId: String,
        repliedCommentId: String?,
        repliedUserId: String?,
        text: String,
        isReplied: Boolean,
        date: Date,
        source: String
    ): CommentResponse {
        return try {
            var recommendationTransaction = false
            var userTransaction = false
            var commentId: String? = null

            val document = Comment(
                userId = userId,
                recommendationId = recommendationId,
                repliedCommentId = repliedCommentId,
                repliedUserId = repliedUserId,
                isReply = isReplied,
                text = text,
                date = date,
                likedBy = listOf(),
                source = source
            )

            firestore
                .collection(RECOMMENDATIONS_COLLECTION)
                .document(recommendationId)
                .collection(COMMENTS_COLLECTION)
                .add(document)
                .addOnCompleteListener { task ->
                    recommendationTransaction = task.isSuccessful
                    if (task.isSuccessful) {
                        commentId = task.result.id
                        Log.d(TAG, "Comment added to Recommendation (${recommendationId})")
                    } else
                        Log.d(TAG, "Comment wasn't add to Recommendation (${recommendationId})")
                }
                .await()
            if (commentId != null) {
                firestore
                    .collection(USERS_COLLECTION)
                    .document(userId)
                    .collection(USER_COMMENTS_SUBCOLLECTION)
                    .document(commentId!!)
                    .set(document)
                    .addOnCompleteListener { task ->
                        userTransaction = task.isSuccessful
                        if (task.isSuccessful) {
                            Log.d(TAG, "Comment added to User (${userId})")
                        } else
                            Log.d(TAG, "Comment wasn't add to User (${userId})")
                    }
                    .await()
            }

            if (recommendationTransaction && userTransaction) {
                Response.Success(true)
            } else {
                Response.Failure(Exception())
            }
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
                var recommendationTransaction = false
                var userTransaction = false

                firestore
                    .collection(RECOMMENDATIONS_COLLECTION)
                    .document(recommendationId)
                    .collection(COMMENTS_COLLECTION)
                    .document(commentId)
                    .delete()
                    .addOnCompleteListener { task ->
                        recommendationTransaction = task.isSuccessful
                        if (task.isSuccessful) {
                            Log.d(TAG, "Comment removed from Recommendation (${recommendationId})")
                        } else
                            Log.d(TAG, "Comment wasn't removed from Recommendation (${recommendationId})")
                    }
                    .await()
                firestore
                    .collection(USERS_COLLECTION)
                    .document(userId)
                    .collection(USER_COMMENTS_SUBCOLLECTION)
                    .document(commentId)
                    .delete()
                    .addOnCompleteListener { task ->
                        userTransaction = task.isSuccessful
                        if (task.isSuccessful)
                            Log.d(TAG, "Comment removed from User (${userId})")
                        else
                            Log.w(TAG, "Comment wasn't remove from User (${userId})")
                    }
                    .await()
                if (recommendationTransaction && userTransaction)
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
                .collection(USER_CONTENT_SUBCOLLECTION)
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
                    .collection(USER_REPOSTS_SUBCOLLECTION)
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
                    .collection(RECOMMENDATION_REPOSTS_SUBCOLLECTION)
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
                .collection(USER_CONTENT_SUBCOLLECTION)
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
                .collection(USER_REPOSTS_SUBCOLLECTION)
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
                .collection(RECOMMENDATION_REPOSTS_SUBCOLLECTION)
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

    private fun generateLikeId(
        recommendationId: String,
        userId: String
    ): String = (recommendationId + userId + "like").hashCode().toString()

    companion object {
        private const val USERS_COLLECTION = "users"
        private const val RECOMMENDATIONS_COLLECTION = "recommendations"
        private const val COMMENTS_COLLECTION = "comments"

        private const val USER_CONTENT_SUBCOLLECTION = "content"
        private const val USER_LIKES_SUBCOLLECTION = "likes"
        private const val USER_COMMENTS_SUBCOLLECTION = "comments"
        private const val USER_REPOSTS_SUBCOLLECTION = "reposts"

        private const val RECOMMENDATION_LIKES_SUBCOLLECTION = "likes"
        private const val RECOMMENDATION_REPOSTS_SUBCOLLECTION = "reposts"
    }
}