package com.serj.recommend.android.model.subcollections

import com.google.firebase.firestore.DocumentId
import com.serj.recommend.android.model.items.UserItem
import java.util.Date

data class RecommendationComment(
    @DocumentId val commentId: String? = null,
    val userId: String? = null,
    val repliedCommentId: String? = null,

    val text: String? = null,

    val date: Date? = null,
    val source: String? = null,
    val likedBy: List<String> = listOf(),

    var userItem: UserItem? = null
)