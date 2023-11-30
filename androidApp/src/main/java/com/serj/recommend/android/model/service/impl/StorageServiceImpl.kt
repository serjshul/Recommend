package com.serj.recommend.android.model.service.impl

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.dataObjects
import com.google.firebase.firestore.toObject
import com.serj.recommend.android.model.Article
import com.serj.recommend.android.model.Category
import com.serj.recommend.android.model.service.AccountService
import com.serj.recommend.android.model.service.StorageService
import com.serj.recommend.android.model.service.trace
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class StorageServiceImpl
@Inject
constructor(private val firestore: FirebaseFirestore, private val auth: AccountService) :
    StorageService {

    @OptIn(ExperimentalCoroutinesApi::class)
    override val articles: Flow<List<Article>>
        get() = firestore.collection(ARTICLES_COLLECTION).dataObjects()
            /*
            auth.currentUser.flatMapLatest { user ->
                firestore.collection(ARTICLES_COLLECTION).whereEqualTo(USER_ID_FIELD, user.id).dataObjects()
            }

             */

    @OptIn(ExperimentalCoroutinesApi::class)
    override val categories: Flow<List<Category>>
        get() = firestore.collection(CATEGORY_COLLECTION).dataObjects()
    /*
    auth.currentUser.flatMapLatest { user ->
        firestore.collection(ARTICLES_COLLECTION).whereEqualTo(USER_ID_FIELD, user.id).dataObjects()
    }

     */

    override suspend fun getCategory(categoryId: String): Category? =
        firestore.collection(CATEGORY_COLLECTION).document(categoryId).get().await().toObject()

    override suspend fun getArticle(articleId: String): Article? =
        firestore.collection(ARTICLES_COLLECTION).document(articleId).get().await().toObject()

    override suspend fun saveArticle(article: Article): String =
        trace(SAVE_TASK_TRACE) {
            val articleWithUserId = article.copy(userId = auth.currentUserId)
            firestore.collection(ARTICLES_COLLECTION).add(articleWithUserId).await().id
        }

    override suspend fun updateArticle(article: Article): Unit =
        trace(UPDATE_TASK_TRACE) {
            firestore.collection(ARTICLES_COLLECTION).document(article.id).set(article).await()
        }

    override suspend fun deleteArticle(articleId: String) {
        firestore.collection(ARTICLES_COLLECTION).document(articleId).delete().await()
    }

    companion object {
        private const val USER_ID_FIELD = "userId"
        private const val ARTICLES_COLLECTION = "articles"
        private const val CATEGORY_COLLECTION = "categories"
        private const val SAVE_TASK_TRACE = "saveArticle"
        private const val UPDATE_TASK_TRACE = "updateArticle"
    }
}