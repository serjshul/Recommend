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
import com.serj.recommend.android.model.collections.User
import com.serj.recommend.android.model.items.RecommendationItem
import com.serj.recommend.android.model.items.RecommendationPreview
import com.serj.recommend.android.model.items.UserItem
import com.serj.recommend.android.model.subcollections.Comment
import com.serj.recommend.android.model.subcollections.Like
import com.serj.recommend.android.model.subcollections.Repost
import com.serj.recommend.android.services.GetBannerResponse
import com.serj.recommend.android.services.GetCategoryResponse
import com.serj.recommend.android.services.GetCommentsResponse
import com.serj.recommend.android.services.GetFollowingRecommendationsIdsResponse
import com.serj.recommend.android.services.GetRecommendationItemResponse
import com.serj.recommend.android.services.GetRecommendationPreviewResponse
import com.serj.recommend.android.services.GetRecommendationResponse
import com.serj.recommend.android.services.GetRepostsResponse
import com.serj.recommend.android.services.GetStorageReferenceFromUrlResponse
import com.serj.recommend.android.services.GetUserItemResponse
import com.serj.recommend.android.services.StorageService
import com.serj.recommend.android.services.UploadRecommendationResponse
import com.serj.recommend.android.services.UploadUserPhotoResponse
import com.serj.recommend.android.services.UploadUserResponse
import com.serj.recommend.android.services.model.Response
import com.serj.recommend.android.services.model.Response.Failure
import com.serj.recommend.android.services.model.Response.Success
import com.serj.recommend.android.services.uploadFollowersResponse
import com.serj.recommend.android.services.uploadFollowingResponse
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
                val likesResponse = getLikes(recommendationId)
                if (likesResponse is Success && likesResponse.data != null) {
                    data.likes.addAll(likesResponse.data)
                }
                val commentsResponse = getComments(recommendationId)
                if (commentsResponse is Success && commentsResponse.data != null) {
                    data.comments.addAll(commentsResponse.data)
                    if (data.comments.isNotEmpty()) {
                        data.topLikedComment = data.comments.maxBy { it.likedBy.size }
                    }
                }
                val repostsResponse = getReposts(recommendationId)
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

                val commentsResponse = getComments(recommendationId)
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

    override suspend fun getLikes(
        recommendationId: String
    ): Response<List<Like>> {
        return try {
            var transactionResult = false
            val likes: ArrayList<Like> = arrayListOf()

            firestore
                .collection(RECOMMENDATIONS_COLLECTION)
                .document(recommendationId)
                .collection(RECOMMENDATION_LIKES_SUBCOLLECTION)
                .get()
                .addOnSuccessListener {
                    val result = it.toObjects<Like>()
                    for (like in result) {
                        if (isLikeValid(like)) {
                            likes.add(like)
                        }
                    }
                    transactionResult = true
                }
                .addOnFailureListener { exception ->
                    transactionResult = false
                    Log.d(TAG, "Error getting documents: ", exception)
                }
                .await()

            if (transactionResult)
                Success(likes)
            else
                Failure(Exception())
        } catch (e: Exception) {
            Failure(e)
        }
    }

    override suspend fun getComments(
        recommendationId: String
    ): GetCommentsResponse {
        return try {
            var transactionResult = false
            val comments: ArrayList<Comment> = arrayListOf()

            firestore
                .collection(RECOMMENDATIONS_COLLECTION)
                .document(recommendationId)
                .collection(COMMENTS_COLLECTION)
                .get()
                .addOnSuccessListener {
                    val result = it.toObjects<Comment>()
                    for (comment in result) {
                        if (isCommentValid(comment)) {
                            comments.add(comment)
                        }
                    }
                    transactionResult = true
                }
                .addOnFailureListener { exception ->
                    transactionResult = false
                    Log.d(TAG, "Error getting documents: ", exception)
                }
                .await()

            if (transactionResult) {
                for (comment in comments) {
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

    override suspend fun getReposts(
        recommendationId: String
    ): GetRepostsResponse {
        return try {
            var transactionResult = false
            val reposts: ArrayList<Repost> = arrayListOf()

            firestore
                .collection(RECOMMENDATIONS_COLLECTION)
                .document(recommendationId)
                .collection(RECOMMENDATION_REPOSTS_SUBCOLLECTION)
                .get()
                .addOnSuccessListener {
                    val result = it.toObjects<Repost>()
                    for (repost in result) {
                        if (isRepostValid(repost)) {
                            reposts.add(repost)
                        }
                    }
                    transactionResult = true
                }
                .addOnFailureListener { exception ->
                    transactionResult = false
                    Log.d(TAG, "Error getting documents: ", exception)
                }
                .await()

            if (transactionResult)
                Success(reposts)
            else
                Failure(Exception())
        } catch (e: Exception) {
            Failure(e)
        }
    }

    override suspend fun uploadUser(
        user: User
    ): UploadUserResponse {
        return try {
            var transactionResult = false

            firestore
                .collection(USERS_COLLECTION)
                .document(user.uid!!)
                .set(user)
                .addOnCompleteListener {
                    transactionResult = it.isSuccessful
                    if (it.isSuccessful)
                        Log.d(TAG, "${user.uid}: Added user's info to \"$USERS_COLLECTION\" collection")
                    else
                        Log.w(TAG, "${user.uid}: Error while adding user's info: ${it.result}")
                }
                .await()

            if (transactionResult)
                Success(true)
            else
                Failure(Exception())
        } catch (e: Exception) {
            Failure(e)
        }
    }

    override suspend fun uploadUserPhoto(
        userId: String,
        uri: Uri,
        context: Context
    ): UploadUserPhotoResponse {
        return try {
            var photoUrl: String? = null
            var storageTransaction = false
            var userTransaction = false

            val storageRef = storage.reference
            val backgroundImageRef = storageRef
                .child("$USERS_COLLECTION/$userId/user_photo.jpg")

            val byteArray = context.contentResolver
                .openInputStream(uri)
                ?.use { it.readBytes() }

            byteArray?.let {
                backgroundImageRef
                    .putBytes(byteArray)
                    .addOnCompleteListener {
                        storageTransaction = it.isSuccessful
                        if (it.isSuccessful) {
                            Log.d(TAG, "$userId: User's photo was uploaded")
                            photoUrl = it.result.storage.toString()
                        } else
                            Log.w(TAG, "$userId: Error while uploading user's photo (${it.result})")
                    }
                    .await()
            }
            if (photoUrl != null) {
                firestore
                    .collection(USERS_COLLECTION)
                    .document(userId)
                    .update("photoUrl", photoUrl)
                    .addOnCompleteListener {
                        userTransaction = it.isSuccessful
                        if (it.isSuccessful)
                            Log.d(TAG, "$userId: User's document updated")
                        else
                            Log.w(TAG, "$userId: Error while updating user's document (${it.result})")
                    }
                    .await()
            }

            if (storageTransaction && userTransaction)
                Success(true)
            else
                Failure(Exception())
        } catch (e: Exception) {
            Failure(e)
        }
    }

    override suspend fun getFollowing(userId: String): uploadFollowingResponse {
        TODO("Not yet implemented")
    }

    override suspend fun getFollowers(userId: String): uploadFollowersResponse {
        TODO("Not yet implemented")
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

    private fun isLikeValid(like: Like) =
        like.userId != null && like.recommendationId != null && like.date != null && like.source != null

    private fun isCommentValid(comment: Comment) = comment.userId != null && comment.recommendationId != null &&
            comment.text != null && comment.date != null && comment.source != null

    private fun isRepostValid(repost: Repost) =
        repost.userId != null && repost.recommendationId != null && repost.date != null && repost.source != null


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
        private const val RECOMMENDATION_LIKES_SUBCOLLECTION = "likes"
        private const val RECOMMENDATION_REPOSTS_SUBCOLLECTION = "reposts"

        private const val UID_FIELD = "uid"
        private const val DATE_FIELD = "date"
    }
}