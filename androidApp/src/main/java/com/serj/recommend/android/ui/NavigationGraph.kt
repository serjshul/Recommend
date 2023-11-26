package com.serj.recommend.android.ui

import android.icu.lang.UCharacter.IndicPositionalCategory.NA
import android.util.Log
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.serj.recommend.android.BottomNavigationItem
import com.serj.recommend.android.ui.screens.FeedScreen
import com.serj.recommend.android.ui.screens.HomeScreen
import com.serj.recommend.android.ui.screens.SavedScreen
import com.serj.recommend.android.ui.screens.SearchScreen
import com.serj.recommend.datalayer.navigation.Navigation

@Composable
fun NavigationGraph(navController: NavHostController) {
    NavHost(
        navController,
        startDestination = BottomNavigationItem.Home.route
    ) {
        composable(BottomNavigationItem.Home.route) {
            HomeScreen(navController)
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
        composable(Navigation.ACTICLES_SCREEN.name) {
            Log.d("TEST", "Navigation to ${Navigation.ACTICLES_SCREEN.name}}")
//            ActiclesScreen()
            Text("ACTICLES SCREEN")
        }
    }
}