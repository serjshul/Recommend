package com.serj.recommend.android.ui.screens.common.recommendation

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
import com.serj.recommend.android.common.ext.itemsInterval
import com.serj.recommend.android.common.ext.recommendationContentShape
import com.serj.recommend.android.common.ext.screenPaddingsInner
import com.serj.recommend.android.common.ext.screenPaddingsOuter
import com.serj.recommend.android.common.ext.toColor
import com.serj.recommend.android.model.Recommendation
import com.serj.recommend.android.ui.components.loadingIndicators.LargeLoadingIndicator
import com.serj.recommend.android.ui.components.social.InfoPanel
import com.serj.recommend.android.ui.components.social.InteractionPanel
import com.serj.recommend.android.ui.screens.common.recommendation.components.Description
import com.serj.recommend.android.ui.screens.common.recommendation.components.Header
import com.serj.recommend.android.ui.screens.common.recommendation.components.Paragraphs
import com.serj.recommend.android.ui.screens.common.recommendation.components.Quote
import com.serj.recommend.android.ui.styles.Black
import com.serj.recommend.android.ui.styles.TexasHeatwave
import com.serj.recommend.android.ui.styles.White

@Composable
fun RecommendationScreen(
    modifier: Modifier = Modifier,
    popUpScreen: () -> Unit,
    viewModel: RecommendationViewModel = hiltViewModel()
) {
    val recommendation by viewModel.recommendation

    RecommendationScreenContent(
        modifier = modifier,
        recommendation = recommendation,
        popUpScreen = popUpScreen
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecommendationScreenContent(
    modifier: Modifier = Modifier,
    recommendation: Recommendation,
    popUpScreen: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }

    Scaffold(
        modifier = modifier
    ) { paddingValues ->
        if (recommendation.title != null && recommendation.type != null &&
            recommendation.creator != null && recommendation.tags != null &&
            recommendation.year != null && recommendation.description != null &&
            recommendation.quote != null && recommendation.date != null) {
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
                            backgroundImageReference = recommendation.backgroundImageReference,
                            backgroundVideoReference = recommendation.backgroundVideoReference,
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
                        paragraphsReferences = recommendation.paragraphsReferences,
                        color = recommendation.color?.toColor() ?: Black
                    )
                }

                item {
                    Quote(
                        modifier = Modifier
                            .itemsInterval()
                            .screenPaddingsInner(),
                        quote = recommendation.quote,
                        color = recommendation.color?.toColor() ?: TexasHeatwave
                    )
                }

                item {
                    InfoPanel(
                        modifier = Modifier
                            .itemsInterval()
                            .screenPaddingsInner(),
                        author = recommendation.uid ?: "",
                        date = recommendation.date.toLocaleString()
                    )
                }

                item {
                    Divider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .itemsInterval()
                            .screenPaddingsInner(),
                        thickness = 1.dp,
                        color = Color.Gray
                    )
                }

                item {
                    InteractionPanel(
                        modifier = Modifier
                            .itemsInterval()
                            .screenPaddingsInner()
                    )
                }

                if (recommendation.commentedBy.isNotEmpty()) {
                    item {
                        /*
                        CommentsList(
                            modifier = Modifier
                                .itemsInterval()
                                .screenPaddingsInner()
                                .clickable { showBottomSheet = true },
                            comments = recommendation.comments
                        )

                         */
                    }
                }
            }

            if (showBottomSheet) {
                ModalBottomSheet(
                    onDismissRequest = { showBottomSheet = false },
                    sheetState = sheetState,
                    containerColor = Color.White
                ) {
                    /*
                    CommentsBottomSheet(
                        modifier = Modifier
                            .screenPaddingsInner()
                            .screenPaddingsOuter(),
                        comments = recommendation.comments
                    )

                     */
                }
            }
        } else {
            LargeLoadingIndicator(backgroundColor = White)
        }
    }
}