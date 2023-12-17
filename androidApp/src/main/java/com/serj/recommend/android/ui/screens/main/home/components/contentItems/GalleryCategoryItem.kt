package com.serj.recommend.android.ui.screens.main.home.components.contentItems

import android.graphics.Bitmap
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.serj.recommend.android.model.Recommendation

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GalleryCategoryItem(
    modifier: Modifier = Modifier,
    recommendationId: String?,
    title: String?,
    creator: String?,
    cover: Bitmap?,
    openScreen: (String) -> Unit,
    onRecommendationClick: ((String) -> Unit, Recommendation) -> Unit
) {
    Column(
        modifier = modifier
    ) {

    }
}