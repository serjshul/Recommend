package com.serj.recommend.android.model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.google.firebase.firestore.DocumentId
import com.google.firebase.storage.StorageReference
import com.serj.recommend.android.model.items.RecommendationItem
import java.util.Date

data class Banner(
    @DocumentId val id: String? = null,

    val title: String? = null,
    val promo: String? = null,
    val creator: String? = null,
    val type: ArrayList<String>? = null,
    val description: String? = null,
    val date: Date? = null,
    val color: String? = null,

    val coverUrl: String? = null,
    val coverReference: MutableState<StorageReference?> = mutableStateOf(null),

    val backgroundImageUrl: String? = null,
    val backgroundImageReference: MutableState<StorageReference?> = mutableStateOf(null),
    val backgroundVideoUrl: String? = null,
    val backgroundVideoReference: MutableState<StorageReference?> = mutableStateOf(null),

    val recommendationIds: ArrayList<String>? = null,
    val content: ArrayList<RecommendationItem>? = null
)