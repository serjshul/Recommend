package com.serj.recommend.android.model.collections

import com.google.firebase.firestore.DocumentId
import com.google.firebase.storage.StorageReference
import com.serj.recommend.android.model.subcollections.Comment
import com.serj.recommend.android.model.subcollections.Like
import com.serj.recommend.android.model.subcollections.Repost
import com.serj.recommend.android.model.subcollections.UserContent
import java.util.Date

data class User(
    @DocumentId val uid: String? = null,

    val name: String? = null,
    val nickname: String? = null,
    val bio: String? = null,
    val dateOfBirth: Date? = null,

    val photoUrl: String? = null,
    var photoReference: StorageReference? = null,

    val followers: ArrayList<String> = arrayListOf(),
    val following: ArrayList<String> = arrayListOf(),

    val content: List<UserContent> = listOf(),

    val likes: List<Like> = listOf(),
    val comment: List<Comment> = listOf(),
    val reposts: List<Repost> = listOf()
)