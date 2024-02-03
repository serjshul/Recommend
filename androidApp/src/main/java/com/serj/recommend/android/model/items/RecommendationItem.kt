package com.serj.recommend.android.model.items

import com.google.firebase.firestore.DocumentId
import com.google.firebase.storage.StorageReference
import com.serj.recommend.android.model.Comment
import com.serj.recommend.android.ui.components.recommendationPreviews.ItemsShapes
import java.util.Date

data class RecommendationItem(
    @DocumentId val id: String? = null,
    val uid: String? = null,

    val title: String? = null,
    val creator: String? = null,
    val type: String? = null,
    val tags: List<String> = listOf(),
    val description: String? = null,
    val date: Date? = null,

    val coversUrl: HashMap<String, String> = hashMapOf(),
    var coverReference: StorageReference? = null,
    var coverType: String? = ItemsShapes.horizontal.name,

    val backgroundUrl: HashMap<String, String> = hashMapOf(),
    var backgroundImageReference: StorageReference? = null,
    var backgroundVideoReference: StorageReference? = null,

    var userItem: UserItem? = null,

    var isLiked: Boolean = false,
    var isReposted: Boolean = false,
    val comments: ArrayList<Comment> = arrayListOf(),
)