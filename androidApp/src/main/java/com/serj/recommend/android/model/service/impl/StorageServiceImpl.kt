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
import com.serj.recommend.android.model.items.CategoryItem
import com.serj.recommend.android.model.items.RecommendationItem
import com.serj.recommend.android.model.items.UserItem
import com.serj.recommend.android.model.service.AccountService
import com.serj.recommend.android.model.service.StorageService
import com.serj.recommend.android.ui.styles.ItemsShapes
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class StorageServiceImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val storage: FirebaseStorage,
    private val auth: AccountService
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

    override suspend fun getRecommendation(recommendationId: String): Recommendation? {
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
                    if (recommendation!!.backgroundReferences["image"] != null) {
                        currentBackgroundImageReference = recommendation!!.backgroundReferences["image"]
                    }
                    for (paragraph in recommendation!!.paragraphs) {
                        currentParagraphsImagesReferences[paragraph.getValue("title")] =
                            paragraph.getOrDefault("image", null)
                    }
                }
            }.addOnFailureListener {
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

    override suspend fun getRecommendationItem(recommendationId: String): RecommendationItem? {
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
                    if (recommendation.backgroundReferences["image"] != null) {
                        currentBackgroundImageReference = recommendation.backgroundReferences["image"]
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
            }.addOnFailureListener {
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

    override suspend fun getBanner(bannerId: String): Banner? {
        var banner: Banner? = null
        var currentCoverReference: String? = null
        var currentBackgroundImageReference: String? = null

        firestore
            .collection(BANNERS_COLLECTION)
            .document(bannerId)
            .get()
            .addOnSuccessListener { document ->
                banner = document.toObject<Banner>()
                if (banner?.backgroundReferences?.get("image") != null) {
                    currentBackgroundImageReference = banner?.backgroundReferences?.get("image")
                }
                currentCoverReference = banner?.coverReference
            }.addOnFailureListener {
                Log.v(TAG, "error getCategoryItem()")
            }
            .await()

        Log.v(TAG, "1 " + banner.toString())

        banner?.cover = currentCoverReference?.let { downloadImage(it) }
        banner?.backgroundImage = currentBackgroundImageReference?.let { downloadImage(it) }
        for (recommendationId in banner?.recommendationIds!!) {
            getCategoryItem(recommendationId)?.let { banner?.content?.add(it) }
        }

        Log.v(TAG, banner.toString())

        return banner
    }

    override suspend fun getCategory(categoryId: String): Category? {
        var category: Category? = null
        var currentBackgroundImageReference: String? = null

        firestore
            .collection(CATEGORIES_COLLECTION)
            .document(categoryId)
            .get()
            .addOnSuccessListener { document ->
                val categoryData = document.toObject<Category>()
                if (categoryData != null) {
                    currentBackgroundImageReference = categoryData.backgroundImageReference
                    category = Category(
                        id = categoryData.id,
                        title = categoryData.title,
                        type = categoryData.type,
                        backgroundImageReference = categoryData.backgroundImageReference,
                        backgroundVideoReference = categoryData.backgroundVideoReference,
                        color = categoryData.color,
                        recommendationIds = categoryData.recommendationIds
                    )
                }
            }.addOnFailureListener {
                Log.v(TAG, "error getCategoryItem()")
            }
            .await()

        category?.backgroundImage = currentBackgroundImageReference?.let { downloadImage(it) }
        for (recommendationId in category?.recommendationIds!!) {
            getCategoryItem(recommendationId)?.let { category?.content?.add(it) }
        }

        return category
    }

    override suspend fun getCategoryItem(recommendationId: String): CategoryItem? {
        var categoryItem: CategoryItem? = null
        var currentCoverType: String?
        var currentCoverReference: String? = null

        firestore
            .collection(RECOMMENDATIONS_COLLECTION)
            .document(recommendationId)
            .get()
            .addOnSuccessListener { document ->
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

                    categoryItem = CategoryItem(
                        id = recommendation.id,
                        uid = recommendation.uid,
                        title = recommendation.title,
                        creator = recommendation.creator,
                        coverType = currentCoverType
                    )
                }
            }.addOnFailureListener {
                Log.v(TAG, "error getCategoryItem()")
            }
            .await()

        categoryItem?.cover = currentCoverReference?.let { downloadImage(it) }

        return categoryItem
    }

    override suspend fun getUserItem(uid: String): UserItem? {
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
            }.addOnFailureListener {
                // Handle any errors
                Log.v(TAG, "error getUserItem()")
            }
            .await()

        userItem?.photo = photoReference?.let { downloadImage(it) }

        return userItem
    }

    override suspend fun getFollowingRecommendations(followingUid: String): List<Recommendation> =
        firestore
            .collection(RECOMMENDATIONS_COLLECTION)
            .whereEqualTo("uid", followingUid)
            .get()
            .await()
            .toObjects()

    override suspend fun downloadImage(gsReference: String): Bitmap? {
        var bmp: Bitmap? = null

        storage
            .getReferenceFromUrl(gsReference)
            .getBytes(ONE_MEGABYTE)
            .addOnSuccessListener {
                // Data for "images/island.jpg" is returned, use this as needed
                bmp = BitmapFactory.decodeByteArray(it, 0, it.size)
            }.addOnFailureListener {
                // Handle any errors
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