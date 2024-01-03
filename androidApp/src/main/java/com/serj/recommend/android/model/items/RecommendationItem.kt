package com.serj.recommend.android.model.items

import com.google.firebase.firestore.DocumentId
import com.google.firebase.storage.StorageReference
import com.serj.recommend.android.ui.styles.ItemsShapes
import java.util.Date

data class RecommendationItem(
    @DocumentId val id: String? = null,
    val uid: String? = null,

    val title: String? = null,
    val creator: String? = null,
    val description: String? = null,
    val date: Date? = null,

    val coverType: String? = ItemsShapes.horizontal.name,
    val coverReference: StorageReference? = null,

    val backgroundImageReference: StorageReference? = null,
    val backgroundVideoReference: StorageReference? = null,

    var user: UserItem? = null,

    val isLiked: Boolean = false,
    val isReposted: Boolean = false,
)