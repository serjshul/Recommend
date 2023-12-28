package com.serj.recommend.android.model.items

import android.graphics.Bitmap
import com.google.firebase.firestore.DocumentId
import java.util.Date

data class RecommendationItem(
    @DocumentId val id: String? = null,
    val uid: String? = null,

    val title: String? = null,
    val creator: String? = null,
    val description: String? = null,
    val date: Date? = null,

    var cover: Bitmap? = null,
    val coverType: String? = null,
    var backgroundImage: Bitmap? = null,
    var backgroundVideo: String? = null,

    var user: UserItem? = null,

    val isLiked: Boolean = false,
    val isReposted: Boolean = false,
)