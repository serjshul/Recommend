package com.serj.recommend.android.model

import com.google.firebase.firestore.DocumentId
import com.serj.recommend.android.model.items.UserItem
import java.util.Date

data class Comment(
    @DocumentId val id: String? = null,
    val userId: String? = null,
    val repliedCommentId: String? = null,

    val text: String? = null,
    val date: Date? = null,

    val likedBy: ArrayList<String>? = arrayListOf(),

    var userItem: UserItem? = null
)