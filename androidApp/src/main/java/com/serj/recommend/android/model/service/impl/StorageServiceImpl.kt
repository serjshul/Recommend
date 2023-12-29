package com.serj.recommend.android.model.service.impl

import android.content.ContentValues.TAG
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.dataObjects
import com.google.firebase.firestore.toObject
import com.google.firebase.firestore.toObjects
import com.google.firebase.storage.FirebaseStorage
import com.serj.recommend.android.model.Banner
import com.serj.recommend.android.model.Category
import com.serj.recommend.android.model.Recommendation
import com.serj.recommend.android.model.User
import com.serj.recommend.android.model.items.RecommendationItem
import com.serj.recommend.android.model.items.RecommendationPreview
import com.serj.recommend.android.model.items.UserItem
import com.serj.recommend.android.model.service.StorageService
import com.serj.recommend.android.ui.styles.BackgroundTypes
import com.serj.recommend.android.ui.styles.ItemsShapes
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class StorageServiceImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val storage: FirebaseStorage
) : StorageService {

    override val recommendations: Flow<List<Recommendation>>
        get() = firestore
            .collection(RECOMMENDATIONS_COLLECTION)
            .dataObjects()

    override val banners: Flow<List<Banner>>
        get() = firestore
            .collection(BANNERS_COLLECTION)
            .dataObjects()

    override val categories: Flow<List<Category>>
        get() = firestore
            .collection(CATEGORIES_COLLECTION)
            .dataObjects()

    override suspend fun getRecommendationById(
        recommendationId: String
    ): Recommendation? {
        var recommendation: Recommendation? = null
        var currentBackgroundImageReference: String? = null
        val currentParagraphsImagesReferences = hashMapOf<String, String?>()
        val currentParagraphsImages = hashMapOf<String, Bitmap?>()

        firestore
            .collection(RECOMMENDATIONS_COLLECTION)
            .document(recommendationId)
            .get()
            .addOnSuccessListener { document ->
                recommendation = document.toObject<Recommendation>()
                if (recommendation != null) {
                    if (recommendation!!.backgroundReferences[BackgroundTypes.image.name] != null) {
                        currentBackgroundImageReference = recommendation!!
                            .backgroundReferences[BackgroundTypes.image.name]
                    }
                    for (paragraph in recommendation!!.paragraphs) {
                        currentParagraphsImagesReferences[paragraph.getValue("title")] =
                            paragraph.getOrDefault(BackgroundTypes.image.name, null)
                    }
                }
            }
            .addOnFailureListener {
                Log.v(TAG, "error getCategoryItem()")
            }
            .await()

        recommendation?.backgroundImage = currentBackgroundImageReference?.let { downloadImage(it) }
        for ((title, reference) in currentParagraphsImagesReferences) {
            currentParagraphsImages[title] = reference?.let { downloadImage(it) }
        }
        recommendation?.paragraphsImages = currentParagraphsImages

        return recommendation
    }

    override suspend fun getBannerById(
        bannerId: String
    ): Banner? {
        var banner: Banner? = null

        firestore
            .collection(BANNERS_COLLECTION)
            .document(bannerId)
            .get()
            .addOnSuccessListener { document ->
                banner = document.toObject<Banner>()
            }
            .addOnFailureListener {
                Log.v(TAG, "error getCategoryItem()")
            }
            .await()

        return banner
    }

    override suspend fun getRecommendationItemById(
        recommendationId: String
    ): RecommendationItem? {
        var recommendationItem: RecommendationItem? = null
        var currentCoverType: String?
        var currentCoverReference: String? = null
        var currentBackgroundImageReference: String? = null

        firestore
            .collection(RECOMMENDATIONS_COLLECTION)
            .document(recommendationId)
            .get()
            .addOnSuccessListener {document ->
                val recommendation = document.toObject<Recommendation>()
                if (recommendation != null) {
                    when {
                        recommendation.coversReferences[ItemsShapes.horizontal.name] != null -> {
                            currentCoverType = ItemsShapes.horizontal.name
                            currentCoverReference =
                                recommendation.coversReferences[ItemsShapes.horizontal.name]
                        }
                        recommendation.coversReferences[ItemsShapes.vertical.name] != null -> {
                            currentCoverType = ItemsShapes.vertical.name
                            currentCoverReference =
                                recommendation.coversReferences[ItemsShapes.vertical.name]
                        }
                        recommendation.coversReferences[ItemsShapes.square.name] != null -> {
                            currentCoverType = ItemsShapes.square.name
                            currentCoverReference =
                                recommendation.coversReferences[ItemsShapes.square.name]
                        }
                        else -> {
                            currentCoverType = ItemsShapes.horizontal.name
                            currentCoverReference =
                                recommendation.coversReferences[ItemsShapes.horizontal.name]
                        }
                    }
                    if (recommendation.backgroundReferences[BackgroundTypes.image.name] != null) {
                        currentBackgroundImageReference = recommendation
                            .backgroundReferences[BackgroundTypes.image.name]
                    }
                    recommendationItem = RecommendationItem(
                        id = recommendation.id,
                        uid = recommendation.uid,
                        title = recommendation.title,
                        creator = recommendation.creator,
                        description = recommendation.description,
                        date = recommendation.date,
                        coverType = currentCoverType,
                        isLiked = false,
                        isReposted = false,
                    )
                }
            }
            .addOnFailureListener {
                // Handle any errors
                Log.v(TAG, "error getCategoryItem()")
            }
            .await()

        recommendationItem?.user = recommendationItem?.uid?.let { getUserItem(it) }
        recommendationItem?.cover = currentCoverReference?.let { downloadImage(it) }
        recommendationItem?.backgroundImage = currentBackgroundImageReference?.let {
            downloadImage(it)
        }

        return recommendationItem
    }

    override suspend fun getRecommendationPreviewById(
        recommendationId: String
    ): RecommendationPreview? {
        var recommendationPreview: RecommendationPreview? = null

        firestore
            .collection(RECOMMENDATIONS_COLLECTION)
            .document(recommendationId)
            .get()
            .addOnSuccessListener { document ->
                val recommendation = document.toObject<Recommendation>()
                if (recommendation != null) {
                    val coverType = when {
                        recommendation.coversReferences[ItemsShapes.horizontal.name] != null ->
                            ItemsShapes.horizontal.name
                        recommendation.coversReferences[ItemsShapes.square.name] != null ->
                            ItemsShapes.square.name
                        recommendation.coversReferences[ItemsShapes.vertical.name] != null ->
                            ItemsShapes.vertical.name
                        else -> ItemsShapes.horizontal.name
                    }
                    recommendationPreview = RecommendationPreview(
                        id = recommendation.id,
                        uid = recommendation.uid,
                        title = recommendation.title,
                        creator = recommendation.creator,
                        coverReference = recommendation.coversReferences[coverType],
                        coverType = coverType
                    )
                }
            }
            .addOnFailureListener {
                Log.v(TAG, "error getCategoryItem()")
            }
            .await()

        return recommendationPreview
    }

    override suspend fun getUserItem(
        uid: String
    ): UserItem? {
        var userItem: UserItem? = null
        var photoReference: String? = null

        firestore
            .collection(USERS_COLLECTION)
            .document(uid)
            .get()
            .addOnSuccessListener {document ->
                val user = document.toObject<User>()
                if (user != null) {
                    userItem = UserItem(
                        uid = user.uid,
                        nickname = user.nickname,
                    )
                    photoReference = user.photoReference
                }
            }
            .addOnFailureListener {
                // Handle any errors
                Log.v(TAG, "error getUserItem()")
            }
            .await()

        userItem?.photo = photoReference?.let { downloadImage(it) }

        return userItem
    }

    override suspend fun getFollowingRecommendationsIds(
        followingUid: String
    ): List<String> {
        val followingRecommendationsIds = arrayListOf<String>()

        firestore
            .collection(RECOMMENDATIONS_COLLECTION)
            .whereEqualTo("uid", followingUid)
            .get()
            .addOnSuccessListener {document ->
                val recommendations = document.toObjects<Recommendation>()
                for (recommendation in recommendations) {
                    recommendation.id?.let { followingRecommendationsIds.add(it) }
                }
            }
            .addOnFailureListener {
                // Handle any errors
                Log.v(TAG, "error getFollowingRecommendationsIds()")
            }
            .await()

        return followingRecommendationsIds
    }

    override suspend fun downloadImage(gsReference: String): Bitmap? {
        var bmp: Bitmap? = null

        storage
            .getReferenceFromUrl(gsReference)
            .getBytes(ONE_MEGABYTE)
            .addOnSuccessListener {
                bmp = BitmapFactory.decodeByteArray(it, 0, it.size)
            }
            .addOnFailureListener {
                Log.v(TAG, "not downloaded !!!")
            }
            .await()

        return bmp
    }

    companion object {
        private const val RECOMMENDATIONS_COLLECTION = "recommendations"
        private const val CATEGORIES_COLLECTION = "categories"
        private const val BANNERS_COLLECTION = "banners"
        private const val USERS_COLLECTION = "users"

        private const val ONE_MEGABYTE: Long = 1024 * 1024
    }
}