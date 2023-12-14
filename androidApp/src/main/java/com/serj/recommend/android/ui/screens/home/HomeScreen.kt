package com.serj.recommend.android.ui.screens.home

import android.graphics.Bitmap
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.serj.recommend.android.model.Category
import com.serj.recommend.android.model.CategoryItem
import com.serj.recommend.android.model.Recommendation
import com.serj.recommend.android.model.service.Banner
import com.serj.recommend.android.ui.screens.home.components.Banner
import com.serj.recommend.android.ui.screens.home.components.categoryItems.ExtendedCategory
import com.serj.recommend.android.ui.screens.home.components.categoryItems.OrdinaryCategory

@Composable
fun HomeScreen(
    openScreen: (String) -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val options by viewModel.options

    val categories = viewModel.categories.collectAsStateWithLifecycle(emptyList())
    val categoriesBackgrounds = viewModel.categoriesBackgrounds
    val categoriesItems = viewModel.categoriesItems
    val categoriesImages = viewModel.categoriesImages

    val banner = viewModel.banner
    val bannerItems = viewModel.bannerItems
    val bannerBackground = viewModel.bannerBackground.value

    HomeScreenContent(
        banner = banner.value,
        bannerItems = bannerItems,
        bannerBackground = bannerBackground,
        categories = categories.value,
        categoriesBackgrounds = categoriesBackgrounds,
        categoriesItems = categoriesItems,
        categoriesImages = categoriesImages,
        options = options,
        openScreen = openScreen,
        onRecommendationClick = viewModel::onRecommendationClick
    )
}

@Composable
fun HomeScreenContent(
    modifier: Modifier = Modifier,
    banner: Banner?,
    bannerItems: List<CategoryItem?>?,
    bannerBackground: Bitmap?,
    categories: List<Category>,
    categoriesBackgrounds: Map<String?, Bitmap?>,
    categoriesItems: Map<String?, List<CategoryItem?>?>,
    categoriesImages: Map<String?, List<Bitmap?>?>,
    openScreen: (String) -> Unit,
    onRecommendationClick: ((String) -> Unit, Recommendation) -> Unit,
    options: List<String>,
) {
    Scaffold { paddingValues ->
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

            items(categories) { category ->
                when (category.type) {
                    "Ordinary" -> {
                        OrdinaryCategory(
                            category = category,
                            items = categoriesItems[category.title],
                            covers = categoriesImages[category.title],
                            openScreen = openScreen,
                            onRecommendationClick = onRecommendationClick
                        )
                    }

                    "Extended" -> {
                        ExtendedCategory(
                            category = category,
                            backgroundImage = categoriesBackgrounds[category.title],
                            items = categoriesItems[category.title],
                            covers = categoriesImages[category.title],
                            openScreen = openScreen,
                            onRecommendationClick = onRecommendationClick
                        )
                    }

                    "Gallery" -> {

                    }

                    else -> {
                        // TODO: Declare TODOs more understandable!
                        //  (add more description to TODOs)
                        // TODO: what to do?
                    }
                }
            }
        }
    }
}