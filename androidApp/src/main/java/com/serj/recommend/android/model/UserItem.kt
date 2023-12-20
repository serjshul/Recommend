package com.serj.recommend.android.model

import com.google.firebase.firestore.DocumentId

data class UserItem(
    @DocumentId val uid: String? = null,
    val nickname: String? = null,
    val name: String? = null,
    val userPhoto: String? = null,
)