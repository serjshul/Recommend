package com.serj.recommend.android.model

import androidx.compose.ui.graphics.Color
import com.serj.recommend.datalayer.RecommendationType

fun RecommendationType.getColor() = when (this) {
    RecommendationType.Film -> Color.Red
    RecommendationType.Music -> Color.Blue
    RecommendationType.Book -> Color.Yellow
    RecommendationType.Series -> Color.Green
    RecommendationType.Perfume -> Color.Magenta
    RecommendationType.Place -> Color.Cyan
}
