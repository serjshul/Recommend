package com.serj.recommend.android.ui.screens.recommendation

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.serj.recommend.android.model.Recommendation

@Composable
fun RecommendationScreen(
    popUpScreen: () -> Unit,
    viewModel: RecommendationViewModel = hiltViewModel()
) {
    val options by viewModel.options

    val recommendation by viewModel.recommendation

    Log.v(TAG, recommendation.toString())

    RecommendationScreenContent(
        recommendation = recommendation,
        options = options,
        popUpScreen = popUpScreen
    )

    //LaunchedEffect(viewModel) { viewModel.loadArticleOptions() }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecommendationScreenContent(
    modifier: Modifier = Modifier,
    recommendation: Recommendation,
    options: List<String>,
    popUpScreen: () -> Unit,
) {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }

    Scaffold() {paddingValues ->
        LazyColumn(
            modifier = modifier.padding(paddingValues)
        ) {
            item {
                Box(
                    modifier = modifier
                        .fillMaxWidth()
                ) {
                    Header(
                        modifier = modifier,
                        title = recommendation.title,
                        type = recommendation.type,
                        creator = recommendation.creator,
                        tags = recommendation.tags,
                        year = recommendation.year,
                        popUpScreen = popUpScreen
                    )
                    Column(
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(top = 280.dp)
                            .background(Color.White, RoundedCornerShape(20.dp))
                    ) {
                        Description(
                            modifier = modifier,
                            description = recommendation.description
                        )
                        for (paragraph in recommendation.paragraphs) {
                            Paragraph(
                                modifier = modifier,
                                title = paragraph["title"] ?: "",
                                image = paragraph["image"],
                                video = paragraph["video"],
                                text = paragraph["text"] ?: "",
                                color = recommendation.color
                            )
                        }
                        Quote(
                            modifier = modifier,
                            quote = recommendation.quote,
                            color = recommendation.color
                        )
                        Footer(
                            modifier = modifier,
                            author = recommendation.authorId,
                            date = recommendation.date,
                            viewsCount = recommendation.viewsCount,
                            likesCount = recommendation.likesCount,
                            commentsCount = recommendation.commentsCount,
                            repostsCount = recommendation.repostsCount,
                            comments = recommendation.comments,
                            onCommentsClick = {
                                showBottomSheet = true
                            }
                        )
                    }
                }
            }
        }

        if (showBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = {
                    showBottomSheet = false
                },
                sheetState = sheetState,
                containerColor = Color.White
            ) {
                Column (
                    modifier = modifier.padding(start = 15.dp, end = 15.dp, bottom = 20.dp)
                ) {
                    for (i in 0..2) {
                        CommentItem(
                            modifier = modifier,
                        )
                    }
                }
            }
        }
    }
}