package com.serj.recommend.android.ui.screens.home

import android.graphics.Bitmap
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.serj.recommend.android.model.Category
import com.serj.recommend.android.model.Recommendation
import com.serj.recommend.android.ui.screens.home.categories.OrdinaryCategory
import com.serj.recommend.android.ui.screens.home.components.Banner

@Composable
fun HomeScreen(
    openScreen: (String) -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val categories = viewModel.categories.collectAsStateWithLifecycle(emptyList())
    val categoriesImages = viewModel.categoriesImages

    val options by viewModel.options

    HomeScreenContent(
        categories = categories.value,
        options = options,
        openScreen = openScreen,
        categoriesImages = categoriesImages,
        onRecommendationClick = viewModel::onRecommendationClick
    )
}

@Composable
fun HomeScreenContent(
    modifier: Modifier = Modifier,
    categories: List<Category>,
    categoriesImages: Map<String?, List<Bitmap?>?>,
    options: List<String>,
    openScreen: (String) -> Unit,
    onRecommendationClick: ((String) -> Unit, Recommendation) -> Unit
) {
    Scaffold() { paddingValues ->
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(color = Color.White)
        ) {
            item {
                Banner()
            }

            for (category in categories) {
                item {
                    OrdinaryCategory(
                        category = category,
                        covers = categoriesImages[category.title],
                        openScreen = openScreen,
                        onRecommendationClick = onRecommendationClick
                    )
                }
            }
        }
    }
}