package com.serj.recommend.android.model.subcollections

import com.google.firebase.firestore.DocumentId
import java.util.Date

data class RecommendationView (
    @DocumentId val userId: String? = null,

    val cameFrom: String? = null,
    val startWatchTime: Date? = null,
    val endWatchTime: Date? = null
)