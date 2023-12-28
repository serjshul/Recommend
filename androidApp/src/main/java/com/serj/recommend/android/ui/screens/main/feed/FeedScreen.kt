package com.serj.recommend.android.ui.screens

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
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

    /*
    FeedScreenContent(
        posts = posts,
        users = users,
        postsRecommendation = postsRecommendations,
        usersPhotos = usersPhotos,
        postsPhotos = postsImages,
        openScreen = openScreen,
        onRecommendationClick = viewModel::onRecommendationClick
    )

     */
}

/*
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
    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) { paddingValues ->
        if (!posts.isNullOrEmpty()) {
            LazyColumn(
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(start = 5.dp, end = 5.dp)
            ) {
                item {
                    Spacer(modifier = Modifier.size(5.dp))
                }

                items(posts) {
                    if (it != null) {
                        if (postsPhotos?.getOrDefault(it.id, null) != null) {

                        } else {
                            PostWithBackground(
                                modifier = Modifier.padding(bottom = 5.dp),
                                nickname = users?.get(it.uid)?.nickname,
                                date = it.date.toString(),
                                userPhoto = usersPhotos?.getOrDefault(it.uid, null),
                                description = it.descrition,
                                backgroundImage = postsPhotos?.getOrDefault(it.id, null),
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
        } else {
            LargeLoadingIndicator(backgroundColor = LightGray)
        }
    }
}

 */