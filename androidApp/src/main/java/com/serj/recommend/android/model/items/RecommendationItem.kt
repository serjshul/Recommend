package com.serj.recommend.android.model.items

import android.graphics.Bitmap
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.google.firebase.firestore.DocumentId
import com.serj.recommend.android.ui.styles.ItemsShapes
import java.util.Date

data class RecommendationItem(
    @DocumentId val id: String? = null,
    val uid: String? = null,

    val title: String? = null,
    val creator: String? = null,
    val description: String? = null,
    val date: Date? = null,

    val coverReference: String? = null,
    var cover: MutableState<Bitmap?> = mutableStateOf(null),
    val coverType: String? = ItemsShapes.horizontal.name,

    val backgroundImageReference: String? = null,
    var backgroundImage: MutableState<Bitmap?> = mutableStateOf(null),
    val backgroundVideoReference: String? = null,
    var backgroundVideo: String? = null,

    var user: UserItem? = null,

    val isLiked: Boolean = false,
    val isReposted: Boolean = false,
)