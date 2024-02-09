package com.serj.recommend.android.ui.screens.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.serj.recommend.android.RecommendAppState
import com.serj.recommend.android.RecommendRoutes
import com.serj.recommend.android.ui.components.bottombar.BottomNavigationBar
import com.serj.recommend.android.ui.screens.main.feed.FeedScreen
import com.serj.recommend.android.ui.screens.main.home.HomeScreen
import com.serj.recommend.android.ui.screens.main.newRecommendation.NewRecommendationScreen
import com.serj.recommend.android.ui.screens.main.profile.ProfileScreen
import com.serj.recommend.android.ui.screens.main.search.SearchScreen
import com.serj.recommend.android.ui.styles.RecommendTheme

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainScreen(
    appState: RecommendAppState,
    modifier: Modifier = Modifier
) {
    RecommendTheme {
        val navController = rememberNavController()

        Surface(
            modifier = modifier,
            color = MaterialTheme.colors.background
        ) {
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                bottomBar = {
                    BottomNavigationBar(
                        modifier = Modifier,
                        navController = navController
                    )
                }
            ) { paddingValues ->
                NavHost(
                    modifier = Modifier.padding(paddingValues),
                    navController = navController,
                    startDestination = RecommendRoutes.HomeScreen.name
                ) {
                    mainScreenGraph(appState = appState)
                }
            }
        }
    }
}

@ExperimentalMaterialApi
fun NavGraphBuilder.mainScreenGraph(
    appState: RecommendAppState
) {
    composable(RecommendRoutes.HomeScreen.name) {
        HomeScreen(
            openScreen = { route -> appState.navigate(route) }
        )
    }
    composable(RecommendRoutes.FeedScreen.name) {
        FeedScreen(
            openScreen = { route -> appState.navigate(route) }
        )
    }
    composable(RecommendRoutes.NewRecommendationScreen.name) {
        NewRecommendationScreen()
    }
    composable(RecommendRoutes.SearchScreen.name) {
        SearchScreen()
    }
    composable(RecommendRoutes.ProfileScreen.name) {
        ProfileScreen()
    }
}