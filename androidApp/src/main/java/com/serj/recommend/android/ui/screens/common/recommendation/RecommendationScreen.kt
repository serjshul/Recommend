package com.serj.recommend.android.ui.screens.common.recommendation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.serj.recommend.android.common.ext.itemsInterval
import com.serj.recommend.android.common.ext.screenPaddingsInner
import com.serj.recommend.android.common.ext.toColor
import com.serj.recommend.android.model.Recommendation
import com.serj.recommend.android.services.GetRecommendationResponse
import com.serj.recommend.android.services.model.Response.Failure
import com.serj.recommend.android.services.model.Response.Success
import com.serj.recommend.android.ui.components.interaction.InteractionPanel
import com.serj.recommend.android.ui.components.loadingIndicators.LargeLoadingIndicator
import com.serj.recommend.android.ui.screens.common.recommendation.components.Description
import com.serj.recommend.android.ui.screens.common.recommendation.components.Header
import com.serj.recommend.android.ui.screens.common.recommendation.components.HeaderBackground
import com.serj.recommend.android.ui.screens.common.recommendation.components.InfoPanel
import com.serj.recommend.android.ui.screens.common.recommendation.components.Paragraphs
import com.serj.recommend.android.ui.screens.common.recommendation.components.Quote
import com.serj.recommend.android.ui.screens.common.recommendation.components.RecommendationTopBar
import com.serj.recommend.android.ui.styles.Black
import com.serj.recommend.android.ui.styles.TexasHeatwave
import com.serj.recommend.android.ui.styles.White
import java.util.Date

@Composable
fun RecommendationScreen(
    modifier: Modifier = Modifier,
    popUpScreen: () -> Unit,
    viewModel: RecommendationViewModel = hiltViewModel()
) {
    val recommendationResponse by viewModel.getRecommendationResponse

    RecommendationScreenContent(
        modifier = modifier,
        getRecommendationResponse = recommendationResponse,
        popUpScreen = popUpScreen
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecommendationScreenContent(
    modifier: Modifier = Modifier,
    getRecommendationResponse: GetRecommendationResponse?,
    popUpScreen: () -> Unit
) {
    when (getRecommendationResponse) {
        is Success -> {
            val recommendation = getRecommendationResponse.data
            val sheetState = rememberModalBottomSheetState()
            var showBottomSheet by remember {
                mutableStateOf(false)
            }

            val lazyListState: LazyListState = rememberLazyListState()
            val currentOffset by remember {
                derivedStateOf {
                    lazyListState.firstVisibleItemScrollOffset
                }
            }
            val isBackgroundHidden by remember {
                derivedStateOf {
                    lazyListState.firstVisibleItemIndex > 0
                }
            }

            Scaffold(
                modifier = modifier
            ) { paddingValues ->
                if (recommendation != null) {
                    if (recommendation.title != null && recommendation.type != null &&
                        recommendation.creator != null && recommendation.tags != null &&
                        recommendation.year != null && recommendation.description != null &&
                        recommendation.quote != null && recommendation.date != null) {

                        Box(
                            modifier = Modifier
                                .padding(paddingValues)
                                .fillMaxSize()
                        ) {
                            HeaderBackground(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .align(Alignment.TopCenter)
                                    .alpha(150 / currentOffset.toFloat()),
                                backgroundImageReference = recommendation.backgroundImageReference,
                                backgroundVideoReference = recommendation.backgroundVideoReference
                            )

                            LazyColumn(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .align(Alignment.TopCenter),
                                state = lazyListState
                            ) {
                                item {
                                    Header(
                                        modifier = Modifier.padding(top = 200.dp),
                                        title = recommendation.title,
                                        creator = recommendation.creator,
                                        tags = recommendation.tags,
                                        year = recommendation.year
                                    )
                                }

                                item {
                                    Description(
                                        description = recommendation.description
                                    )
                                }

                                item {
                                    Paragraphs(
                                        modifier = Modifier,
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
                                            .screenPaddingsInner(),
                                        isLiked = false,
                                        recommendationId = recommendation.id,
                                        currentUserUid = "",
                                        onLikeClick = { _: Boolean, _: String, _: String -> Success(true) }
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

                            RecommendationTopBar(
                                modifier = Modifier
                                    .align(Alignment.TopCenter),
                                type = recommendation.type,
                                isBackgroundHidden = isBackgroundHidden,
                                popUpScreen = popUpScreen
                            )
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
                    }
                }
            }
        }
        is Failure -> {
            print(getRecommendationResponse.e)
        }
        else -> {
            LargeLoadingIndicator(
                backgroundColor = White
            )
        }
    }
}

@Preview
@Composable
fun RecommendationScreenContentPreview() {
    val getRecommendationResponse = Success(
        Recommendation(
            id = "recommendationId",
            uid = "userId",
            title = "Title",
            type = "Preview",
            creator = "Preview",
            tags = listOf("Preview", "Preview", "Preview"),
            year = 2023,
            description = "Preview preview preview preview preview preview preview preview " +
                    "preview preview preview preview preview preview preview preview preview",
            quote = "Preview preview\nPreview preview\nPreview preview\nPreview preview",
            paragraphs = arrayListOf(
                hashMapOf(
                    "title" to "Preview",
                    "text" to "Preview preview preview preview preview preview preview preview " +
                            "preview preview preview preview preview preview preview preview" +
                            "preview preview preview preview preview preview preview preview" +
                            "preview preview preview preview preview preview preview preview"
                ),
                hashMapOf(
                    "title" to "Preview",
                    "text" to "Preview preview preview preview preview preview preview preview " +
                            "preview preview preview preview preview preview preview preview" +
                            "preview preview preview preview preview preview preview preview" +
                            "preview preview preview preview preview preview preview preview"
                ),
                hashMapOf(
                    "title" to "Preview",
                    "text" to "Preview preview preview preview preview preview preview preview " +
                            "preview preview preview preview preview preview preview preview" +
                            "preview preview preview preview preview preview preview preview" +
                            "preview preview preview preview preview preview preview preview"
                )
            ),
            date = Date(0),
            color = "#ad0f0b",
            likedBy = arrayListOf(),
            commentedBy = arrayListOf(),
            repostedBy = arrayListOf(),
            views = 0
        )
    )

    RecommendationScreenContent(
        modifier = Modifier,
        getRecommendationResponse = getRecommendationResponse,
        popUpScreen = { }
    )
}