package com.serj.recommend.android.model

import com.google.firebase.firestore.DocumentId

data class RecommendationItem(
    @DocumentId val id: String? = null,
    val title: String? = null,
    val creator: String? = null,
    val background: HashMap<String, String>? = null
)