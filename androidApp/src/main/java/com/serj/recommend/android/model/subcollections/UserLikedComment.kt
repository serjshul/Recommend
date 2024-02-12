package com.serj.recommend.android.model.subcollections

import com.google.firebase.firestore.DocumentId
import java.util.Date

data class UserLikedComment (
    @DocumentId val commentId: String? = null,
    val recommendationId: String? = null,

    val date: Date? = null,
    val source: String? = null
)