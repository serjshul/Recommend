package com.serj.recommend.android.model

import com.google.firebase.firestore.DocumentId

data class Category(
    @DocumentId val id: String = "",
    val title: String = "",
    val type: String = "",
    val content: ArrayList<HashMap<String, String>> = arrayListOf(),
)