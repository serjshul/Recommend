package com.serj.recommend.android.ui.screens.main.home

import android.graphics.Bitmap
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.serj.recommend.android.R
import com.serj.recommend.android.model.Banner
import com.serj.recommend.android.model.Category
import com.serj.recommend.android.model.CategoryItem
import com.serj.recommend.android.model.Recommendation
import com.serj.recommend.android.ui.CATEGORY_EXTENDED
import com.serj.recommend.android.ui.CATEGORY_ORDINARY
import com.serj.recommend.android.ui.CATEGORY_PAGER
import com.serj.recommend.android.ui.components.snackbar.SnackbarManager
import com.serj.recommend.android.ui.screens.main.home.components.Banner
import com.serj.recommend.android.ui.screens.main.home.components.categoryItems.ExtendedCategory
import com.serj.recommend.android.ui.screens.main.home.components.categoryItems.OrdinaryCategory
import com.serj.recommend.android.ui.screens.main.home.components.categoryItems.PagerCategory

@Composable
fun HomeScreen(
    openScreen: (String) -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val categories = viewModel.categories.collectAsStateWithLifecycle(emptyList())
    val categoriesBackgrounds = viewModel.categoriesBackgrounds
    val categoriesItems = viewModel.categoriesItems
    val categoriesImages = viewModel.categoriesImages

    val banner = viewModel.banner
    val bannerCover = viewModel.bannerCover.value

    HomeScreenContent(
        banner = banner.value,
        bannerCover = bannerCover,
        categories = categories.value,
        categoriesBackgrounds = categoriesBackgrounds,
        categoriesItems = categoriesItems,
        categoriesImages = categoriesImages,
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
    bannerCover: Bitmap?,
    categories: List<Category>,
    categoriesBackgrounds: Map<String?, Bitmap?>,
    categoriesItems: Map<String?, List<CategoryItem?>?>,
    categoriesImages: Map<String?, List<Bitmap?>?>,
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
                        bannerId = banner.id,
                        title = banner.title,
                        promo = banner.promo,
                        backgroundImage = bannerCover,
                        openScreen = openScreen,
                        onBannerClick = onBannerClick
                    )
                }
            }

            for (category in categories) {
                item {
                    when (category.type) {
                        CATEGORY_ORDINARY -> {
                            OrdinaryCategory(
                                category = category,
                                items = categoriesItems[category.title],
                                covers = categoriesImages[category.title],
                                openScreen = openScreen,
                                onRecommendationClick = onRecommendationClick,
                                onCategoryClick = onCategoryClick
                            )
                        }
                        CATEGORY_EXTENDED -> {
                            ExtendedCategory(
                                category = category,
                                backgroundImage = categoriesBackgrounds[category.title],
                                items = categoriesItems[category.title],
                                covers = categoriesImages[category.title],
                                openScreen = openScreen,
                                onRecommendationClick = onRecommendationClick,
                                onCategoryClick = onCategoryClick
                            )
                        }
                        CATEGORY_PAGER -> {
                            PagerCategory(
                                category = category,
                                items = categoriesItems[category.title],
                                covers = categoriesImages[category.title],
                                openScreen = openScreen,
                                onRecommendationClick = onRecommendationClick,
                                onCategoryClick = onCategoryClick
                            )
                        }
                        else -> {
                            SnackbarManager.showMessage(R.string.error_category_type)
                        }
                    }
                }
            }
        }
    }
}