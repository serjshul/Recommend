package com.serj.recommend.android.services.impl

import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Source
import com.google.firebase.firestore.dataObjects
import com.google.firebase.firestore.toObject
import com.google.firebase.firestore.toObjects
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.serj.recommend.android.model.Banner
import com.serj.recommend.android.model.Category
import com.serj.recommend.android.model.Recommendation
import com.serj.recommend.android.model.User
import com.serj.recommend.android.model.items.RecommendationItem
import com.serj.recommend.android.model.items.RecommendationPreviewItem
import com.serj.recommend.android.model.items.UserItem
import com.serj.recommend.android.services.StorageService
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

        firestore
            .collection(RECOMMENDATIONS_COLLECTION)
            .document(recommendationId)
            .get()
            .addOnSuccessListener { document ->
                recommendation = document.toObject<Recommendation>()
                if (recommendation != null) {
                    if (recommendation!!.backgroundUrl[BackgroundTypes.image.name] != null) {
                        recommendation!!.backgroundImageReference =
                            recommendation!!
                                .backgroundUrl[BackgroundTypes.image.name]
                                ?.let { storage.getReferenceFromUrl(it) }
                    }
                    /*
                    if (recommendation!!.backgroundUrl[BackgroundTypes.video.name] != null) {
                        recommendation!!.backgroundVideoReference =
                            recommendation!!
                                .backgroundUrl[BackgroundTypes.video.name]
                                ?.let { storage.getReference(it) }
                    }

                     */
                    for (paragraph in recommendation!!.paragraphs) {
                        recommendation!!.paragraphsReferences[paragraph.getValue("title")] =
                            paragraph
                                .getOrDefault(BackgroundTypes.image.name, null)
                                ?.let { storage.getReferenceFromUrl(it) }
                    }
                }
            }
            .addOnFailureListener {
                Log.v(TAG, "error getRecommendationById()")
            }
            .await()

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
                if (banner != null) {
                    banner!!.coverReference = banner!!.coverUrl
                        ?.let { storage.getReferenceFromUrl(it) }
                    if (banner!!.backgroundUrl[BackgroundTypes.image.name] != null) {
                        banner!!.backgroundImageReference = banner!!
                            .backgroundUrl[BackgroundTypes.image.name]
                            ?.let { storage.getReferenceFromUrl(it) }
                    }
                    /*
                    if (banner!!.backgroundUrl[BackgroundTypes.video.name] != null) {
                        banner!!.backgroundVideoReference = banner!!
                            .backgroundUrl[BackgroundTypes.video.name]
                            ?.let { storage.getReference(it) }
                    }

                     */
                }
            }
            .addOnFailureListener {
                Log.v(TAG, "error getBannerById()")
            }
            .await()

        return banner
    }

    override suspend fun getCategoryById(
        categoryId: String
    ): Category? {
        var category: Category? = null

        firestore
            .collection(CATEGORIES_COLLECTION)
            .document(categoryId)
            .get()
            .addOnSuccessListener { document ->
                category = document.toObject<Category>()
                if (category != null) {
                    if (category!!.backgroundUrl[BackgroundTypes.image.name] != null) {
                        category!!.backgroundImageReference = category!!
                            .backgroundUrl[BackgroundTypes.image.name]
                            ?.let { storage.getReferenceFromUrl(it) }
                    }
                    /*
                    if (category!!.backgroundUrl[BackgroundTypes.video.name] != null) {
                        category!!.backgroundVideoReference = category!!
                            .backgroundUrl[BackgroundTypes.video.name]
                            ?.let { storage.getReferenceFromUrl(it) }
                    }

                     */
                }
            }
            .addOnFailureListener {
                Log.v(TAG, "error getCategoryById()")
            }
            .await()

        return category
    }

    override suspend fun getRecommendationItemById(
        recommendationId: String
    ): RecommendationItem? {
        var recommendationItem: RecommendationItem? = null
        var currentUserUid: String? = null

        firestore
            .collection(RECOMMENDATIONS_COLLECTION)
            .document(recommendationId)
            .get()
            .addOnSuccessListener {document ->
                val recommendation = document.toObject<Recommendation>()
                if (recommendation != null) {
                    val currentCoverType = getCoverType(recommendation.coversUrl)
                    currentUserUid = recommendation.uid

                    recommendationItem = RecommendationItem(
                        id = recommendation.id,
                        uid = currentUserUid,
                        title = recommendation.title,
                        creator = recommendation.creator,
                        description = recommendation.description,
                        date = recommendation.date,
                        coverType = currentCoverType,
                        coverReference = recommendation
                            .coversUrl[currentCoverType]
                            ?.let { storage.getReferenceFromUrl(it) },
                        backgroundImageReference = recommendation
                            .backgroundUrl[BackgroundTypes.image.name]
                            ?.let { storage.getReferenceFromUrl(it) },
                        backgroundVideoReference = null, /*recommendation
                            .backgroundUrl[BackgroundTypes.video.name]
                            ?.let { storage.getReference(it) }, */
                        isLiked = false,
                        isReposted = false,
                    )
                }
            }
            .addOnFailureListener {
                Log.v(TAG, "error getRecommendationItemById()")
            }
            .await()

        recommendationItem?.user = currentUserUid?.let { getUserItemByUid(it) }

        return recommendationItem
    }

    override suspend fun getRecommendationPreviewById(
        recommendationId: String
    ): RecommendationPreviewItem? {
        var recommendationPreview: RecommendationPreviewItem? = null

        firestore
            .collection(RECOMMENDATIONS_COLLECTION)
            .document(recommendationId)
            .get()
            .addOnSuccessListener { document ->
                val recommendation = document.toObject<Recommendation>()
                if (recommendation != null) {
                    val coverType = getCoverType(recommendation.coversUrl)

                    val coverReference = recommendation.coversUrl[coverType]
                        ?.let { storage.getReferenceFromUrl(it) }

                    recommendationPreview = RecommendationPreviewItem(
                        id = recommendation.id,
                        uid = recommendation.uid,
                        title = recommendation.title,
                        creator = recommendation.creator,
                        coverReference = coverReference,
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

    override suspend fun getUserItemByUid(
        uid: String
    ): UserItem? {
        var userItem: UserItem? = null

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
                        photoReference = user.photoUrl
                            ?.let { storage.getReferenceFromUrl(it) }
                    )
                }
            }
            .addOnFailureListener {
                // Handle any errors
                Log.v(TAG, "error getUserItem()")
            }
            .await()

        return userItem
    }

    override suspend fun getFollowingRecommendationsIds(
        followingUid: String
    ): List<Pair<String, Date>> {
        val followingRecommendationsIds = arrayListOf<Pair<String, Date>>()

        firestore
            .collection(RECOMMENDATIONS_COLLECTION)
            .whereEqualTo("uid", followingUid)
            .get(Source.CACHE)
            .addOnSuccessListener {documents ->
                val recommendations = documents.toObjects<Recommendation>()
                for (recommendation in recommendations) {
                    if (recommendation.id != null && recommendation.date != null) {
                        followingRecommendationsIds.add(
                            Pair(recommendation.id, recommendation.date)
                        )
                    }
                }
            }
            .addOnFailureListener {
                // Handle any errors
                Log.v(TAG, "error getFollowingRecommendationsIds()")
            }
            .await()

        return followingRecommendationsIds
    }

    override suspend fun getStorageReferenceFromUrl(
        url: String
    ): StorageReference = storage.getReferenceFromUrl(url)

    private fun getCoverType(coversUrl: HashMap<String, String>): String {
        return when {
            coversUrl[ItemsShapes.square.name] != null ->
                ItemsShapes.square.name
            coversUrl[ItemsShapes.horizontal.name] != null ->
                ItemsShapes.horizontal.name
            coversUrl[ItemsShapes.vertical.name] != null ->
                ItemsShapes.vertical.name
            else -> ItemsShapes.horizontal.name
        }
    }

    companion object {
        private const val RECOMMENDATIONS_COLLECTION = "recommendations"
        private const val CATEGORIES_COLLECTION = "categories"
        private const val BANNERS_COLLECTION = "banners"
        private const val USERS_COLLECTION = "users"
    }
}