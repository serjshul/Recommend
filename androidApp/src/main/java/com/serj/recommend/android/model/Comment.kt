package com.serj.recommend.android.model

import com.google.firebase.firestore.DocumentId
import java.util.Date

data class Comment(
    @DocumentId val id: String = "",
    val uid: String? = null,
    val postId: String? = null,
    val text: String? = null,
    val date: Date? = null,
    val liked: ArrayList<String>? = null,
)