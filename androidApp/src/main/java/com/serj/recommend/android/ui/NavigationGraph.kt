package com.serj.recommend.android.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.serj.recommend.android.BottomNavigationItem
import com.serj.recommend.android.ui.screens.FeedScreen
import com.serj.recommend.android.ui.screens.HomeScreen
import com.serj.recommend.android.ui.screens.ProfileScreen
import com.serj.recommend.android.ui.screens.SearchScreen

@Composable
fun NavigationGraph(navController: NavHostController) {
    val routeHome = stringResource(id = BottomNavigationItem.Home.route)
    val routeFeed = stringResource(id = BottomNavigationItem.Feed.route)
    val routeSearch = stringResource(id = BottomNavigationItem.Search.route)
    val routeProfile = stringResource(id = BottomNavigationItem.Profile.route)

    NavHost(
        navController,
        startDestination = routeHome
    ) {
        composable(routeHome) {
            HomeScreen()
        }
        composable(routeFeed) {
            FeedScreen()
        }
        composable(routeSearch) {
            SearchScreen()
        }
        composable(routeProfile) {
            ProfileScreen()
        }
    }
}