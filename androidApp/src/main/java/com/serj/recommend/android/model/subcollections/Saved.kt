package com.serj.recommend.android.model.subcollections

import com.google.firebase.firestore.DocumentId
import java.util.Date

data class Saved (
    @DocumentId val id: String? = null,

    val title: String? = null,
    val content: HashMap<String, Date> = hashMapOf(),

    val coverUrl: String? = null
)