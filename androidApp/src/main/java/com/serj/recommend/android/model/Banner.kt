package com.serj.recommend.android.model

import android.graphics.Bitmap
import com.google.firebase.firestore.DocumentId
import com.serj.recommend.android.model.items.RecommendationItem
import java.util.Date

data class Banner(
    @DocumentId val id: String? = null,

    val title: String? = null,
    val promo: String? = null,
    val creator: String? = null,
    val type: ArrayList<String>? = null,
    val description: String? = null,

    val coverReference: String? = null,
    var cover: Bitmap? = null,

    val backgroundReferences: HashMap<String, String> = hashMapOf(),
    var backgroundImage: Bitmap? = null,
    var backgroundVideo: String? = null,

    val date: Date? = null,
    val color: String? = null,

    val recommendationIds: ArrayList<String>? = null,
    val content: ArrayList<RecommendationItem>? = null
)