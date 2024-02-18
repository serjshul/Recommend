package com.serj.recommend.android.model

import androidx.compose.ui.graphics.Color

enum class RecommendationType {
    Film, Music, Book,
    Series, Perfume, Place;

    fun getColor(): Color {
        return when (this) {
            Film -> Color.Red
            Music -> Color.Blue
            Book -> Color.Yellow
            Series -> Color.Green
            Perfume -> Color.Magenta
            Place -> Color.Cyan
        }
    }
}
