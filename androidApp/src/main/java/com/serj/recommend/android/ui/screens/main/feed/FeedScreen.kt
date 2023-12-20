package com.serj.recommend.android.ui.screens

import android.graphics.Bitmap
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.serj.recommend.android.model.Post
import com.serj.recommend.android.model.Recommendation
import com.serj.recommend.android.model.RecommendationItem
import com.serj.recommend.android.model.UserItem
import com.serj.recommend.android.ui.components.post.PostItem
import com.serj.recommend.android.ui.screens.main.feed.FeedViewModel


@Composable
fun FeedScreen(
    openScreen: (String) -> Unit,
    viewModel: FeedViewModel = hiltViewModel()
) {
    val posts = viewModel.posts
    val users = viewModel.users
    val postsRecommendations = viewModel.postsRecommendations
    val usersPhotos = viewModel.usersPhotos
    val postsImages = viewModel.postsImages

    FeedScreenContent(
        posts = posts,
        users = users,
        postsRecommendation = postsRecommendations,
        usersPhotos = usersPhotos,
        postsPhotos = postsImages,
        openScreen = openScreen,
        onRecommendationClick = viewModel::onRecommendationClick
    )
}

@Composable
fun FeedScreenContent(
    modifier: Modifier = Modifier,
    posts: List<Post?>?,
    users: Map<String?, UserItem?>?,
    postsRecommendation: Map<String?, RecommendationItem?>?,
    usersPhotos: Map<String?, Bitmap?>?,
    postsPhotos: Map<String?, Bitmap?>?,
    openScreen: (String) -> Unit,
    onRecommendationClick: ((String) -> Unit, Recommendation) -> Unit
) {
    Scaffold { paddingValues ->
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(color = Color.White)
        ) {
            if (posts != null) {
                items(posts) {
                    if (it != null && users?.get(it.uid) != null) {
                        PostItem(
                            modifier = Modifier.padding(bottom = 2.dp),
                            name = users[it.uid]!!.name,
                            nickname = users[it.uid]!!.nickname,
                            date = it.date.toString(),
                            userPhoto = usersPhotos?.getOrDefault(it.uid, null),
                            text = it.text,
                            background = postsPhotos?.getOrDefault(it.id, null),
                            title = postsRecommendation?.get(it.id)?.title,
                            creator = postsRecommendation?.get(it.id)?.creator,
                            likesCounter = it.liked?.size ?: 0,
                            commentsCounter = it.comments?.size ?: 0,
                            repostsCounter = it.reposts?.size ?: 0,
                            viewsCounter = it.views,
                            recommendationId = it.recommendationId,
                            openScreen = openScreen,
                            onRecommendationClick = onRecommendationClick
                        )
                    }
                }
            }
        }
    }
}