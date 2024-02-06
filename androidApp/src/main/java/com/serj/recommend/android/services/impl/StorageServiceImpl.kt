package com.serj.recommend.android.services.impl

import android.content.ContentValues.TAG
import android.content.Context
import android.net.Uri
import android.util.Log
import com.google.firebase.firestore.FieldValue
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
import com.serj.recommend.android.model.Banner
import com.serj.recommend.android.model.Category
import com.serj.recommend.android.model.Comment
import com.serj.recommend.android.model.Recommendation
import com.serj.recommend.android.model.items.RecommendationItem
import com.serj.recommend.android.model.items.RecommendationPreview
import com.serj.recommend.android.model.items.UserItem
import com.serj.recommend.android.services.DeleteCommentResponse
import com.serj.recommend.android.services.GetBannerResponse
import com.serj.recommend.android.services.GetCategoryResponse
import com.serj.recommend.android.services.GetCommentsResponse
import com.serj.recommend.android.services.GetFollowingRecommendationsIdsResponse
import com.serj.recommend.android.services.GetRecommendationItemResponse
import com.serj.recommend.android.services.GetRecommendationPreviewResponse
import com.serj.recommend.android.services.GetRecommendationResponse
import com.serj.recommend.android.services.GetStorageReferenceFromUrlResponse
import com.serj.recommend.android.services.GetUserItemResponse
import com.serj.recommend.android.services.LikeOrUnlikeRecommendationResponse
import com.serj.recommend.android.services.StorageService
import com.serj.recommend.android.services.UploadCommentResponse
import com.serj.recommend.android.services.UploadRecommendationResponse
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

    override suspend fun getComments(
        recommendationId: String
    ): GetCommentsResponse {
        return try {
            val commentsSnapshot = firestore
                .collection(RECOMMENDATIONS_COLLECTION)
                .document(recommendationId)
                .collection(COMMENTS_COLLECTION)
                .get()
                .await()
            val commentsData = commentsSnapshot.toObjects<Comment>()

            for (comment in commentsData) {
                val userItemResponse = comment.userId?.let { getUserItemByUid(it) }
                if (userItemResponse is Success && userItemResponse.data != null) {
                    comment.userItem = userItemResponse.data
                }
            }

            Success(commentsData)
        } catch (e: Exception) {
            Failure(e)
        }
    }

    override fun likeOrUnlikeRecommendation(
        isLiked: Boolean,
        uid: String,
        recommendationId: String
    ): LikeOrUnlikeRecommendationResponse {
        return try {
            if (!isLiked) {
                firestore
                    .collection(USERS_COLLECTION)
                    .document(uid)
                    .update(LIKED_IDS_FIELD, FieldValue.arrayUnion(recommendationId))
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful)
                            Log.d(TAG, "User DocumentSnapshot successfully updated!")
                        else
                            Log.w(TAG, "Error updating user document: $task")
                    }
                firestore
                    .collection(RECOMMENDATIONS_COLLECTION)
                    .document(recommendationId)
                    .update(LIKED_BY_FIELD, FieldValue.arrayUnion(uid))
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful)
                            Log.d(TAG, "Recommendation DocumentSnapshot successfully updated!")
                        else
                            Log.w(TAG, "Error updating recommendation document: $task")
                    }
            } else {
                firestore
                    .collection(USERS_COLLECTION)
                    .document(uid)
                    .update(LIKED_IDS_FIELD, FieldValue.arrayRemove(recommendationId))
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful)
                            Log.d(TAG, "User DocumentSnapshot successfully updated!")
                        else
                            Log.w(TAG, "Error updating user document: $task")
                    }
                firestore
                    .collection(RECOMMENDATIONS_COLLECTION)
                    .document(recommendationId)
                    .update(LIKED_BY_FIELD, FieldValue.arrayRemove(uid))
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful)
                            Log.d(TAG, "Recommendation DocumentSnapshot successfully updated!")
                        else
                            Log.w(TAG, "Error updating recommendation document: $task")
                    }
            }
            Success(true)
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
                    .whereEqualTo(USER_ID_FIELD, followingUid)
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

    override fun uploadComment(
        recommendationId: String,
        userId: String,
        text: String
    ): UploadCommentResponse {
        return try {
            val comment = hashMapOf(
                "userId" to userId,
                "repliedCommentId" to null,
                "text" to text,
                "date" to FieldValue.serverTimestamp(),
                "likedBy" to arrayListOf<String>()
            )
            firestore
                .collection(RECOMMENDATIONS_COLLECTION)
                .document(recommendationId)
                .collection(COMMENTS_COLLECTION)
                .add(comment)
                .addOnCompleteListener { task ->
                if (task.isSuccessful)
                    Log.d(TAG, "Recommendation DocumentSnapshot successfully updated!")
                else
                    Log.w(TAG, "Error updating recommendation document: $task")
                }
            Success(true)
        } catch (e: Exception) {
            Failure(e)
        }
    }

    override fun deleteComment(
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

    override suspend fun uploadRecommendation(
        recommendation: Recommendation
    ): UploadRecommendationResponse {
        return try {
            var result = ""

            firestore
                .collection(RECOMMENDATIONS_COLLECTION)
                .add(recommendation)
                .addOnCompleteListener { task ->
                    result = task.result.id
                    if (task.isSuccessful)
                        Log.d(TAG, "Recommendation successfully uploaded!")
                    else
                        Log.w(TAG, "Error uploading recommendation document: $task")
                }
                .await()

            Success(result)
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
        private const val RECOMMENDATIONS_COLLECTION = "recommendations"
        private const val CATEGORIES_COLLECTION = "categories"
        private const val BANNERS_COLLECTION = "banners"
        private const val USERS_COLLECTION = "users"

        private const val COMMENTS_COLLECTION = "comments"

        private const val USER_ID_FIELD = "uid"
        private const val DATE_FIELD = "date"
        private const val LIKED_IDS_FIELD = "likedIds"
        private const val LIKED_BY_FIELD = "likedBy"
    }
}