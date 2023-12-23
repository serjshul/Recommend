package com.serj.recommend.android.ui.screens.common.category

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
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
import com.serj.recommend.android.common.ext.itemsInterval
import com.serj.recommend.android.common.ext.screenPaddingsInner
import com.serj.recommend.android.model.Category
import com.serj.recommend.android.model.CategoryItem
import com.serj.recommend.android.model.Recommendation
import com.serj.recommend.android.ui.COVER_HORIZONTAL
import com.serj.recommend.android.ui.COVER_SQUARE
import com.serj.recommend.android.ui.COVER_VERTICAL
import com.serj.recommend.android.ui.components.items.cards.HorizontalItemCard
import com.serj.recommend.android.ui.components.items.cards.SquareItemCard
import com.serj.recommend.android.ui.components.items.cards.VerticalItemCard
import com.serj.recommend.android.ui.components.loadingIndicators.LargeLoadingIndicator
import com.serj.recommend.android.ui.components.snackbar.SnackbarManager
import com.serj.recommend.android.ui.styles.LightGray

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
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    CategoryTitle(
                        title = category.title,
                        popUpScreen = popUpScreen
                    )
                }

                items(categoryItems) {item ->
                    when (category.coverType) {
                        COVER_SQUARE -> {
                            SquareItemCard(
                                modifier = Modifier
                                    .itemsInterval()
                                    .screenPaddingsInner(),
                                recommendationId = item?.recommendationId,
                                title = item?.title,
                                creator = item?.creator,
                                description = item?.description,
                                cover = categoryImages?.getOrDefault(item?.title, null),
                                openScreen = openScreen,
                                onRecommendationClick = onRecommendationClick
                            )
                        }
                        COVER_HORIZONTAL -> {
                            HorizontalItemCard(
                                modifier = Modifier
                                    .itemsInterval()
                                    .screenPaddingsInner(),
                                recommendationId = item?.recommendationId,
                                title = item?.title,
                                creator = item?.creator,
                                description = item?.description,
                                cover = categoryImages?.getOrDefault(item?.title, null),
                                openScreen = openScreen,
                                onRecommendationClick = onRecommendationClick
                            )
                        }
                        COVER_VERTICAL -> {
                            VerticalItemCard(
                                modifier = Modifier
                                    .itemsInterval()
                                    .screenPaddingsInner(),
                                recommendationId = item?.recommendationId,
                                title = item?.title,
                                creator = item?.creator,
                                description = item?.description,
                                cover = categoryImages?.getOrDefault(item?.title, null),
                                openScreen = openScreen,
                                onRecommendationClick = onRecommendationClick
                            )
                        }
                        else -> {
                            SnackbarManager.showMessage(R.string.error_cover_type)
                        }
                    }
                }
            }
        } else {
            LargeLoadingIndicator(
                backgroundColor = LightGray
            )
        }
    }
}

@Composable
fun CategoryTitle(
    modifier: Modifier = Modifier,
    title: String,
    popUpScreen: () -> Unit,
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
            text = title,
            color = Color.Black,
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
    }
}