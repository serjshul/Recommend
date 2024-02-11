package com.serj.recommend.android.model.subcollections

import com.google.firebase.firestore.DocumentId
import java.util.Date

data class UserContent (
    @DocumentId val recommendationId: String? = null,
    val isReposted: Boolean? = null,
    val dateOfCreation: Date? = null
)