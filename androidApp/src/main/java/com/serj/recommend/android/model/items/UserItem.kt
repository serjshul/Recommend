package com.serj.recommend.android.model.items

import android.graphics.Bitmap
import com.google.firebase.firestore.DocumentId

data class UserItem(
    @DocumentId val uid: String? = null,
    val nickname: String? = null,
    var photo: Bitmap? = null,
)