package com.serj.recommend.android.model.items

import android.graphics.Bitmap
import com.google.firebase.firestore.DocumentId

data class RecommendationPreview(
    @DocumentId val id: String? = null,
    val uid: String? = null,

    val title: String? = null,
    val creator: String? = null,

    val coverReference: String? = null,
    var cover: Bitmap? = null,
    var coverType: String? = null
)