package com.serj.recommend.android.model.collections

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

    val followers: ArrayList<String> = arrayListOf(),
    val following: ArrayList<String> = arrayListOf(),

    val recommendationsIds: ArrayList<String> = arrayListOf(),

    val content: HashMap<String, Boolean> = hashMapOf(),

    val likedIds: ArrayList<String> = arrayListOf(),
    val savedIds: ArrayList<String> = arrayListOf()
)