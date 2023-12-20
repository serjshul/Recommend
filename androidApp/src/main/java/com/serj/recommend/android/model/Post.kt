package com.serj.recommend.android.model

import com.google.firebase.firestore.DocumentId
import java.util.Date

data class Post(
    @DocumentId val id: String = "",
    val uid: String? = null,
    val recommendationId: String? = null,
    val text: String? = null,
    val date: Date? = null,
    val liked: ArrayList<String>? = null,
    val comments: ArrayList<String>? = null,
    val reposts: ArrayList<String>? = null,
    val views: Int? = null,
)