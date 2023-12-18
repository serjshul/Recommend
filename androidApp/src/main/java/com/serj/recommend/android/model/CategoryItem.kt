package com.serj.recommend.android.model

import java.util.Date

data class CategoryItem(
    val recommendationId: String? = null,
    val title: String? = null,
    val creator: String? = null,
    val description: String? = null,
    val date: Date? = null,
    val cover: String? = null,
)