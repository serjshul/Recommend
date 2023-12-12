package com.serj.recommend.android.model.service.impl

import android.content.ContentValues
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.dataObjects
import com.google.firebase.firestore.toObject
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import com.serj.recommend.android.model.Category
import com.serj.recommend.android.model.CategoryItem
import com.serj.recommend.android.model.Recommendation
import com.serj.recommend.android.model.service.AccountService
import com.serj.recommend.android.model.service.Banner
import com.serj.recommend.android.model.service.StorageService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

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

    override suspend fun getRecommendation(recommendationId: String): Recommendation? =
        firestore
            .collection(RECOMMENDATIONS_COLLECTION)
            .document(recommendationId)
            .get()
            .await()
            .toObject()

    override suspend fun getCategoryItem(recommendationId: String, coverType: String):
            CategoryItem? {
        var categoryItem: CategoryItem? = null

        firestore
            .collection(RECOMMENDATIONS_COLLECTION)
            .document(recommendationId)
            .get()
            .addOnSuccessListener {document ->
                val recommendation = document.toObject<Recommendation>()
                categoryItem = CategoryItem(
                    recommendationId = recommendationId,
                    title = recommendation!!.title,
                    creator = recommendation.creator,
                    cover = recommendation.cover[coverType] ?: "",
                    date = recommendation.date
                )
                //Log.v(ContentValues.TAG, "got CategoryItem")
            }.addOnFailureListener {
                // Handle any errors
                Log.v(ContentValues.TAG, "error getCategoryItem()")
            }
            .await()

        return categoryItem
    }

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
                Log.v(ContentValues.TAG, "not downloaded !!!")
            }
            .await()

        return bmp
    }

    override suspend fun uploadImage(uri: Uri, folderName: String, fileName: String): String {
        return suspendCoroutine { continuation ->
            val storageRef = storage.reference
            val storageReference = storageRef.child("$folderName/$fileName")

            val uploadTask = storageReference.putFile(uri)

            uploadTask
                .addOnFailureListener {
                    continuation.resumeWithException(it)
                }
                .addOnSuccessListener {
                    it.task.continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>> { task ->
                        if (!task.isSuccessful) {
                            task.exception?.let { exception ->
                                continuation.resumeWithException(exception)
                            }
                        }

                        return@Continuation storageReference.downloadUrl
                    }).addOnCompleteListener { uriTask ->
                        if (uriTask.isSuccessful) {
                            val downloadUri = uriTask.result
                            continuation.resume(downloadUri.toString())
                        }
                    }
                }
        }
    }

    companion object {
        private const val RECOMMENDATIONS_COLLECTION = "recommendations"
        private const val CATEGORIES_COLLECTION = "categories"
        private const val BANNERS_COLLECTION = "banners"

        private const val ONE_MEGABYTE: Long = 1024 * 1024
    }
}