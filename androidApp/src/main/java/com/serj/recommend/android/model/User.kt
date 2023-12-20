package com.serj.recommend.android.model

import com.google.firebase.firestore.DocumentId

data class User(
    @DocumentId val id: String = "",
    val uid: String? = null,
    val nickname: String? = null,
    val name: String? = null,
    val dateOfBirth: String? = null,
    val userPhoto: String? = null,
    val followers: ArrayList<String>? = null,
    val following: ArrayList<String>? = null,
    val postsIds: ArrayList<String>? = null,
    val likedIds: ArrayList<String>? = null,
    val savedIds: ArrayList<String>? = null
)