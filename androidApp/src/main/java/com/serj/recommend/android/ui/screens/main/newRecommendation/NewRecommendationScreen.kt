package com.serj.recommend.android.ui.screens.main.newRecommendation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.serj.recommend.android.ui.screens.main.newRecommendation.components.NewRecommendationTopBar

@Composable
fun NewRecommendationScreen(
    viewModel: NewRecommendationViewModel = hiltViewModel()
) {
    NewRecommendationScreenContent(
        title = viewModel.title,
        type = viewModel.type,
        creator = viewModel.creator,
        tags = viewModel.tags,
        year = viewModel.year
    )
}

@Composable
fun NewRecommendationScreenContent(
    modifier: Modifier = Modifier,
    title: String,
    type: String,
    creator: String,
    tags: List<String>,
    year: Int,
) {
    Scaffold(
        topBar = {
            NewRecommendationTopBar(isValid = false)
        },
        containerColor = Color.White,
        modifier = modifier.fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(it)
        ) {

        }
    }
}

@Preview
@Composable
fun NewRecommendationScreenContentPreview() {
    NewRecommendationScreenContent(
        title = "The White Lotus",
        type = "Series",
        creator = "Mike White",
        tags = listOf("Comedy", "Drama"),
        year = 2021
    )
}