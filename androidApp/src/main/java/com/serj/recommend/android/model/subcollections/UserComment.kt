package com.serj.recommend.android.model.subcollections

import com.google.firebase.firestore.DocumentId
import java.util.Date

data class UserComment (
    @DocumentId val commentId: String? = null,
    val recommendationId: String? = null,

    val date: Date? = null,
    val likedBy: String? = null,
    val source: String? = null
)