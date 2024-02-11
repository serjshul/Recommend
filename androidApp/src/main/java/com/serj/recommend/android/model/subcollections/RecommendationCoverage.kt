package com.serj.recommend.android.model.subcollections

import com.google.firebase.firestore.DocumentId
import java.util.Date

data class RecommendationCoverage (
    @DocumentId val recommendationId: String? = null,

    val source: String? = null,
    val startWatchTime: Date? = null,
    val endWatchTime: Date? = null
)