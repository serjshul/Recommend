package com.serj.recommend.android.model.subcollections

import com.google.firebase.firestore.DocumentId
import java.util.Date

data class RecommendationLike (
    @DocumentId val userId: String? = null,

    val date: Date? = null,

    val source: String? = null
)