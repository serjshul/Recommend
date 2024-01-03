package com.serj.recommend.android.model

import com.google.firebase.firestore.DocumentId
import com.google.firebase.storage.StorageReference
import com.serj.recommend.android.model.items.RecommendationItem
import java.util.Date

data class Banner(
    @DocumentId val id: String? = null,

    val title: String? = null,
    val promo: String? = null,
    val creator: String? = null,
    val type: ArrayList<String>? = null,
    val description: String? = null,
    val date: Date? = null,
    val color: String? = null,

    val coverUrl: String? = null,
    var coverReference: StorageReference? = null,

    val backgroundUrl: HashMap<String, String> = hashMapOf(),
    var backgroundImageReference: StorageReference? = null,
    var backgroundVideoReference: StorageReference? = null,

    val recommendationIds: ArrayList<String>? = arrayListOf(),
    val content: ArrayList<RecommendationItem>? = arrayListOf()
)