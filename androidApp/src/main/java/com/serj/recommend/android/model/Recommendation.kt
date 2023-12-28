package com.serj.recommend.android.model

import android.graphics.Bitmap
import com.google.firebase.firestore.DocumentId
import java.util.Date

data class Recommendation(
    @DocumentId val id: String? = null,
    val uid: String? = null,
    val date: Date? = null,
    val color: String? = null,

    val title: String? = null,
    val type: String? = null,
    val creator: String? = null,
    val tags: List<String>? = null,
    val year: Int? = null,

    val description: String? = null,
    val quote: String? = null,
    val paragraphs: ArrayList<HashMap<String, String>> = arrayListOf(),
    var paragraphsImages: HashMap<String, Bitmap?> = hashMapOf(),

    val coversReferences: HashMap<String, String> = hashMapOf(),
    var cover: Bitmap? = null,
    var coverType: String? = null,
    val backgroundReferences: HashMap<String, String> = hashMapOf(),
    var backgroundImage: Bitmap? = null,
    var backgroundVideo: String? = null,

    val likes: ArrayList<String> = arrayListOf(),
    val comments: ArrayList<String> = arrayListOf(),
    val reposts: ArrayList<String> = arrayListOf(),
    val views: Int = 0
)