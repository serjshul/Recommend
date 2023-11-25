package com.serj.recommend.android.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.serj.recommend.android.BottomNavigationItem
import com.serj.recommend.android.ui.screens.FeedScreen
import com.serj.recommend.android.ui.screens.HomeScreen
import com.serj.recommend.android.ui.screens.SavedScreen
import com.serj.recommend.android.ui.screens.SearchScreen

@Composable
fun NavigationGraph(navController: NavHostController) {
    NavHost(
        navController,
        startDestination = BottomNavigationItem.Home.route
    ) {
        composable(BottomNavigationItem.Home.route) {
            HomeScreen()
        }
        composable(BottomNavigationItem.Feed.route) {
            FeedScreen()
        }
        composable(BottomNavigationItem.Search.route) {
            SearchScreen()
        }
        composable(BottomNavigationItem.Saved.route) {
            SavedScreen()
        }
    }
}