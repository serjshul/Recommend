package com.serj.recommend.android.model.collections

import com.google.firebase.firestore.DocumentId
import com.google.firebase.storage.StorageReference
import com.serj.recommend.android.model.items.Preview
import com.serj.recommend.android.ui.components.categories.CategoryTypes
import com.serj.recommend.android.ui.components.recommendationPreviews.ItemsShapes
import java.util.Date

data class Category(
    @DocumentId val id: String = "",

    val title: String? = null,
    val type: String? = CategoryTypes.ordinary.name,
    val color: String? = null,
    val date: Date? = null,
    val coverType: String = ItemsShapes.horizontal.name,

    val backgroundUrl: HashMap<String, String> = hashMapOf(),
    var backgroundImageReference: StorageReference? = null,
    var backgroundVideoReference: StorageReference? = null,

    val recommendationsIds: ArrayList<String> = arrayListOf(),
    val content: ArrayList<Preview> = arrayListOf()
)