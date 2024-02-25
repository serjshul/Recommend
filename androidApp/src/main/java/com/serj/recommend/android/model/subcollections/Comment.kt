package com.serj.recommend.android.model.subcollections

// TODO: Push this file to shared folder,
//  using: https://github.com/Kotlin/kotlinx-datetime
import com.google.firebase.firestore.DocumentId
import com.serj.recommend.android.model.items.UserItem
import java.util.Date

data class Comment(
    @DocumentId val id: String? = null,
    val recommendationId: String? = null,
    val userId: String? = null,

    val repliedCommentId: String? = null,
    val repliedUserId: String? = null,
    val isReply: Boolean? = false,

    val text: String? = null,

    val date: Date? = null,
    val source: String? = null,
    val likedBy: List<String> = listOf(),

    var userItem: UserItem? = null
)