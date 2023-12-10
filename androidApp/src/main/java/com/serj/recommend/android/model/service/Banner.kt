package com.serj.recommend.android.model.service

import com.google.firebase.firestore.DocumentId
import com.serj.recommend.android.model.CategoryItem

data class Banner(
    @DocumentId val id: String = "",
    val title: String? = null,
    val description: String? = null,
    val background: HashMap<String, String>? = null,
    val recommendationIds: ArrayList<String>? = null,
    var content: ArrayList<CategoryItem>? = null
)