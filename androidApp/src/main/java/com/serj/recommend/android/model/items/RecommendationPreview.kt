package com.serj.recommend.android.model.items

import com.google.firebase.firestore.DocumentId
import com.google.firebase.storage.StorageReference
import com.serj.recommend.android.ui.components.recommendationPreviews.ItemsShapes

data class RecommendationPreview(
    @DocumentId val id: String? = null,
    val uid: String? = null,

    val title: String? = null,
    val creator: String? = null,
    val type: String? = null,
    val tags: List<String> = listOf(),

    val coversUrl: HashMap<String, String> = hashMapOf(),
    var coverReference: StorageReference? = null,
    var coverType: String? =  ItemsShapes.horizontal.name
)