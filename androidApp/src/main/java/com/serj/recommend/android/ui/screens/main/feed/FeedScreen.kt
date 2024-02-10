package com.serj.recommend.android.ui.screens.main.feed

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
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
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

@Composable
fun FeedScreen(
    openScreen: (String) -> Unit,
    viewModel: FeedViewModel = hiltViewModel()
) {
    FeedScreenContent(
        currentUser = viewModel.currentUser.value,
        currentRecommendations = viewModel.currentRecommendations,
        recommendationsAmount = viewModel.currentRecommendationsAmount.intValue,
        bottomSheetComments = viewModel.bottomSheetComments,
        showCommentsBottomSheet = viewModel.showCommentsBottomSheet,
        commentInput = viewModel.commentInput,
        openScreen = openScreen,
        onCommentInputValueChange = viewModel::onCommentInputValueChange,
        onCommentSheetDismissRequest = viewModel::onCommentSheetDismissRequest,
        onUploadCommentClick = viewModel::onUploadCommentClick,
        onDeleteCommentClick = viewModel::onDeleteCommentClick,
        onLikeClick = viewModel::onLikeClick,
        onCommentIconClick = viewModel::onCommentIconClick,
        onCommentClick = viewModel::onCommentClick,
        onCommentDismissRequest = viewModel::onCommentDismissRequest,
        onRecommendationClick = viewModel::onRecommendationClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedScreenContent(
    modifier: Modifier = Modifier,
    currentUser: User?,
    currentRecommendations: List<RecommendationItem>,
    recommendationsAmount: Int,
    bottomSheetComments: Map<Comment, Boolean>,
    showCommentsBottomSheet: Boolean,
    commentInput: String,
    openScreen: (String) -> Unit,
    onCommentInputValueChange: (String) -> Unit,
    onCommentSheetDismissRequest: () -> Unit,
    onUploadCommentClick: () -> Unit,
    onDeleteCommentClick: (Comment) -> Unit,
    onLikeClick: (Boolean, String, String) -> Response<Boolean>,
    onCommentIconClick: (String, List<Comment>) -> Unit,
    onCommentClick: (Comment) -> Unit,
    onCommentDismissRequest: (Comment) -> Unit,
    onRecommendationClick: ((String) -> Unit, Recommendation) -> Unit,
) {
    var isLoading by remember { mutableStateOf(true) }
    var currentRecommendationsAmount = 0
    val commentSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    Scaffold(
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
        },
        containerColor = White,
        modifier = modifier.fillMaxSize(),
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
        ) {
            items(currentRecommendations) {
                if (currentUser != null) {
                    RecommendationItem(
                        user = it.userItem,
                        date = it.date,
                        description = it.description,
                        backgroundImageReference = it.backgroundImageReference,
                        backgroundVideoReference = it.backgroundVideoReference,
                        title = it.title,
                        creator = it.creator,
                        type = it.type,
                        tags = it.tags,
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
                containerColor = White,
                content = {
                    CommentsBottomSheet(
                        modifier = Modifier,
                        comments = bottomSheetComments,
                        commentInput = commentInput,
                        currentUserPhotoReference = currentUser!!.photoReference,
                        onCommentInputValueChange = onCommentInputValueChange,
                        onUploadCommentClick = onUploadCommentClick,
                        onDeleteCommentClick = onDeleteCommentClick,
                        onCommentClick = onCommentClick,
                        onCommentDismissRequest = onCommentDismissRequest
                    )
                }
            )
        }
    }
}

@Preview
@Composable
fun FeedScreenContentPreview() {
    FeedScreenContent(
        currentUser = User(),
        currentRecommendations = listOf(),
        recommendationsAmount = 3,
        bottomSheetComments = mapOf(),
        showCommentsBottomSheet = false,
        commentInput = "",
        openScreen = { },
        onCommentInputValueChange = { },
        onCommentSheetDismissRequest = { /*TODO*/ },
        onUploadCommentClick = { },
        onDeleteCommentClick = { },
        onLikeClick = { _: Boolean, _: String, _: String -> Response.Success(true) },
        onCommentIconClick = { _: String, _: List<Comment> -> },
        onCommentClick = { _: Comment -> },
        onCommentDismissRequest = { },
        onRecommendationClick = { _: (String) -> Unit, _: Recommendation -> },
    )
}