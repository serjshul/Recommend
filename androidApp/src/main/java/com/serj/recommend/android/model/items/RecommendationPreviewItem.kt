package com.serj.recommend.android.model.items

import com.google.firebase.firestore.DocumentId
import com.google.firebase.storage.StorageReference
import com.serj.recommend.android.ui.components.recommendationPreviews.ItemsShapes

data class RecommendationPreviewItem(
    @DocumentId val id: String? = null,
    val uid: String? = null,

    val title: String? = null,
    val creator: String? = null,

    val coverType: String? =  ItemsShapes.horizontal.name,
    val coverReference: StorageReference? = null
)