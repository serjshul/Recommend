package com.serj.recommend.android.model

import androidx.compose.ui.graphics.Color

enum class RecommendationType {
    Film, Music, Book,
    Series, Perfume, Place
}

fun RecommendationType.getColor(): Color {
    return when(this) {
        RecommendationType.Film -> Color.Red
        RecommendationType.Music -> Color.Blue
        RecommendationType.Book -> Color.Yellow
        RecommendationType.Series -> Color.Green
        RecommendationType.Perfume -> Color.Magenta
        RecommendationType.Place -> Color.Cyan
    }
}