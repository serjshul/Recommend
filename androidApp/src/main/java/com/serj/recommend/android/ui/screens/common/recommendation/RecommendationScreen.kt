package com.serj.recommend.android.ui.screens.common.recommendation

import android.graphics.Bitmap
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.serj.recommend.android.common.ext.recommendationContentShape
import com.serj.recommend.android.common.ext.recommendationItemsInterval
import com.serj.recommend.android.common.ext.screenPaddingsInner
import com.serj.recommend.android.common.ext.screenPaddingsOuter
import com.serj.recommend.android.model.Recommendation
import com.serj.recommend.android.ui.components.comments.CommentsBottomSheet
import com.serj.recommend.android.ui.components.comments.CommentsList
import com.serj.recommend.android.ui.components.post.InfoPanel
import com.serj.recommend.android.ui.components.post.InteractionPanel
import com.serj.recommend.android.ui.screens.common.recommendation.components.Description
import com.serj.recommend.android.ui.screens.common.recommendation.components.Header
import com.serj.recommend.android.ui.screens.common.recommendation.components.Paragraphs
import com.serj.recommend.android.ui.screens.common.recommendation.components.Quote

@Composable
fun RecommendationScreen(
    modifier: Modifier = Modifier,
    popUpScreen: () -> Unit,
    viewModel: RecommendationViewModel = hiltViewModel()
) {
    val recommendation by viewModel.recommendation
    val backgroundImage = viewModel.backgroundImage.value
    val paragraphsImages = viewModel.paragraphsImages

    RecommendationScreenContent(
        modifier = modifier,
        recommendation = recommendation,
        backgroundImage = backgroundImage,
        paragraphsImages = paragraphsImages,
        popUpScreen = popUpScreen
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecommendationScreenContent(
    modifier: Modifier = Modifier,
    recommendation: Recommendation,
    backgroundImage: Bitmap?,
    paragraphsImages: Map<Int?, Bitmap?>,
    popUpScreen: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }

    Scaffold(
        modifier = modifier
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier.padding(paddingValues)
        ) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Header(
                        modifier = Modifier,
                        title = recommendation.title,
                        type = recommendation.type,
                        creator = recommendation.creator,
                        tags = recommendation.tags,
                        year = recommendation.year,
                        backgroundImage = backgroundImage,
                        backgroundVideo = null,
                        popUpScreen = popUpScreen
                    )

                    Column(
                        modifier = Modifier
                            .recommendationContentShape()
                            .screenPaddingsInner()
                            .screenPaddingsOuter()
                    ) {
                        Description(
                            description = recommendation.description
                        )
                    }
                }
            }

            item {
                Paragraphs(
                    modifier = Modifier.screenPaddingsInner(),
                    paragraphs = recommendation.paragraphs,
                    paragraphsImages = paragraphsImages,
                    color = recommendation.color
                )
            }

            item {
                Quote(
                    modifier = Modifier
                        .recommendationItemsInterval()
                        .screenPaddingsInner(),
                    quote = recommendation.quote,
                    color = recommendation.color
                )
            }

            item {
                InfoPanel(
                    modifier = Modifier
                        .recommendationItemsInterval()
                        .screenPaddingsInner(),
                    author = recommendation.authorId,
                    date = recommendation.date.toLocaleString()
                )
            }

            item {
                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .recommendationItemsInterval()
                        .screenPaddingsInner(),
                    thickness = 1.dp,
                    color = Color.Gray
                )
            }

            item {
                InteractionPanel(
                    modifier = Modifier
                        .recommendationItemsInterval()
                        .screenPaddingsInner(),
                    views = recommendation.viewsCount.toString(),
                    likes = recommendation.likesCount.toString(),
                    comments = recommendation.commentsCount.toString(),
                    reposts = recommendation.repostsCount.toString()
                )
            }

            if (recommendation.comments.isNotEmpty()) {
                item {
                    CommentsList(
                        modifier = Modifier
                            .recommendationItemsInterval()
                            .screenPaddingsInner()
                            .clickable { showBottomSheet = true },
                        comments = recommendation.comments
                    )
                }
            }
        }

        if (showBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = { showBottomSheet = false },
                sheetState = sheetState,
                containerColor = Color.White
            ) {
                CommentsBottomSheet(
                    modifier = Modifier
                        .screenPaddingsInner()
                        .screenPaddingsOuter(),
                    comments = recommendation.comments
                )
            }
        }
    }
}