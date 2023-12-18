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
import com.serj.recommend.android.model.Banner
import com.serj.recommend.android.model.Category
import com.serj.recommend.android.model.CategoryItem
import com.serj.recommend.android.model.Recommendation
import com.serj.recommend.android.ui.screens.main.home.components.Banner
import com.serj.recommend.android.ui.screens.main.home.components.categoryItems.ExtendedCategory
import com.serj.recommend.android.ui.screens.main.home.components.categoryItems.OrdinaryCategory
import com.serj.recommend.android.ui.screens.main.home.components.categoryItems.PagerCategory

const val ORDINARY_CATEGORY = "ordinary"
const val EXTENDED_CATEGORY = "extended"
const val PAGER_CATEGORY = "pager"

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
                        ORDINARY_CATEGORY -> {
                            OrdinaryCategory(
                                category = category,
                                items = categoriesItems[category.title],
                                covers = categoriesImages[category.title],
                                openScreen = openScreen,
                                onRecommendationClick = onRecommendationClick,
                                onCategoryClick = onCategoryClick
                            )
                        }
                        EXTENDED_CATEGORY -> {
                            ExtendedCategory(
                                category = category,
                                backgroundImage = categoriesBackgrounds[category.title],
                                items = categoriesItems[category.title],
                                covers = categoriesImages[category.title],
                                openScreen = openScreen,
                                onRecommendationClick = onRecommendationClick
                            )
                        }
                        PAGER_CATEGORY -> {
                            PagerCategory(
                                category = category,
                                items = categoriesItems[category.title],
                                covers = categoriesImages[category.title],
                                openScreen = openScreen,
                                onRecommendationClick = onRecommendationClick
                            )
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