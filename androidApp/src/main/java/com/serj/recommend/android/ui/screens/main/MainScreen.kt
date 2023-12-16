package com.serj.recommend.android.ui.screens.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.serj.recommend.android.FEED_SCREEN
import com.serj.recommend.android.HOME_SCREEN
import com.serj.recommend.android.PROFILE_SCREEN
import com.serj.recommend.android.REC_SCREEN
import com.serj.recommend.android.RecommendAppState
import com.serj.recommend.android.SEARCH_SCREEN
import com.serj.recommend.android.ui.BottomNavigationBar
import com.serj.recommend.android.ui.screens.FeedScreen
import com.serj.recommend.android.ui.screens.main.home.HomeScreen
import com.serj.recommend.android.ui.screens.main.profile.ProfileScreen
import com.serj.recommend.android.ui.screens.main.rec.RecScreen
import com.serj.recommend.android.ui.screens.main.search.SearchScreen
import com.serj.recommend.android.ui.styles.MyApplicationTheme

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainScreen(
    appState: RecommendAppState
) {
    MyApplicationTheme {
        val buttonsVisible = remember { mutableStateOf(true) }
        val navController = rememberNavController()

        Surface(color = MaterialTheme.colors.background) {
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                bottomBar = {
                    BottomNavigationBar(
                        navController = navController,
                        state = buttonsVisible,
                        modifier = Modifier
                    )
                }
            ) { paddingValues ->
                NavHost(
                    modifier = Modifier.padding(paddingValues),
                    navController = navController,
                    startDestination = HOME_SCREEN
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
    composable(HOME_SCREEN) {
        HomeScreen(
            openScreen = { route -> appState.navigate(route) }
        )
    }
    composable(FEED_SCREEN) {
        FeedScreen()
    }
    composable(REC_SCREEN) {
        RecScreen()
    }
    composable(SEARCH_SCREEN) {
        SearchScreen()
    }
    composable(PROFILE_SCREEN) {
        ProfileScreen()
    }
}