package com.serj.recommend.android.model

import com.google.firebase.firestore.DocumentId
import java.util.Date

data class Recommendation(
    @DocumentId val id: String = "",
    val date: Date = Date(0),
    val authorId: String = "",
    val title: String = "",
    val creator: String = "",
    val cover: HashMap<String, String> = hashMapOf(),
    val type: String = "",
    val tags: List<String> = arrayListOf(""),
    val background: HashMap<String, String> = hashMapOf(),
    val description: String = "",
    val year: Int = 0,
    val quote: String = "",
    val color: String = "#000000",
    val paragraphs: ArrayList<HashMap<String, String>> = arrayListOf(),
    val viewsCount: Int = 0,
    val likesCount: Int = 0,
    val commentsCount: Int = 0,
    val repostsCount: Int = 0,
    val comments: ArrayList<HashMap<String, String>> = arrayListOf()
)