package com.serj.recommend.android.model

import android.graphics.Bitmap
import com.google.firebase.firestore.DocumentId
import com.serj.recommend.android.model.items.RecommendationPreviewItem
import com.serj.recommend.android.ui.screens.main.home.CategoryType

data class Category(
    @DocumentId val id: String = "",

    val title: String = "",
    val type: String = CategoryType.ordinary.name,
    val color: String? = null,

    val backgroundImageReference: String? = null,
    val backgroundImage: Bitmap? = null,
    val backgroundVideoReference: String? = null,
    val backgroundVideo: String? = null,

    val recommendationIds: ArrayList<String> = arrayListOf(),
    val content: ArrayList<RecommendationPreviewItem>? = arrayListOf()
)