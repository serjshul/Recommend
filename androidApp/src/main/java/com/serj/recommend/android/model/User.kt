package com.serj.recommend.android.model

import android.graphics.Bitmap
import com.google.firebase.firestore.DocumentId
import java.util.Date

data class User(
    @DocumentId val uid: String? = null,
    val nickname: String? = null,
    val name: String? = null,
    val dateOfBirth: Date? = null,

    val photoReference: String? = null,
    var photo: Bitmap? = null,

    val followers: ArrayList<String>? = null,
    val following: ArrayList<String>? = null,

    val postsIds: ArrayList<String>? = null,

    val likedIds: ArrayList<String>? = null,
    val savedIds: ArrayList<String>? = null
)