package com.serj.recommend.android.ui.screens.main.home.category

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.serj.recommend.android.R
import com.serj.recommend.android.common.ext.horizontalRecommendationCategory
import com.serj.recommend.android.common.ext.squareRecommendationCategory
import com.serj.recommend.android.model.Category
import com.serj.recommend.android.model.CategoryItem
import com.serj.recommend.android.model.Recommendation
import com.serj.recommend.android.ui.components.loadingIndicators.LargeLoadingIndicator
import com.serj.recommend.android.ui.screens.main.home.components.contentItems.HorizontalCategoryItem
import com.serj.recommend.android.ui.screens.main.home.components.contentItems.SquareCategoryItem

@Composable
fun CategoryScreen(
    modifier: Modifier = Modifier,
    openScreen: (String) -> Unit,
    popUpScreen: () -> Unit,
    viewModel: CategoryViewModel = hiltViewModel()
) {
    val category = viewModel.category
    val categoryItems = viewModel.categoryItems
    val categoryImages = viewModel.categoryImages

    CategoryScreenContent(
        modifier = modifier,
        category = category.value,
        categoryItems = categoryItems,
        categoryImages = categoryImages,
        openScreen = openScreen,
        popUpScreen = popUpScreen,
        onRecommendationClick = viewModel::onRecommendationClick
    )
}

@Composable
fun CategoryScreenContent(
    modifier: Modifier = Modifier,
    category: Category?,
    categoryItems: List<CategoryItem?>?,
    categoryImages: Map<String?, Bitmap?>?,
    openScreen: (String) -> Unit,
    popUpScreen: () -> Unit,
    onRecommendationClick: ((String) -> Unit, Recommendation) -> Unit
) {
    Scaffold(
        modifier = modifier
    ) { paddingValues ->
        if (category != null && categoryItems != null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                ) {
                    Image(
                        modifier = modifier
                            .padding(20.dp)
                            .align(Alignment.CenterStart)
                            .clickable { popUpScreen() },
                        painter = painterResource(id = R.drawable.icon_arrow_back_black),
                        contentDescription = "button_back",
                        contentScale = ContentScale.Crop
                    )

                    Text(
                        modifier = modifier
                            .align(Alignment.Center),
                        text = category.title,
                        color = Color.Black,
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                }

                when (category.coverType) {
                    "square" -> {
                        LazyVerticalGrid(
                            modifier = modifier.fillMaxSize(),
                            columns = GridCells.Fixed(2),
                            contentPadding = PaddingValues(
                                top = 5.dp,
                                bottom = 5.dp
                            ),
                            horizontalArrangement = Arrangement.Center,
                            content = {
                                items(categoryItems.size) { i ->
                                    SquareCategoryItem(
                                        modifier = Modifier.squareRecommendationCategory(),
                                        title = categoryItems[i]?.title,
                                        creator = categoryItems[i]?.creator,
                                        cover = categoryImages?.getOrDefault(categoryItems[i]?.title, null),
                                        recommendationId = categoryItems[i]?.recommendationId,
                                        openScreen = openScreen,
                                        onRecommendationClick = onRecommendationClick
                                    )
                                }
                            }
                        )
                    }
                    "horizontal" -> {
                        LazyColumn(
                            modifier = modifier.fillMaxSize()
                        ) {
                            items(categoryItems) { item ->
                                HorizontalCategoryItem(
                                    modifier = Modifier.horizontalRecommendationCategory(),
                                    title = item?.title,
                                    creator = item?.creator,
                                    cover = categoryImages?.getOrDefault(item?.title, null),
                                    recommendationId = item?.recommendationId,
                                    isInCategoryScreen = true,
                                    openScreen = openScreen,
                                    onRecommendationClick = onRecommendationClick
                                )
                            }
                        }
                    }
                    "vertical" -> {
                        /*
                        VerticalCategoryItem(
                            title = item?.title,
                            creator = item?.creator,
                            cover = categoryImages?.getOrDefault(item?.title, null),
                            recommendationId = item?.recommendationId,
                            openScreen = openScreen,
                            onRecommendationClick = onRecommendationClick
                        )

                         */
                    }
                    else -> {
                        // TODO: what else?
                    }
                }
            }
        } else {
            LargeLoadingIndicator()
        }
    }
}