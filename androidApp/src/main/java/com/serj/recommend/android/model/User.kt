package com.serj.recommend.android.model

import com.google.firebase.firestore.DocumentId
import com.google.firebase.storage.StorageReference
import java.util.Date

data class User(
    @DocumentId val uid: String? = null,

    val nickname: String? = null,
    val name: String? = null,
    val dateOfBirth: Date? = null,

    val photoUrl: String? = null,
    var photoReference: StorageReference? = null,

    val followers: ArrayList<String>? = null,
    val following: ArrayList<String>? = null,

    val recommendationsIds: ArrayList<String>? = null,

    val likedIds: ArrayList<String>? = null,
    val savedIds: ArrayList<String>? = null
)