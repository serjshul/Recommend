package com.serj.recommend.android.ui.screens.main.feed

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.TopAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.serj.recommend.android.R
import com.serj.recommend.android.model.Recommendation
import com.serj.recommend.android.model.items.RecommendationItem
import com.serj.recommend.android.ui.components.loadingIndicators.SmallLoadingIndicator
import com.serj.recommend.android.ui.components.recommendationPreviews.RecommendationItem
import com.serj.recommend.android.ui.styles.White

@Composable
fun FeedScreen(
    openScreen: (String) -> Unit,
    viewModel: FeedViewModel = hiltViewModel()
) {
    val currentRecommendations = viewModel.currentRecommendations
    val currentRecommendationsAmount = viewModel.currentRecommendationsAmount.intValue

    FeedScreenContent(
        currentRecommendations = currentRecommendations,
        recommendationsAmount = currentRecommendationsAmount,
        openScreen = openScreen,
        onRecommendationClick = viewModel::onRecommendationClick
    )
}

@Composable
fun FeedScreenContent(
    modifier: Modifier = Modifier,
    currentRecommendations: List<RecommendationItem>,
    recommendationsAmount: Int,
    openScreen: (String) -> Unit,
    onRecommendationClick: ((String) -> Unit, Recommendation) -> Unit
) {
    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .background(color = White),
        topBar = {
            TopAppBar(
                backgroundColor = White
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp)
                ) {
                    Icon(
                        ImageVector.vectorResource(id = R.drawable.app_logo),
                        modifier = Modifier.align(Alignment.Center),
                        contentDescription = "app logo",
                    )
                }
            }
        }
    ) { paddingValues ->
        var isLoading by remember { mutableStateOf(true) }
        var currentRecommendationsAmount = 0

        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
        ) {
            items(currentRecommendations) {
                RecommendationItem(
                    modifier = Modifier.padding(bottom = 10.dp),
                    user = it.userItem,
                    date = it.date,
                    description = it.description,
                    backgroundImageReference = it.backgroundImageReference,
                    backgroundVideoReference = it.backgroundVideoReference,
                    title = it.title,
                    creator = it.creator,
                    coverType = it.coverType,
                    coverReference = it.coverReference,
                    recommendationId = it.id,
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