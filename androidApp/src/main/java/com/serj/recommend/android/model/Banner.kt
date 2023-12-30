package com.serj.recommend.android.model

import android.graphics.Bitmap
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.google.firebase.firestore.DocumentId
import com.serj.recommend.android.model.items.RecommendationItem
import java.util.Date

data class Banner(
    @DocumentId val id: String? = null,

    val title: String? = null,
    val promo: String? = null,
    val creator: String? = null,
    val type: ArrayList<String>? = null,
    val description: String? = null,

    val coverReference: String? = null,
    val cover: MutableState<Bitmap?> = mutableStateOf(null),

    val backgroundReferences: HashMap<String, String> = hashMapOf(),
    val backgroundImage: MutableState<Bitmap?> = mutableStateOf(null),
    val backgroundVideo: String? = null,

    val date: Date? = null,
    val color: String? = null,

    val recommendationIds: ArrayList<String>? = null,
    val content: ArrayList<RecommendationItem>? = null
)