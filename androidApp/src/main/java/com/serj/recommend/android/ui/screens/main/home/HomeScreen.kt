package com.serj.recommend.android.ui.screens.main.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.serj.recommend.android.model.Banner
import com.serj.recommend.android.model.Category
import com.serj.recommend.android.model.Recommendation
import com.serj.recommend.android.ui.components.categories.CategoryTypes
import com.serj.recommend.android.ui.components.categories.ExtendedCategory
import com.serj.recommend.android.ui.components.categories.OrdinaryCategory
import com.serj.recommend.android.ui.components.categories.PagerCategory
import com.serj.recommend.android.ui.components.loadingIndicators.LargeLoadingIndicator
import com.serj.recommend.android.ui.components.loadingIndicators.SmallLoadingIndicator
import com.serj.recommend.android.ui.screens.main.home.components.Banner
import com.serj.recommend.android.ui.styles.White

@Composable
fun HomeScreen(
    openScreen: (String) -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val currentCategories = viewModel.currentCategories
    val currentCategoriesAmount = viewModel.currentCategoriesAmount.intValue
    val currentBanner = viewModel.currentBanner

    HomeScreenContent(
        banner = currentBanner.value,
        categories = currentCategories,
        categoriesAmount = currentCategoriesAmount,
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
    categoriesAmount: Int,
    openScreen: (String) -> Unit,
    onRecommendationClick: ((String) -> Unit, Recommendation) -> Unit,
    onBannerClick: ((String) -> Unit, String) -> Unit,
    onCategoryClick: ((String) -> Unit, String) -> Unit
) {
    Scaffold { paddingValues ->
        if (banner != null && categories.isNotEmpty()) {
            var isLoading by rememberSaveable { mutableStateOf(true) }
            var currentCategoriesAmount = 0

            LazyColumn(
                modifier = modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(color = Color.White)
            ) {
                item {
                    Banner(
                        banner = banner,
                        coverReference = banner.coverReference,
                        openScreen = openScreen,
                        onBannerClick = onBannerClick
                    )
                }

                items(categories) { category ->
                    when (category.value.type) {
                        CategoryTypes.extended.name -> {
                            ExtendedCategory(
                                category = category.value,
                                openScreen = openScreen,
                                onRecommendationClick = onRecommendationClick,
                                onCategoryClick = onCategoryClick
                            )
                        }
                        CategoryTypes.pager.name -> {
                            PagerCategory(
                                category = category.value,
                                openScreen = openScreen,
                                onRecommendationClick = onRecommendationClick,
                                onCategoryClick = onCategoryClick
                            )
                        }
                        else -> {
                            OrdinaryCategory(
                                category = category.value,
                                openScreen = openScreen,
                                onRecommendationClick = onRecommendationClick,
                                onCategoryClick = onCategoryClick
                            )
                        }
                    }

                    currentCategoriesAmount++
                    isLoading = currentCategoriesAmount < categoriesAmount
                }

                if (isLoading) {
                    item {
                        SmallLoadingIndicator(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(80.dp),
                            backgroundColor = White
                        )
                    }
                }
            }
        } else {
            LargeLoadingIndicator(
                modifier = Modifier.fillMaxSize(),
                backgroundColor = White
            )
        }
    }
}