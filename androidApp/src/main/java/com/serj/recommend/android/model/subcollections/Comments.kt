package com.serj.recommend.android.model.subcollections

import com.google.firebase.firestore.DocumentId
import java.util.Date

data class Comments (
    @DocumentId val commentId: String? = null,
    val recommendationId: String? = null,

    val isLiked: Boolean = false,

    val date: Date? = null
)