package com.serj.recommend.android.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.serj.recommend.android.model.Recommendation
import com.serj.recommend.android.model.items.RecommendationItem
import com.serj.recommend.android.ui.components.loadingIndicators.LargeLoadingIndicator
import com.serj.recommend.android.ui.components.post.RecommendationItemWithBackground
import com.serj.recommend.android.ui.components.post.RecommendationItemWithoutBackground
import com.serj.recommend.android.ui.screens.main.feed.FeedViewModel
import com.serj.recommend.android.ui.styles.LightGray
import com.serj.recommend.android.ui.styles.White


@Composable
fun FeedScreen(
    openScreen: (String) -> Unit,
    viewModel: FeedViewModel = hiltViewModel()
) {
    val currentRecommendations = viewModel.currentRecommendations
    
    FeedScreenContent(
        currentRecommendations = currentRecommendations,
        openScreen = openScreen,
        onRecommendationClick = viewModel::onRecommendationClick
    )
}

@Composable
fun FeedScreenContent(
    modifier: Modifier = Modifier,
    currentRecommendations: List<RecommendationItem>,
    openScreen: (String) -> Unit,
    onRecommendationClick: ((String) -> Unit, Recommendation) -> Unit
) {
    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .background(color = White)
    ) { paddingValues ->
        if (currentRecommendations.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(start = 5.dp, end = 5.dp)
            ) {
                item {
                    Spacer(modifier = Modifier.size(5.dp))
                }

                items(currentRecommendations) {
                    if (it.backgroundImage != null || it.backgroundVideo != null) {
                        RecommendationItemWithBackground(
                            modifier = Modifier.padding(bottom = 5.dp),
                            user = it.user,
                            date = it.date,
                            description = it.description,
                            backgroundImage = it.backgroundImage,
                            title = it.title,
                            creator = it.creator,
                            coverType = it.coverType,
                            cover = it.cover,
                            recommendationId = it.id,
                            openScreen = openScreen,
                            onRecommendationClick = onRecommendationClick
                        )
                    } else {
                        RecommendationItemWithoutBackground(
                            modifier = Modifier.padding(bottom = 5.dp),
                            user = it.user,
                            date = it.date,
                            description = it.description,
                            title = it.title,
                            creator = it.creator,
                            coverType = it.coverType,
                            cover = it.cover,
                            recommendationId = it.id,
                            openScreen = openScreen,
                            onRecommendationClick = onRecommendationClick
                        )
                    }
                }
            }
        } else {
            LargeLoadingIndicator(backgroundColor = LightGray)
        }
    }
}