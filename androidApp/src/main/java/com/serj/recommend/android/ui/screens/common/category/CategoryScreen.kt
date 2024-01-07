package com.serj.recommend.android.ui.screens.common.category

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.TopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
import com.serj.recommend.android.model.Recommendation
import com.serj.recommend.android.model.items.RecommendationItem
import com.serj.recommend.android.services.CategoryResponse
import com.serj.recommend.android.services.model.Response.Failure
import com.serj.recommend.android.services.model.Response.Success
import com.serj.recommend.android.ui.components.loadingIndicators.LargeLoadingIndicator
import com.serj.recommend.android.ui.components.loadingIndicators.SmallLoadingIndicator
import com.serj.recommend.android.ui.components.recommendationPreviews.RecommendationItem
import com.serj.recommend.android.ui.styles.White

@Composable
fun CategoryScreen(
    modifier: Modifier = Modifier,
    openScreen: (String) -> Unit,
    popUpScreen: () -> Unit,
    viewModel: CategoryViewModel = hiltViewModel()
) {
    val categoryResponse = viewModel.categoryResponse
    val currentRecommendations = viewModel.currentRecommendations
    val currentRecommendationsAmount = viewModel.currentRecommendationsAmount.intValue

    CategoryScreenContent(
        modifier = modifier,
        categoryResponse = categoryResponse.value,
        currentRecommendations = currentRecommendations,
        recommendationsAmount = currentRecommendationsAmount,
        openScreen = openScreen,
        popUpScreen = popUpScreen,
        onRecommendationClick = viewModel::onRecommendationClick
    )
}

@Composable
fun CategoryScreenContent(
    modifier: Modifier = Modifier,
    categoryResponse: CategoryResponse?,
    currentRecommendations: List<MutableState<RecommendationItem>>,
    recommendationsAmount: Int,
    openScreen: (String) -> Unit,
    popUpScreen: () -> Unit,
    onRecommendationClick: ((String) -> Unit, Recommendation) -> Unit
) {
    when (categoryResponse) {
        is Success -> {
            val category = categoryResponse.data

            Scaffold(
                modifier = modifier,
                topBar = {
                    TopAppBar(
                        backgroundColor = White
                    ) {
                        if (category != null) {
                            category.title?.let {
                                CategoryTitle(
                                    title = it,
                                    popUpScreen = popUpScreen
                                )
                            }
                        }
                    }
                }
            ) { paddingValues ->
                var isLoading by rememberSaveable { mutableStateOf(true) }
                var currentRecommendationsAmount = 0

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    items(currentRecommendations) {
                        RecommendationItem(
                            modifier = Modifier.padding(bottom = 10.dp),
                            user = it.value.userItem,
                            date = it.value.date,
                            description = it.value.description,
                            backgroundImageReference = it.value.backgroundImageReference,
                            backgroundVideoReference = it.value.backgroundVideoReference,
                            title = it.value.title,
                            creator = it.value.creator,
                            coverType = it.value.coverType,
                            coverReference = it.value.coverReference,
                            recommendationId = it.value.id,
                            openScreen = openScreen,
                            onRecommendationClick = onRecommendationClick
                        )

                        currentRecommendationsAmount++
                        isLoading = currentRecommendationsAmount < recommendationsAmount
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
            }
        }
        is Failure -> {
            print(categoryResponse.e)
        }
        else -> {
            LargeLoadingIndicator(
                backgroundColor = White
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