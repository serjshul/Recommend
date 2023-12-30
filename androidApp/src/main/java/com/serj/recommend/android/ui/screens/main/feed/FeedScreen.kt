package com.serj.recommend.android.ui.screens.main.feed

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.serj.recommend.android.model.Recommendation
import com.serj.recommend.android.model.items.RecommendationItem
import com.serj.recommend.android.ui.components.loadingIndicators.LargeLoadingIndicator
import com.serj.recommend.android.ui.components.loadingIndicators.SmallLoadingIndicator
import com.serj.recommend.android.ui.components.post.RecommendationItemWithBackground
import com.serj.recommend.android.ui.components.post.RecommendationItemWithoutBackground
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
    currentRecommendations: List<MutableState<RecommendationItem>>,
    recommendationsAmount: Int,
    openScreen: (String) -> Unit,
    onRecommendationClick: ((String) -> Unit, Recommendation) -> Unit
) {
    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .background(color = White)
    ) { paddingValues ->
        if (currentRecommendations.isNotEmpty()) {
            var isLoading by rememberSaveable { mutableStateOf(true) }
            var currentRecommendationsAmount = 0

            LazyColumn(
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(start = 5.dp, end = 5.dp)
            ) {
                item {
                    Spacer(modifier = Modifier.size(5.dp))
                }

                items(currentRecommendations) {
                    val recommendationItem = it.value

                    if (recommendationItem.backgroundImage.value != null ||
                        recommendationItem.backgroundVideo != null) {
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

                item {
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
            LargeLoadingIndicator(backgroundColor = White)
        }
    }
}