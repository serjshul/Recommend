package com.serj.recommend.android.model

import com.google.firebase.firestore.DocumentId
import java.util.Date

data class Banner(
    @DocumentId val id: String? = null,
    val title: String? = null,
    val promo: String? = null,
    val creator: String? = null,
    val type: ArrayList<String>? = null,
    val description: String? = null,
    val cover: HashMap<String, String>? = null,
    val background: HashMap<String, String>? = null,
    val coverType: String? = null,
    val color: String? = null,
    val recommendationIds: ArrayList<String>? = null,
    var content: ArrayList<CategoryItem>? = null,
    var author: String? = null,
    var date: Date? = null
)