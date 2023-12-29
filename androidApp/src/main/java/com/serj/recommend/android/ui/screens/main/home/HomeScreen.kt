package com.serj.recommend.android.ui.screens.main.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import com.serj.recommend.android.R
import com.serj.recommend.android.model.Banner
import com.serj.recommend.android.model.Category
import com.serj.recommend.android.model.Recommendation
import com.serj.recommend.android.ui.components.snackbar.SnackbarManager
import com.serj.recommend.android.ui.screens.main.home.components.Banner
import com.serj.recommend.android.ui.screens.main.home.components.categoryItems.ExtendedCategory
import com.serj.recommend.android.ui.screens.main.home.components.categoryItems.OrdinaryCategory
import com.serj.recommend.android.ui.screens.main.home.components.categoryItems.PagerCategory

enum class CategoryType {
    ordinary, extended, pager
}

@Composable
fun HomeScreen(
    openScreen: (String) -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val currentCategories = viewModel.currentCategories
    val currentBanner = viewModel.currentBanner

    HomeScreenContent(
        banner = currentBanner.value,
        categories = currentCategories,
        openScreen = openScreen,
        onRecommendationClick = viewModel::onRecommendationClick,
        onBannerClick = viewModel::onBannerClick,
        onCategoryClick = viewModel::onCategoryClick
    )
}

@Composable
fun HomeScreenContent(
    modifier: Modifier = Modifier,
    banner: Banner?,
    categories: List<MutableState<Category>>,
    openScreen: (String) -> Unit,
    onRecommendationClick: ((String) -> Unit, Recommendation) -> Unit,
    onBannerClick: ((String) -> Unit, String) -> Unit,
    onCategoryClick: ((String) -> Unit, String) -> Unit
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
                        banner = banner,
                        openScreen = openScreen,
                        onBannerClick = onBannerClick
                    )
                }
            }

            items(categories) { category ->
                val value = category.value
                when (value.type) {
                    CategoryType.ordinary.name -> {
                        OrdinaryCategory(
                            category = value,
                            openScreen = openScreen,
                            onRecommendationClick = onRecommendationClick,
                            onCategoryClick = onCategoryClick
                        )
                    }
                    CategoryType.extended.name -> {
                        ExtendedCategory(
                            category = value,
                            openScreen = openScreen,
                            onRecommendationClick = onRecommendationClick,
                            onCategoryClick = onCategoryClick
                        )
                    }
                    CategoryType.pager.name -> {
                        PagerCategory(
                            category = value,
                            openScreen = openScreen,
                            onRecommendationClick = onRecommendationClick,
                            onCategoryClick = onCategoryClick
                        )
                    }
                    else -> {
                        // TODO: Declare TODOs more understandable!
                        //  (add more description to TODOs)
                        // TODO: what to do?

                        SnackbarManager.showMessage(R.string.error_category_type)

                        // TODO: if this doesn't must happened anytime of app lifecycle,
                        //  use enums for categories, so when always be fulled
                    }
                }
            }
        }
    }
}