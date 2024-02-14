package com.serj.recommend.android.model.collections

import com.google.firebase.firestore.DocumentId
import com.google.firebase.storage.StorageReference
import com.serj.recommend.android.model.subcollections.Comment
import com.serj.recommend.android.model.subcollections.Like
import com.serj.recommend.android.model.subcollections.Repost
import java.util.Date

data class Recommendation(
    @DocumentId val id: String? = null,
    val uid: String? = null,

    val title: String? = null,
    val type: String? = null,
    val creator: String? = null,
    val tags: List<String>? = null,
    val year: Int? = null,

    val description: String? = null,
    val paragraphs: ArrayList<HashMap<String, String>> = arrayListOf(),
    val paragraphsReferences: HashMap<String, StorageReference?> = hashMapOf(),
    val quote: String? = null,
    val color: String? = null,

    val coversUrl: HashMap<String, String> = hashMapOf(),
    val coverReference: StorageReference? = null,
    var coverType: String? = null,

    val backgroundUrl: HashMap<String, String> = hashMapOf(),
    var backgroundImageReference: StorageReference? = null,
    var backgroundVideoReference: StorageReference? = null,

    val date: Date? = Date(),

    val likedBy: ArrayList<String> = arrayListOf(),
    val repostedBy: ArrayList<String> = arrayListOf(),

    val likes: ArrayList<Like> = arrayListOf(),
    val comments: ArrayList<Comment> = arrayListOf(),
    val reposts: ArrayList<Repost> = arrayListOf(),
    var topLikedComment: Comment? = null,

    val views: Int = 0,
    val coverage: Int = 0
)