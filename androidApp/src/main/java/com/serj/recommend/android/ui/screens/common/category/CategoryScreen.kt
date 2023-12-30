package com.serj.recommend.android.ui.screens.common.category

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
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
import com.serj.recommend.android.common.ext.screenPaddingsInner
import com.serj.recommend.android.common.ext.screenPaddingsOuter
import com.serj.recommend.android.model.Category
import com.serj.recommend.android.model.Recommendation
import com.serj.recommend.android.model.items.RecommendationItem
import com.serj.recommend.android.ui.components.loadingIndicators.LargeLoadingIndicator
import com.serj.recommend.android.ui.components.loadingIndicators.SmallLoadingIndicator
import com.serj.recommend.android.ui.components.post.RecommendationItemWithBackground
import com.serj.recommend.android.ui.components.post.RecommendationItemWithoutBackground
import com.serj.recommend.android.ui.styles.LightGray
import com.serj.recommend.android.ui.styles.White

@Composable
fun CategoryScreen(
    modifier: Modifier = Modifier,
    openScreen: (String) -> Unit,
    popUpScreen: () -> Unit,
    viewModel: CategoryViewModel = hiltViewModel()
) {
    val category = viewModel.category
    val currentRecommendations = viewModel.currentRecommendations
    val currentRecommendationsAmount = viewModel.currentRecommendationsAmount.intValue

    CategoryScreenContent(
        modifier = modifier,
        category = category.value,
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
    category: Category?,
    currentRecommendations: List<MutableState<RecommendationItem>>,
    recommendationsAmount: Int,
    openScreen: (String) -> Unit,
    popUpScreen: () -> Unit,
    onRecommendationClick: ((String) -> Unit, Recommendation) -> Unit
) {
    Scaffold(
        modifier = modifier
    ) { paddingValues ->
        if (category != null) {
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

                if (currentRecommendations.isNotEmpty()) {
                    item {
                        var isLoading by rememberSaveable { mutableStateOf(true) }
                        var currentRecommendationsAmount = 0

                        Column(
                            modifier = Modifier
                                .screenPaddingsInner()
                                .screenPaddingsOuter()
                        ) {
                            for (it in currentRecommendations) {
                                val recommendationItem = it.value

                                if (recommendationItem.backgroundImage.value != null ||
                                    recommendationItem.backgroundVideo != null
                                ) {
                                    RecommendationItemWithBackground(
                                        modifier = Modifier.padding(bottom = 15.dp),
                                        user = recommendationItem.user,
                                        date = recommendationItem.date,
                                        description = recommendationItem.description,
                                        backgroundImage = recommendationItem.backgroundImage.value,
                                        title = recommendationItem.title,
                                        creator = recommendationItem.creator,
                                        coverType = recommendationItem.coverType,
                                        cover = recommendationItem.cover.value,
                                        recommendationId = recommendationItem.id,
                                        openScreen = openScreen,
                                        onRecommendationClick = onRecommendationClick
                                    )
                                } else {
                                    RecommendationItemWithoutBackground(
                                        modifier = Modifier.padding(bottom = 15.dp),
                                        user = recommendationItem.user,
                                        date = recommendationItem.date,
                                        description = recommendationItem.description,
                                        title = recommendationItem.title,
                                        creator = recommendationItem.creator,
                                        coverType = recommendationItem.coverType,
                                        cover = recommendationItem.cover.value,
                                        recommendationId = recommendationItem.id,
                                        openScreen = openScreen,
                                        onRecommendationClick = onRecommendationClick
                                    )
                                }

                                currentRecommendationsAmount++
                                isLoading = currentRecommendationsAmount < recommendationsAmount
                            }

                            if (isLoading) {
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
                    item {
                        SmallLoadingIndicator(
                            modifier = Modifier.size(300.dp),
                            backgroundColor = White
                        )
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