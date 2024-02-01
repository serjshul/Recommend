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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.serj.recommend.android.R
import com.serj.recommend.android.model.Comment
import com.serj.recommend.android.model.Recommendation
import com.serj.recommend.android.model.User
import com.serj.recommend.android.model.items.RecommendationItem
import com.serj.recommend.android.services.model.Response
import com.serj.recommend.android.ui.components.comments.CommentsBottomSheet
import com.serj.recommend.android.ui.components.loadingIndicators.SmallLoadingIndicator
import com.serj.recommend.android.ui.components.recommendationPreviews.RecommendationItem
import com.serj.recommend.android.ui.styles.White

@Composable
fun FeedScreen(
    openScreen: (String) -> Unit,
    viewModel: FeedViewModel = hiltViewModel()
) {
    FeedScreenContent(
        currentUser = viewModel.currentUser.value,
        offset = viewModel.offset,
        currentRecommendations = viewModel.currentRecommendations,
        recommendationsAmount = viewModel.currentRecommendationsAmount.intValue,
        bottomSheetComments = viewModel.bottomSheetComments,
        showCommentsBottomSheet = viewModel.showCommentsBottomSheet,
        commentInput = viewModel.commentInput,
        isDropdownMenuExpanded = viewModel.isDropdownMenuExpanded.value,
        currentRecommendationId = viewModel.currentRecommendationId,
        openScreen = openScreen,
        onCommentInputValueChange = viewModel::onCommentInputValueChange,
        onCommentSheetDismissRequest = viewModel::onCommentSheetDismissRequest,
        onUploadCommentClick = viewModel::onUploadCommentClick,
        onLikeClick = viewModel::onLikeClick,
        onCommentIconClick = viewModel::onCommentIconClick,
        onCommentClick = viewModel::onCommentClick,
        onCommentDismissRequest = viewModel::onCommentDismissRequest,
        onRecommendationClick = viewModel::onRecommendationClick,
        onOffsetChangeValue = viewModel::onOffsetChangeValue
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedScreenContent(
    modifier: Modifier = Modifier,
    offset: DpOffset,
    currentUser: User?,
    currentRecommendations: List<RecommendationItem>,
    recommendationsAmount: Int,
    bottomSheetComments: List<Comment>,
    showCommentsBottomSheet: Boolean,
    commentInput: String,
    isDropdownMenuExpanded: Boolean,
    currentRecommendationId: String,
    openScreen: (String) -> Unit,
    onCommentInputValueChange: (String) -> Unit,
    onCommentSheetDismissRequest: () -> Unit,
    onUploadCommentClick: (String) -> Unit,
    onLikeClick: (Boolean, String, String) -> Response<Boolean>,
    onCommentIconClick: (String, List<Comment>) -> Unit,
    onCommentClick: () -> Unit,
    onCommentDismissRequest: () -> Unit,
    onRecommendationClick: ((String) -> Unit, Recommendation) -> Unit,
    onOffsetChangeValue: (Dp, Dp) -> Unit
) {
    var isLoading by remember { mutableStateOf(true) }
    var currentRecommendationsAmount = 0
    val commentSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

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
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
        ) {
            items(currentRecommendations) {
                if (currentUser != null) {
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
                        comments = it.comments,
                        isLiked = it.isLiked,
                        recommendationId = it.id,
                        currentUserUid = currentUser.uid,
                        openScreen = openScreen,
                        onLikeClick = onLikeClick,
                        onCommentIconClick = onCommentIconClick,
                        onRecommendationClick = onRecommendationClick
                    )
                }
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

        if (showCommentsBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = { onCommentSheetDismissRequest() },
                sheetState = commentSheetState,
                content = {
                    CommentsBottomSheet(
                        modifier = Modifier,
                        offset = offset,
                        comments = bottomSheetComments,
                        commentInput = commentInput,
                        currentUserPhotoReference = currentUser!!.photoReference,
                        currentRecommendationId = currentRecommendationId,
                        isDropdownMenuExpanded = isDropdownMenuExpanded,
                        onCommentInputValueChange = onCommentInputValueChange,
                        onUploadCommentClick = onUploadCommentClick,
                        onCommentClick = onCommentClick,
                        onOffsetChangeValue = onOffsetChangeValue,
                        onCommentDismissRequest = onCommentDismissRequest
                    )
                },
                containerColor = Color.White
            )
        }
    }
}

@Preview
@Composable
fun FeedScreenContentPreview() {
    FeedScreenContent(
        currentUser = User(),
        offset = DpOffset.Zero,
        currentRecommendations = listOf(),
        recommendationsAmount = 3,
        bottomSheetComments = listOf(),
        showCommentsBottomSheet = false,
        commentInput = "",
        isDropdownMenuExpanded = false,
        currentRecommendationId = "",
        openScreen = { },
        onCommentInputValueChange = { },
        onCommentSheetDismissRequest = { /*TODO*/ },
        onUploadCommentClick = { },
        onLikeClick = { _: Boolean, _: String, _: String -> Response.Success(true) },
        onCommentIconClick = { _: String, _: List<Comment> -> },
        onCommentClick = { },
        onCommentDismissRequest = { },
        onRecommendationClick = { _: (String) -> Unit, _: Recommendation -> },
        onOffsetChangeValue = { _: Dp, _: Dp -> }
    )
}