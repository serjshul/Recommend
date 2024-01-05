package com.serj.recommend.android.model.items

import com.google.firebase.firestore.DocumentId
import com.google.firebase.storage.StorageReference

data class UserItem(
    @DocumentId val uid: String? = null,

    val nickname: String? = null,

    val photoReference: StorageReference? = null
)