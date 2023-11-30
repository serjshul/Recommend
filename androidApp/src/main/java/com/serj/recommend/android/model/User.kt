package com.serj.recommend.android.model

import com.google.firebase.firestore.DocumentId

data class User(
    @DocumentId val id: String = "",
    val isAnonymous: Boolean = true
)