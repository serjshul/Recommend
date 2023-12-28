package com.serj.recommend.android.model

import android.graphics.Bitmap
import com.google.firebase.firestore.DocumentId
import com.serj.recommend.android.model.items.CategoryItem
import com.serj.recommend.android.ui.screens.main.home.CategoryType

data class Category(
    @DocumentId val id: String = "",

    val title: String = "",
    val type: String = CategoryType.ordinary.name,

    val backgroundImageReference: String? = null,
    var backgroundImage: Bitmap? = null,
    val backgroundVideoReference: String? = null,
    var backgroundVideo: String? = null,

    val color: String? = null,
    val recommendationIds: ArrayList<String> = arrayListOf(),
    val content: ArrayList<CategoryItem> = arrayListOf()
)