package com.serj.recommend.android.model.items

import android.graphics.Bitmap
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.google.firebase.firestore.DocumentId

data class RecommendationPreview(
    @DocumentId val id: String? = null,
    val uid: String? = null,

    val title: String? = null,
    val creator: String? = null,

    val coverReference: String? = null,
    val cover: MutableState<Bitmap?> = mutableStateOf(null),
    var coverType: String? = null
)