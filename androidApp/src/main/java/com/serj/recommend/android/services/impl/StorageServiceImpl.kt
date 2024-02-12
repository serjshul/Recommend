package com.serj.recommend.android.services.impl

import android.content.ContentValues.TAG
import android.content.Context
import android.net.Uri
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.dataObjects
import com.google.firebase.firestore.toObject
import com.google.firebase.firestore.toObjects
import com.google.firebase.storage.FirebaseStorage
import com.serj.recommend.android.common.BannerNotFoundException
import com.serj.recommend.android.common.CategoryNotFoundException
import com.serj.recommend.android.common.RecommendationNotFoundException
import com.serj.recommend.android.common.UserNotFoundException
import com.serj.recommend.android.model.collections.Banner
import com.serj.recommend.android.model.collections.Category
import com.serj.recommend.android.model.collections.Recommendation
import com.serj.recommend.android.model.items.RecommendationItem
import com.serj.recommend.android.model.items.RecommendationPreview
import com.serj.recommend.android.model.items.UserItem
import com.serj.recommend.android.model.subcollections.RecommendationComment
import com.serj.recommend.android.model.subcollections.RecommendationLike
import com.serj.recommend.android.model.subcollections.RecommendationRepost
import com.serj.recommend.android.services.DeleteCommentResponse
import com.serj.recommend.android.services.GetBannerResponse
import com.serj.recommend.android.services.GetCategoryResponse
import com.serj.recommend.android.services.GetFollowingRecommendationsIdsResponse
import com.serj.recommend.android.services.GetRecommendationCommentsResponse
import com.serj.recommend.android.services.GetRecommendationItemResponse
import com.serj.recommend.android.services.GetRecommendationPreviewResponse
import com.serj.recommend.android.services.GetRecommendationResponse
import com.serj.recommend.android.services.GetStorageReferenceFromUrlResponse
import com.serj.recommend.android.services.GetUserItemResponse
import com.serj.recommend.android.services.LikeRecommendationResponse
import com.serj.recommend.android.services.RemoveLikeRecommendationResponse
import com.serj.recommend.android.services.RemoveRepostRecommendationResponse
import com.serj.recommend.android.services.RepostRecommendationResponse
import com.serj.recommend.android.services.StorageService
import com.serj.recommend.android.services.UploadCommentResponse
import com.serj.recommend.android.services.UploadRecommendationResponse
import com.serj.recommend.android.services.model.Response
import com.serj.recommend.android.services.model.Response.Failure
import com.serj.recommend.android.services.model.Response.Success
import com.serj.recommend.android.ui.components.media.BackgroundTypes
import com.serj.recommend.android.ui.components.recommendationPreviews.ItemsShapes
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import java.util.Date
import javax.inject.Inject

class StorageServiceImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val storage: FirebaseStorage
) : StorageService {

    override val banners: Flow<List<Banner>>
        get() = firestore
            .collection(BANNERS_COLLECTION)
            .limit(50)
            .dataObjects()

    override val categories: Flow<List<Category>>
        get() = firestore
            .collection(CATEGORIES_COLLECTION)
            .orderBy(DATE_FIELD, Query.Direction.DESCENDING)
            .limit(50)
            .dataObjects()

    override suspend fun getRecommendationById(
        recommendationId: String
    ): GetRecommendationResponse {
        return try {
            val recommendationSnapshot = firestore
                .collection(RECOMMENDATIONS_COLLECTION)
                .document(recommendationId)
                .get()
                .await()
            val data = recommendationSnapshot.toObject<Recommendation>()

            if (data != null) {
                if (data.backgroundUrl[BackgroundTypes.image.name] != null) {
                    data.backgroundImageReference = data
                            .backgroundUrl[BackgroundTypes.image.name]
                            ?.let { storage.getReferenceFromUrl(it) }
                }
                for (paragraph in data.paragraphs) {
                    data.paragraphsReferences[paragraph.getValue("title")] = paragraph
                            .getOrDefault(BackgroundTypes.image.name, null)
                            ?.let { storage.getReferenceFromUrl(it) }
                }
                val likesResponse = getLikesFromRecommendation(recommendationId)
                if (likesResponse is Success && likesResponse.data != null) {
                    data.likes.addAll(likesResponse.data)
                }
                val commentsResponse = getCommentsFromRecommendation(recommendationId)
                if (commentsResponse is Success && commentsResponse.data != null) {
                    data.comments.addAll(commentsResponse.data)
                    if (data.comments.isNotEmpty()) {
                        data.topLikedComment = data.comments.maxBy { it.likedBy.size }
                    }
                }
                val repostsResponse = getRepostsFromRecommendation(recommendationId)
                if (repostsResponse is Success && repostsResponse.data != null) {
                    data.reposts.addAll(repostsResponse.data)
                }
                Success(data)
            } else {
                Failure(RecommendationNotFoundException())
            }
        } catch (e: Exception) {
            Failure(e)
        }
    }

    override suspend fun getBannerById(
        bannerId: String
    ): GetBannerResponse {
        return try {
            val bannerSnapshot = firestore
                .collection(BANNERS_COLLECTION)
                .document(bannerId)
                .get()
                .await()
            val data = bannerSnapshot.toObject<Banner>()

            if (data != null) {
                data.coverReference = data.coverUrl
                    ?.let { storage.getReferenceFromUrl(it) }
                if (data.backgroundUrl[BackgroundTypes.image.name] != null) {
                    data.backgroundImageReference = data
                        .backgroundUrl[BackgroundTypes.image.name]
                        ?.let { storage.getReferenceFromUrl(it) }
                }
                Success(data)
            } else {
                Failure(BannerNotFoundException())
            }
        } catch (e: Exception) {
            Failure(e)
        }
    }

    override suspend fun getCategoryById(
        categoryId: String
    ): GetCategoryResponse {
        return try {
            val categorySnapshot = firestore
                .collection(CATEGORIES_COLLECTION)
                .document(categoryId)
                .get()
                .await()
            val data = categorySnapshot.toObject<Category>()

            if (data != null) {
                if (data.backgroundUrl[BackgroundTypes.image.name] != null) {
                    data.backgroundImageReference = data
                        .backgroundUrl[BackgroundTypes.image.name]
                        ?.let { storage.getReferenceFromUrl(it) }
                }
                Success(data)
            } else {
                Failure(CategoryNotFoundException())
            }
        } catch (e: Exception) {
            Failure(e)
        }
    }

    override suspend fun getRecommendationItemById(
        recommendationId: String,
        currentUserLikedIds: ArrayList<String>
    ): GetRecommendationItemResponse {
        return try {
            val recommendationItemSnapshot = firestore
                .collection(RECOMMENDATIONS_COLLECTION)
                .document(recommendationId)
                .get()
                .await()
            val recommendationData = recommendationItemSnapshot.toObject<RecommendationItem>()

            if (recommendationData != null) {
                val coverType = getCoverType(recommendationData.coversUrl)
                if (recommendationData.uid != null) {
                    when (val userItemResponse = getUserItemByUid(recommendationData.uid)) {
                        is Success -> recommendationData.userItem = userItemResponse.data
                        else -> Failure(UserNotFoundException())
                    }
                }
                recommendationData.coverType = coverType
                recommendationData.coverReference = recommendationData.coversUrl[coverType]
                    ?.let { storage.getReferenceFromUrl(it) }
                recommendationData.backgroundImageReference = recommendationData.backgroundUrl[BackgroundTypes.image.name]
                    ?.let { storage.getReferenceFromUrl(it) }
                recommendationData.isLiked = currentUserLikedIds.contains(recommendationData.id)

                val commentsResponse = getCommentsFromRecommendation(recommendationId)
                if (commentsResponse is Success && commentsResponse.data != null) {
                    recommendationData.comments.addAll(commentsResponse.data)
                }

                Success(recommendationData)
            } else {
                Failure(RecommendationNotFoundException())
            }
        } catch (e: Exception) {
            Failure(e)
        }
    }

    override suspend fun getRecommendationPreviewById(
        recommendationId: String,
        coverType: String
    ): GetRecommendationPreviewResponse {
        return try {
            val recommendationPreviewSnapshot = firestore
                .collection(RECOMMENDATIONS_COLLECTION)
                .document(recommendationId)
                .get()
                .await()
            val data = recommendationPreviewSnapshot.toObject<RecommendationPreview>()

            if (data != null) {
                val availableCoverTypes = getAvailableCoverTypes(data.coversUrl)
                val currentCoverType = if (availableCoverTypes.contains(coverType)) coverType
                else getCoverType(data.coversUrl)
                data.coverType = currentCoverType
                data.coverReference = data.coversUrl[currentCoverType]
                    ?.let { storage.getReferenceFromUrl(it) }
                Success(data)
            } else {
                Failure(RecommendationNotFoundException())
            }
        } catch (e: Exception) {
            Failure(e)
        }
    }

    override suspend fun getUserItemByUid(
        uid: String
    ): GetUserItemResponse {
        return try {
            val recommendationPreviewSnapshot = firestore
                .collection(USERS_COLLECTION)
                .document(uid)
                .get()
                .await()
            val data = recommendationPreviewSnapshot.toObject<UserItem>()

            if (data != null) {
                data.photoReference = data.photoUrl
                    ?.let { storage.getReferenceFromUrl(it) }
                Success(data)
            } else {
                Failure(UserNotFoundException())
            }
        } catch (e: Exception) {
            Failure(e)
        }
    }

    override suspend fun getCommentsFromRecommendation(
        recommendationId: String
    ): GetRecommendationCommentsResponse {
        return try {
            var transactionResult = false
            var comments: List<RecommendationComment>? = null

            firestore
                .collection(RECOMMENDATIONS_COLLECTION)
                .document(recommendationId)
                .collection(COMMENTS_COLLECTION)
                .get()
                .addOnSuccessListener { result ->
                    comments = result.toObjects<RecommendationComment>()
                    transactionResult = true
                }
                .addOnFailureListener { exception ->
                    transactionResult = false
                    Log.d(TAG, "Error getting documents: ", exception)
                }
                .await()

            if (transactionResult && comments != null) {
                for (comment in comments!!) {
                    val userItemResponse = comment.userId?.let { getUserItemByUid(it) }
                    if (userItemResponse is Success && userItemResponse.data != null)
                        comment.userItem = userItemResponse.data
                    else
                        transactionResult = false
                }
                Success(comments)
            } else
                Failure(Exception())
        } catch (e: Exception) {
            Failure(e)
        }
    }

    override suspend fun like(
        userId: String,
        recommendationId: String,
        date: Date,
        source: String
    ): LikeRecommendationResponse {
        return try {
            var firstTransactionResult = false
            var secondTransactionResult = false

            val document = hashMapOf(
                DATE_FIELD to date,
                SOURCE_FIELD to source
            )

            firestore
                .collection(USERS_COLLECTION)
                .document(userId)
                .collection(USER_LIKES_SUBCOLLECTION)
                .document(recommendationId)
                .set(document)
                .addOnCompleteListener {
                    firstTransactionResult = it.isSuccessful
                    if (it.isSuccessful)
                        Log.d(TAG, "Like added to User (${userId})")
                    else
                        Log.d(TAG, "Like didn't add to User (${userId})")
                }
                .await()
            firestore
                .collection(RECOMMENDATIONS_COLLECTION)
                .document(recommendationId)
                .collection(RECOMMENDATION_LIKES_SUBCOLLECTION)
                .document(userId)
                .set(document)
                .addOnCompleteListener {
                    secondTransactionResult = it.isSuccessful
                    if (it.isSuccessful)
                        Log.d(TAG, "Like added to Recommendation (${recommendationId})")
                    else
                        Log.d(TAG, "Like didn't add to Recommendation (${recommendationId})")
                }
                .await()

            if (firstTransactionResult && secondTransactionResult)
                Success(true)
            else
                Failure(Exception())
        } catch (e: Exception) {
            Failure(e)
        }
    }

    override suspend fun removeLike(
        userId: String,
        recommendationId: String
    ): RemoveLikeRecommendationResponse {
        return try {
            var firstTransactionResult = false
            var secondTransactionResult = false

            firestore
                .collection(USERS_COLLECTION)
                .document(userId)
                .collection(USER_LIKES_SUBCOLLECTION)
                .document(recommendationId)
                .delete()
                .addOnCompleteListener {
                    firstTransactionResult = it.isSuccessful
                    if (it.isSuccessful)
                        Log.d(TAG, "Like removed from User (${userId})")
                    else
                        Log.d(TAG, "Like didn't removed from User (${userId})")
                }
                .await()
            firestore
                .collection(RECOMMENDATIONS_COLLECTION)
                .document(recommendationId)
                .collection(RECOMMENDATION_LIKES_SUBCOLLECTION)
                .document(userId)
                .delete()
                .addOnCompleteListener {
                    secondTransactionResult = it.isSuccessful
                    if (it.isSuccessful)
                        Log.d(TAG, "Like removed from Recommendation (${recommendationId})")
                    else
                        Log.d(TAG, "Like didn't removed from Recommendation (${recommendationId})")
                }
                .await()

            if (firstTransactionResult && secondTransactionResult)
                Success(true)
            else
                Failure(Exception())
        } catch (e: Exception) {
            Failure(e)
        }
    }

    override suspend fun repost(
        userId: String,
        recommendationId: String,
        date: Date,
        source: String
    ): RepostRecommendationResponse {
        return try {
            var firstTransactionResult = false
            var secondTransactionResult = false
            var thirdTransactionResult = false

            val contentDocument = hashMapOf(
                IS_REPOSTED_FIELD to true,
                DATE_FIELD to date
            )
            val repostDocument = hashMapOf(
                DATE_FIELD to date,
                SOURCE_FIELD to source
            )

            firestore
                .collection(USERS_COLLECTION)
                .document(userId)
                .collection(USER_CONTENT_SUBCOLLECTION)
                .document(recommendationId)
                .set(contentDocument)
                .addOnCompleteListener {
                    firstTransactionResult = it.isSuccessful
                    if (it.isSuccessful)
                        Log.d(TAG, "Repost added to User (${userId})")
                    else
                        Log.d(TAG, "Repost didn't add to User (${userId})")
                }
                .await()
            firestore
                .collection(USERS_COLLECTION)
                .document(userId)
                .collection(USER_REPOSTS_SUBCOLLECTION)
                .document(recommendationId)
                .set(repostDocument)
                .addOnCompleteListener {
                    secondTransactionResult = it.isSuccessful
                    if (it.isSuccessful)
                        Log.d(TAG, "Repost added to User (${userId})")
                    else
                        Log.d(TAG, "Repost didn't add to User (${userId})")
                }
                .await()
            firestore
                .collection(RECOMMENDATIONS_COLLECTION)
                .document(recommendationId)
                .collection(RECOMMENDATION_REPOSTS_SUBCOLLECTION)
                .document(userId)
                .set(repostDocument)
                .addOnCompleteListener {
                    thirdTransactionResult = it.isSuccessful
                    if (it.isSuccessful)
                        Log.d(TAG, "Repost added to Recommendation (${recommendationId})")
                    else
                        Log.d(TAG, "Repost didn't add to Recommendation (${recommendationId})")
                }
                .await()

            if (firstTransactionResult && secondTransactionResult && thirdTransactionResult)
                Success(true)
            else
                Failure(Exception())
        } catch (e: Exception) {
            Failure(e)
        }
    }

    override suspend fun removeRepost(
        userId: String,
        recommendationId: String
    ): RemoveRepostRecommendationResponse {
        return try {
            var firstTransactionResult = false
            var secondTransactionResult = false
            var thirdTransactionResult = false

            firestore
                .collection(USERS_COLLECTION)
                .document(userId)
                .collection(USER_CONTENT_SUBCOLLECTION)
                .document(recommendationId)
                .delete()
                .addOnCompleteListener {
                    firstTransactionResult = it.isSuccessful
                    if (it.isSuccessful)
                        Log.d(TAG, "Repost removed from User (${userId})")
                    else
                        Log.d(TAG, "Repost didn't removed from User (${userId})")
                }
                .await()
            firestore
                .collection(USERS_COLLECTION)
                .document(userId)
                .collection(USER_REPOSTS_SUBCOLLECTION)
                .document(recommendationId)
                .delete()
                .addOnCompleteListener {
                    secondTransactionResult = it.isSuccessful
                    if (it.isSuccessful)
                        Log.d(TAG, "Repost removed from User (${userId})")
                    else
                        Log.d(TAG, "Repost didn't removed from User (${userId})")
                }
                .await()
            firestore
                .collection(RECOMMENDATIONS_COLLECTION)
                .document(recommendationId)
                .collection(RECOMMENDATION_REPOSTS_SUBCOLLECTION)
                .document(userId)
                .delete()
                .addOnCompleteListener {
                    thirdTransactionResult = it.isSuccessful
                    if (it.isSuccessful)
                        Log.d(TAG, "Repost removed from Recommendation (${recommendationId})")
                    else
                        Log.d(TAG, "Repost didn't removed from Recommendation (${recommendationId})")
                }
                .await()

            if (firstTransactionResult && secondTransactionResult && thirdTransactionResult)
                Success(true)
            else
                Failure(Exception())
        } catch (e: Exception) {
            Failure(e)
        }
    }

    override fun comment(
        recommendationId: String,
        userId: String,
        text: String
    ): UploadCommentResponse {
        return try {
            var firstTransaction = false
            var secondTransaction = false

            var commentId: String? = null
            val commentForRecommendation = hashMapOf(
                USER_ID_FIELD to userId,
                REPLIED_COMMENT_ID_FIELD to null,
                TEXT_FIELD to text,
                DATE_FIELD to Date(),
                LIKED_BY_FIELD to arrayListOf<String>()
            )
            val commentForUser = hashMapOf(
                RECOMMENDATION_ID_FIELD to recommendationId,
                REPLIED_COMMENT_ID_FIELD to null,
                DATE_FIELD to Date(),
                LIKED_BY_FIELD to arrayListOf<String>(),
                SOURCE_FIELD to null
            )

            firestore
                .collection(RECOMMENDATIONS_COLLECTION)
                .document(recommendationId)
                .collection(COMMENTS_COLLECTION)
                .add(commentForRecommendation)
                .addOnCompleteListener { task ->
                    firstTransaction = task.isSuccessful
                    if (task.isSuccessful) {
                        commentId = task.result.id
                        Log.d(TAG, "Comment added to Recommendation (${recommendationId})")
                    } else
                        Log.d(TAG, "Comment didn't add to Recommendation (${recommendationId})")
                }
            if (commentId != null) {
                firestore
                    .collection(USERS_COLLECTION)
                    .document(userId)
                    .collection(USER_REPOSTS_SUBCOLLECTION)
                    .document(commentId!!)
                    .set(commentForUser)
                    .addOnCompleteListener { task ->
                        secondTransaction = task.isSuccessful
                        if (task.isSuccessful) {
                            Log.d(TAG, "Comment added to User (${userId})")
                        } else
                            Log.d(TAG, "Comment didn't add to User (${userId})")
                    }
            }

            if (firstTransaction && secondTransaction) {
                Success(true)
            } else {
                Failure(Exception())
            }
        } catch (e: Exception) {
            Failure(e)
        }
    }

    override fun removeComment(
        recommendationId: String,
        userId: String,
        commentId: String,
        commentOwnerId: String
    ): DeleteCommentResponse {
        return try {
            if (userId == commentOwnerId) {
                firestore
                    .collection(RECOMMENDATIONS_COLLECTION)
                    .document(recommendationId)
                    .collection(COMMENTS_COLLECTION)
                    .document(commentId)
                    .delete()
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful)
                            Log.d(TAG, "Recommendation DocumentSnapshot successfully updated!")
                        else
                            Log.w(TAG, "Error updating recommendation document: $task")
                    }
                Success(true)
            } else {
                Failure(Exception())
            }
        } catch (e: Exception) {
            Failure(e)
        }
    }

    private suspend fun getLikesFromRecommendation(
        recommendationId: String
    ): Response<List<RecommendationLike>> {
        return try {
            var transactionResult = false
            var likes: List<RecommendationLike>? = null

            firestore
                .collection(RECOMMENDATIONS_COLLECTION)
                .document(recommendationId)
                .collection(RECOMMENDATION_LIKES_SUBCOLLECTION)
                .get()
                .addOnSuccessListener { result ->
                    likes = result.toObjects<RecommendationLike>()
                    transactionResult = true
                }
                .addOnFailureListener { exception ->
                    transactionResult = false
                    Log.d(TAG, "Error getting documents: ", exception)
                }
                .await()

            if (transactionResult && likes != null)
                Success(likes)
            else
                Failure(Exception())
        } catch (e: Exception) {
            Failure(e)
        }
    }

    private suspend fun getRepostsFromRecommendation(
        recommendationId: String
    ): Response<List<RecommendationRepost>> {
        return try {
            var transactionResult = false
            var reposts: List<RecommendationRepost>? = null

            firestore
                .collection(RECOMMENDATIONS_COLLECTION)
                .document(recommendationId)
                .collection(RECOMMENDATION_REPOSTS_SUBCOLLECTION)
                .get()
                .addOnSuccessListener { result ->
                    reposts = result.toObjects<RecommendationRepost>()
                    transactionResult = true
                }
                .addOnFailureListener { exception ->
                    transactionResult = false
                    Log.d(TAG, "Error getting documents: ", exception)
                }
                .await()

            if (transactionResult && reposts != null)
                Success(reposts)
            else
                Failure(Exception())
        } catch (e: Exception) {
            Failure(e)
        }
    }

    override suspend fun getFollowingRecommendationsIds(
        followingUids: List<String>
    ): GetFollowingRecommendationsIdsResponse {
        return try {
            val followingRecommendationsIds = mutableListOf<Pair<String, Date>>()
            for (followingUid in followingUids) {
                val recommendationSnapshot = firestore
                    .collection(RECOMMENDATIONS_COLLECTION)
                    .whereEqualTo(UID_FIELD, followingUid)
                    .orderBy(DATE_FIELD, Query.Direction.DESCENDING)
                    .limit(10)
                    .get()
                    .await()
                for (item in recommendationSnapshot) {
                    followingRecommendationsIds.add(
                        Pair(
                            item.id,
                            item.getTimestamp(DATE_FIELD)!!.toDate()
                        )
                    )
                }
            }
            followingRecommendationsIds.sortByDescending { it.second }
            Success(followingRecommendationsIds.map { it.first })
        } catch (e: Exception) {
            Failure(e)
        }
    }

    override suspend fun uploadRecommendation(
        recommendation: Recommendation,
        currentUserId: String,
        isReposted: Boolean
    ): UploadRecommendationResponse {
        return try {
            var uploadedRecommendationId = ""

            val userContent = hashMapOf(
                "isReposted" to isReposted,
                "dateOfCreation" to recommendation.date
            )

            firestore
                .collection(RECOMMENDATIONS_COLLECTION)
                .add(recommendation)
                .addOnCompleteListener { task ->
                    uploadedRecommendationId = task.result.id
                    if (task.isSuccessful)
                        Log.d(TAG, "Recommendation (id = ${uploadedRecommendationId}) successfully uploaded!")
                    else
                        Log.w(TAG, "Error uploading recommendation (id = ${uploadedRecommendationId}) document: $task")
                }
                .await()
            if (uploadedRecommendationId != "") {
                firestore
                    .collection(USERS_COLLECTION)
                    .document(currentUserId)
                    .collection(USER_CONTENT_SUBCOLLECTION)
                    .document(uploadedRecommendationId)
                    .set(userContent)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful)
                            Log.d(
                                TAG,
                                "UserContent (uid = ${currentUserId}) successfully uploaded!"
                            )
                        else
                            Log.w(
                                TAG,
                                "Error uploading userContent (uid = ${currentUserId}) document: $task"
                            )
                    }
                    .await()
            }

            Success(uploadedRecommendationId)
        } catch (e: Exception) {
            Failure(e)
        }
    }

    override suspend fun uploadBackgroundImage(
        recommendationId: String,
        uri: Uri,
        context: Context
    ) {
        val backgroundUrl = hashMapOf<String, String>()

        val storageRef = storage.reference
        val backgroundImageRef = storageRef.child(
            "recommendations/${recommendationId}/background.jpg"
        )
        val byteArray = context.contentResolver
            .openInputStream(uri)
            ?.use { it.readBytes() }
        byteArray?.let {
            backgroundImageRef
                .putBytes(byteArray)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, "Well done")
                        backgroundUrl["image"] = task.result.storage.toString()
                    } else
                        Log.w(TAG, "Error in uploadImage")
                }
                .await()
        }

        if (backgroundUrl.isNotEmpty()) {
            firestore
                .collection(RECOMMENDATIONS_COLLECTION)
                .document(recommendationId)
                .update("backgroundUrl", backgroundUrl)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful)
                        Log.d(TAG, "Recommendation successfully updated!")
                    else
                        Log.w(TAG, "Error updating recommendation document: $task")
                }
                .await()
        }
    }

    override suspend fun uploadCoverImage(
        recommendationId: String,
        uri: Uri,
        coverType: String,
        context: Context
    ) {
        val coversUrl = hashMapOf<String, String>()

        val storageRef = storage.reference
        val backgroundImageRef = storageRef.child(
            "recommendations/${recommendationId}/cover_$coverType.jpg"
        )
        val byteArray = context.contentResolver
            .openInputStream(uri)
            ?.use { it.readBytes() }
        byteArray?.let {
            backgroundImageRef
                .putBytes(byteArray)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, "Well done")
                        coversUrl[coverType] = task.result.storage.toString()
                    } else
                        Log.w(TAG, "Error in uploadImage")
                }
                .await()
        }

        if (coversUrl.isNotEmpty()) {
            firestore
                .collection(RECOMMENDATIONS_COLLECTION)
                .document(recommendationId)
                .update("coversUrl", coversUrl)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful)
                        Log.d(TAG, "Recommendation successfully updated!")
                    else
                        Log.w(TAG, "Error updating recommendation document: $task")
                }
                .await()
        }
    }

    override fun getStorageReferenceFromUrl(
        url: String
    ): GetStorageReferenceFromUrlResponse {
        return try {
            val storageReference = storage.getReferenceFromUrl(url)
            Success(storageReference)
        } catch (e: Exception) {
            Failure(e)
        }
    }

    private fun getCoverType(
        coversUrl: HashMap<String, String>
    ): String {
        return when {
            coversUrl[ItemsShapes.square.name] != null ->
                ItemsShapes.square.name
            coversUrl[ItemsShapes.vertical.name] != null ->
                ItemsShapes.vertical.name
            coversUrl[ItemsShapes.horizontal.name] != null ->
                ItemsShapes.horizontal.name
            else -> ItemsShapes.horizontal.name
        }
    }

    private fun getAvailableCoverTypes(
        coversUrl: HashMap<String, String>
    ): List<String> = coversUrl.keys.toList()

    companion object {
        private const val USERS_COLLECTION = "users"
        private const val RECOMMENDATIONS_COLLECTION = "recommendations"
        private const val CATEGORIES_COLLECTION = "categories"
        private const val BANNERS_COLLECTION = "banners"
        private const val COMMENTS_COLLECTION = "comments"

        private const val USER_CONTENT_SUBCOLLECTION = "content"
        private const val USER_LIKES_SUBCOLLECTION = "likes"
        private const val USER_REPOSTS_SUBCOLLECTION = "reposts"

        private const val RECOMMENDATION_LIKES_SUBCOLLECTION = "likes"
        private const val RECOMMENDATION_REPOSTS_SUBCOLLECTION = "reposts"

        private const val UID_FIELD = "uid"

        private const val RECOMMENDATION_ID_FIELD = "recommendationId"
        private const val USER_ID_FIELD = "userId"
        private const val DATE_FIELD = "date"
        private const val SOURCE_FIELD = "source"
        private const val IS_REPOSTED_FIELD = "isReposted"
        private const val REPLIED_COMMENT_ID_FIELD = "repliedCommentId"
        private const val TEXT_FIELD = "text"
        private const val LIKED_BY_FIELD = "likedBy"
    }
}