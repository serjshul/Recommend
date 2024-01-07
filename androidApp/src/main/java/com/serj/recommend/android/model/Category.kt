package com.serj.recommend.android.model

import com.google.firebase.firestore.DocumentId
import com.google.firebase.storage.StorageReference
import com.serj.recommend.android.model.items.RecommendationPreview
import com.serj.recommend.android.ui.components.categories.CategoryTypes
import java.util.Date

data class Category(
    @DocumentId val id: String = "",

    val title: String? = null,
    val type: String? = CategoryTypes.ordinary.name,
    val color: String? = null,
    val date: Date? = null,

    val backgroundUrl: HashMap<String, String> = hashMapOf(),
    var backgroundImageReference: StorageReference? = null,
    var backgroundVideoReference: StorageReference? = null,

    val recommendationIds: ArrayList<String> = arrayListOf(),
    val content: ArrayList<RecommendationPreview> = arrayListOf()
)