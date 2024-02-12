package com.serj.recommend.android.ui.screens.common.recommendation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
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
import com.serj.recommend.android.common.ext.toColor
import com.serj.recommend.android.model.Recommendation
import com.serj.recommend.android.model.items.UserItem
import com.serj.recommend.android.services.GetRecommendationResponse
import com.serj.recommend.android.services.GetUserItemResponse
import com.serj.recommend.android.services.model.Response.Failure
import com.serj.recommend.android.services.model.Response.Success
import com.serj.recommend.android.ui.components.interaction.InteractionPanelRecommendation
import com.serj.recommend.android.ui.components.loadingIndicators.LargeLoadingIndicator
import com.serj.recommend.android.ui.screens.common.recommendation.components.Description
import com.serj.recommend.android.ui.screens.common.recommendation.components.Header
import com.serj.recommend.android.ui.screens.common.recommendation.components.HeaderBackground
import com.serj.recommend.android.ui.screens.common.recommendation.components.Paragraphs
import com.serj.recommend.android.ui.screens.common.recommendation.components.Quote
import com.serj.recommend.android.ui.screens.common.recommendation.components.RecommendationTopBar
import java.util.Date

//TODO: when leaving the recommendation with two clicks on the left,
// just a white screen appeared for @serjshul

@Composable
fun RecommendationScreen(
    modifier: Modifier = Modifier,
    popUpScreen: () -> Unit,
    viewModel: RecommendationViewModel = hiltViewModel()
) {
    val recommendationResponse by viewModel.getRecommendationResponse
    val getUserItemResponse by viewModel.getUserItemResponse

    RecommendationScreenContent(
        modifier = modifier,
        getRecommendationResponse = recommendationResponse,
        getUserItemResponse = getUserItemResponse,
        popUpScreen = popUpScreen
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecommendationScreenContent(
    modifier: Modifier = Modifier,
    getRecommendationResponse: GetRecommendationResponse?,
    getUserItemResponse: GetUserItemResponse?,
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
                if (recommendation != null && getUserItemResponse is Success) {
                    if (recommendation.title != null && recommendation.type != null &&
                        recommendation.creator != null && recommendation.tags != null &&
                        recommendation.year != null && recommendation.description != null &&
                        recommendation.date != null) {

                        Box(
                            modifier = Modifier
                                .padding(paddingValues)
                                .fillMaxSize()
                        ) {
                            HeaderBackground(
                                modifier = Modifier
                                    .align(Alignment.TopCenter)
                                    .alpha(80 / currentOffset.toFloat()),
                                color = recommendation.color?.toColor(),
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
                                    val userItem = getUserItemResponse.data
                                    if (userItem != null) {
                                        Header(
                                            modifier = Modifier
                                                .padding(top = 50.dp),
                                            title = recommendation.title,
                                            creator = recommendation.creator,
                                            tags = recommendation.tags,
                                            year = recommendation.year,
                                            photoReference = userItem.photoReference,
                                            nickname = userItem.nickname
                                        )
                                    }
                                }

                                item {
                                    Description(
                                        description = recommendation.description
                                    )
                                }

                                item {
                                    Paragraphs(
                                        paragraphs = recommendation.paragraphs,
                                        paragraphsReferences = recommendation.paragraphsReferences,
                                        color = recommendation.color?.toColor() ?: Color.Black
                                    )
                                }

                                if (recommendation.quote != null) {
                                    item {
                                        Quote(
                                            quote = recommendation.quote,
                                            color = recommendation.color?.toColor()
                                        )
                                    }
                                }

                                item {
                                    InteractionPanelRecommendation(
                                        modifier = Modifier,
                                        color = recommendation.color?.toColor(),
                                        isLiked = false,
                                        isReposted = false,
                                        likedBy = recommendation.likedBy,
                                        repostedBy = recommendation.repostedBy,
                                        comments = arrayListOf(),
                                        views = recommendation.views,
                                        date = recommendation.date,
                                        recommendationId = recommendation.id,
                                        currentUserUid = "",
                                        onLikeClick = { _: Boolean, _: String, _: String -> Success(true) }
                                    )
                                }
                            }

                            RecommendationTopBar(
                                modifier = Modifier
                                    .align(Alignment.TopCenter),
                                type = recommendation.type,
                                color = recommendation.color?.toColor(),
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
                backgroundColor = Color.White
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
            type = "Type",
            creator = "Creator",
            tags = listOf("Tag", "Tag", "Tag", "Tag", "Tag", "Tag"),
            year = 2023,
            description = "Description description description description description description " +
                    "description description description description description description " +
                    "description description description description description description\n" +
                    "Description description description description description description " +
                    "description description description description description description " +
                    "description description description description description description ",
            quote = "Quote quote\nQuote quote\nQuote quote\nQuote quote",
            paragraphs = arrayListOf(
                hashMapOf(
                    "title" to "Paragraph title",
                    "text" to "Text text text text text text text text text text text text text " +
                            "text text text text text text text text text text text text text " +
                            "text text text text text text text text text text text text text\n" +
                            "Text text text text text text text text text text text text text " +
                            "text text text text text text text text text text text text text " +
                            "text text text text text text text text text text text text text"
                ),
                hashMapOf(
                    "title" to "Paragraph title",
                    "text" to "Text text text text text text text text text text text text text " +
                            "text text text text text text text text text text text text text " +
                            "text text text text text text text text text text text text text\n" +
                            "Text text text text text text text text text text text text text " +
                            "text text text text text text text text text text text text text " +
                            "text text text text text text text text text text text text text"
                ),
                hashMapOf(
                    "title" to "Paragraph title",
                    "text" to "Text text text text text text text text text text text text text " +
                            "text text text text text text text text text text text text text " +
                            "text text text text text text text text text text text text text\n" +
                            "Text text text text text text text text text text text text text " +
                            "text text text text text text text text text text text text text " +
                            "text text text text text text text text text text text text text"
                )
            ),
            date = Date(0),
            color = "#ad0f0b",
            likedBy = arrayListOf(),
            repostedBy = arrayListOf(),
            views = 0
        )
    )
    val getUserItemResponse = Success(
        UserItem(
            uid = "userId",
            nickname = "nickname"
        )
    )

    RecommendationScreenContent(
        modifier = Modifier,
        getRecommendationResponse = getRecommendationResponse,
        getUserItemResponse = getUserItemResponse,
        popUpScreen = { }
    )
}