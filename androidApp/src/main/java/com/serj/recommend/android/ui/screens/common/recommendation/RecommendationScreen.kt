package com.serj.recommend.android.ui.screens.common.recommendation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.serj.recommend.android.common.ext.toColor
import com.serj.recommend.android.model.collections.Recommendation
import com.serj.recommend.android.model.collections.User
import com.serj.recommend.android.model.items.UserItem
import com.serj.recommend.android.model.subcollections.Comment
import com.serj.recommend.android.services.model.Response
import com.serj.recommend.android.services.model.Response.Success
import com.serj.recommend.android.ui.components.comments.CommentsBottomSheet
import com.serj.recommend.android.ui.components.interaction.InteractionPanelRecommendation
import com.serj.recommend.android.ui.components.loadingIndicators.LargeLoadingIndicator
import com.serj.recommend.android.ui.screens.common.recommendation.components.Description
import com.serj.recommend.android.ui.screens.common.recommendation.components.Header
import com.serj.recommend.android.ui.screens.common.recommendation.components.HeaderBackground
import com.serj.recommend.android.ui.screens.common.recommendation.components.InsightsBottomSheet
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
    RecommendationScreenContent(
        modifier = modifier,
        loadingStatus = viewModel.loadingStatus.value,
        recommendation = viewModel.recommendation.value,
        userItem = viewModel.userItem.value,
        currentUser = viewModel.currentUser.value,
        isLiked = viewModel.isLiked,
        isCommented = viewModel.isCommented,
        isReposted = viewModel.isReposted,
        commentInput = viewModel.commentInput,
        bottomSheetComments = viewModel.bottomSheetComments,
        showCommentsBottomSheet = viewModel.showCommentsBottomSheet,
        showInsightsBottomSheet = viewModel.showInsightsBottomSheet,
        onInsightsClick = viewModel::onInsightsClick,
        onInsightsSheetDismissRequest = viewModel::onInsightsSheetDismissRequest,
        onLikeClick = viewModel::onLikeClick,
        onCommentClick = viewModel::onCommentClick,
        onRepostClick = viewModel::onRepostClick,
        onCommentItemClick = viewModel::onCommentItemClick,
        onCommentDismissRequest = viewModel::onCommentItemDismissRequest,
        onCommentInputValueChange = viewModel::onCommentInputValueChange,
        onCommentSheetDismissRequest = viewModel::onCommentSheetDismissRequest,
        onUploadCommentClick = viewModel::onUploadCommentClick,
        onDeleteCommentClick = viewModel::onDeleteCommentClick,
        popUpScreen = popUpScreen
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecommendationScreenContent(
    modifier: Modifier = Modifier,
    loadingStatus: Response<Boolean>,
    recommendation: Recommendation?,
    userItem: UserItem?,
    currentUser: User?,
    isLiked: Boolean,
    isCommented: Boolean,
    isReposted: Boolean,
    commentInput: String,
    bottomSheetComments: Map<Comment, Boolean>,
    showCommentsBottomSheet: Boolean,
    showInsightsBottomSheet: Boolean,
    onInsightsClick: () -> Unit,
    onInsightsSheetDismissRequest: () -> Unit,
    onLikeClick: () -> Unit,
    onCommentClick: () -> Unit,
    onRepostClick: () -> Unit,
    onCommentItemClick: (Comment) -> Unit,
    onCommentDismissRequest: (Comment) -> Unit,
    onCommentInputValueChange: (String) -> Unit,
    onCommentSheetDismissRequest: () -> Unit,
    onUploadCommentClick: () -> Unit,
    onDeleteCommentClick: (Comment) -> Unit,
    popUpScreen: () -> Unit
) {
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
    val commentSheetState =
        rememberModalBottomSheetState(
            skipPartiallyExpanded = true
        )
    val insightsSheetState =
        rememberModalBottomSheetState()

    Scaffold(
        modifier = modifier
    ) { paddingValues ->
        when (loadingStatus) {
            is Success -> {
                Box(
                    modifier = Modifier
                        .padding(paddingValues)
                        .fillMaxSize()
                ) {
                    HeaderBackground(
                        modifier = Modifier
                            .align(Alignment.TopCenter)
                            .alpha(200 / currentOffset.toFloat()),
                        color = recommendation!!.color?.toColor(),
                        backgroundImageReference = recommendation.backgroundImageReference,
                        backgroundVideoReference = recommendation.backgroundVideoReference
                    )

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .align(Alignment.TopCenter),
                        state = lazyListState
                    ) {
                        if (userItem != null) {
                            item {
                                Header(
                                    modifier = Modifier
                                        .padding(top = 50.dp),
                                    title = recommendation.title!!,
                                    creator = recommendation.creator!!,
                                    tags = recommendation.tags!!,
                                    year = recommendation.year!!,
                                    photoReference = userItem.photoReference,
                                    nickname = userItem.nickname
                                )
                            }
                        }

                        item {
                            Description(
                                description = recommendation.description!!
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

                        if (currentUser != null) {
                            item {
                                InteractionPanelRecommendation(
                                    modifier = Modifier,
                                    color = recommendation.color?.toColor(),
                                    isLiked = isLiked,
                                    isCommented = isCommented,
                                    isReposted = isReposted,
                                    topLikedComment = recommendation.topLikedComment,
                                    authorUserId = recommendation.uid,
                                    currentUserid = currentUser.uid,
                                    onLikeClick = onLikeClick,
                                    onCommentClick = onCommentClick,
                                    onRepostClick = onRepostClick,
                                    onInsightsClick = onInsightsClick
                                )
                            }
                        }
                    }

                    RecommendationTopBar(
                        modifier = Modifier
                            .align(Alignment.TopCenter),
                        title = recommendation.title!!,
                        type = recommendation.type!!,
                        color = recommendation.color?.toColor(),
                        isBackgroundHidden = isBackgroundHidden,
                        popUpScreen = popUpScreen
                    )

                    if (showInsightsBottomSheet) {
                        ModalBottomSheet(
                            onDismissRequest = { onInsightsSheetDismissRequest() },
                            sheetState = insightsSheetState,
                            containerColor = Color.White,
                            content = {
                                InsightsBottomSheet(
                                    likesAmount = recommendation.likes.size,
                                    commentsAmount = recommendation.comments.size,
                                    repostsAmount = recommendation.reposts.size,
                                    savedAmounts = 0,
                                    viewsAmount = 0,
                                    coverageAmount = 0,
                                    date = recommendation.date!!
                                )
                            }
                        )
                    }
                }

                if (showCommentsBottomSheet) {
                    ModalBottomSheet(
                        onDismissRequest = { onCommentSheetDismissRequest() },
                        sheetState = commentSheetState,
                        containerColor = Color.White,
                        content = {
                            CommentsBottomSheet(
                                modifier = Modifier,
                                comments = bottomSheetComments,
                                commentInput = commentInput,
                                currentUserPhotoReference = currentUser!!.photoReference,
                                onCommentInputValueChange = onCommentInputValueChange,
                                onUploadCommentClick = onUploadCommentClick,
                                onDeleteCommentClick = onDeleteCommentClick,
                                onCommentClick = onCommentItemClick,
                                onCommentDismissRequest = onCommentDismissRequest
                            )
                        }
                    )
                }
            }

            is Response.Failure -> {
                Box(
                    modifier = Modifier
                        .padding(paddingValues)
                        .fillMaxSize()
                        .background(Color.White)
                ) {
                    Text(
                        text = "Couldn't open the recommendation",
                        color = Color.Black,
                        fontSize = 14.sp,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }

            else -> {
                LargeLoadingIndicator(
                    backgroundColor = Color.White
                )
            }
        }
    }
}

@Preview
@Composable
fun RecommendationScreenContentPreview() {
    val recommendation = Recommendation(
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
    val userItem = UserItem(
        uid = "userId",
        nickname = "nickname"
    )

    RecommendationScreenContent(
        modifier = Modifier,
        loadingStatus = Success(true),
        recommendation = recommendation,
        userItem = userItem,
        currentUser = null,
        isLiked = false,
        isCommented = false,
        isReposted = false,
        commentInput = "",
        bottomSheetComments = mapOf(),
        showCommentsBottomSheet = false,
        showInsightsBottomSheet = false,
        onInsightsClick = { },
        onInsightsSheetDismissRequest = { },
        onLikeClick = { },
        onCommentClick = { },
        onRepostClick = { },
        onCommentItemClick = { },
        onCommentDismissRequest = { },
        onCommentInputValueChange = { },
        onCommentSheetDismissRequest = { },
        onUploadCommentClick = { },
        onDeleteCommentClick = { },
        popUpScreen = { }
    )
}