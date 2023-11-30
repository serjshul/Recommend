package com.serj.recommend.android.model

import com.google.firebase.firestore.DocumentId


data class Article(
    @DocumentId val id: String = "",
    val userId: String = "",
    val title: String = "",
    val creator: String = "",
    val tags: List<String> = arrayListOf(""),
    val description: String = "",
    val year: Int = 0
)