package com.serj.recommend.android.model

import com.google.firebase.firestore.DocumentId

data class Category(
    @DocumentId val id: String = "",
    val title: String = "",
    val type: String = "",
    val background: HashMap<String, String>? = null,
    val coverType: String = "",
    val color: String? = null,
    val recommendationIds: ArrayList<String> = arrayListOf(),
    var content: ArrayList<CategoryItem> = arrayListOf()
)