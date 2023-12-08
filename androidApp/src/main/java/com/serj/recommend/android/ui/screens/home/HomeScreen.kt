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
import com.serj.recommend.android.ui.screens.home.components.Banner
import com.serj.recommend.android.ui.screens.home.components.OrdinaryCategory

@Composable
fun HomeScreen(
    openScreen: (String) -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val options by viewModel.options

    val categories = viewModel.categories.collectAsStateWithLifecycle(emptyList())
    val categoriesImages = viewModel.categoriesImages
    val banner = viewModel.banner
    val bannerBackground = viewModel.bannerBackground.value

    HomeScreenContent(
        banner = banner.value,
        bannerBackground = bannerBackground,
        categories = categories.value.shuffled(),
        categoriesImages = categoriesImages,
        options = options,
        openScreen = openScreen,
        onRecommendationClick = viewModel::onRecommendationClick
    )
}

@Composable
fun HomeScreenContent(
    modifier: Modifier = Modifier,
    banner: Category?,
    bannerBackground: Bitmap?,
    categories: List<Category>,
    categoriesImages: Map<String?, List<Bitmap?>?>,
    openScreen: (String) -> Unit,
    onRecommendationClick: ((String) -> Unit, Recommendation) -> Unit,
    options: List<String>,
) {
    Scaffold() { paddingValues ->
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(color = Color.White)
        ) {
            if (banner != null) {
                item {
                    Banner(
                        title = banner.title,
                        description = banner.description,
                        background = bannerBackground
                    )
                }
            }

            for (category in categories) {
                item {
                    when (category.type) {
                        "Ordinary" -> {
                            OrdinaryCategory(
                                category = category,
                                covers = categoriesImages[category.title],
                                openScreen = openScreen,
                                onRecommendationClick = onRecommendationClick
                            )
                        }
                        "Gallery" -> {

                        }
                        "Extended" -> {

                        }
                        else -> {
                            // TODO: what to do?
                        }
                    }
                }
            }
        }
    }
}