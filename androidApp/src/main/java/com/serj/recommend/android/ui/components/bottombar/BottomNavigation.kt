package com.serj.recommend.android.ui.components.bottombar

import com.serj.recommend.android.R
import com.serj.recommend.android.RecommendRoutes

sealed class BottomNavigation(
    val route: String,
    var title: Int,
    var icon: Int
) {
    data object Home :
        BottomNavigation(
            RecommendRoutes.HomeScreen.name,
            R.string.navigation_title_home_screen,
            R.drawable.bottom_navigation_home
        )

    data object Feed :
        BottomNavigation(
            RecommendRoutes.FeedScreen.name,
            R.string.navigation_title_feed_screen,
            R.drawable.bottom_navigation_feed
        )

    data object Rec :
        BottomNavigation(
            RecommendRoutes.RecScreen.name,
            R.string.rec_screen,
            R.drawable.bottom_navigation_add
        )

    data object Search :
        BottomNavigation(
            RecommendRoutes.SearchScreen.name,
            R.string.navigation_title_search_screen,
            R.drawable.bottom_navigation_search
        )

    data object Profile :
        BottomNavigation(
            RecommendRoutes.ProfileScreen.name,
            R.string.navigation_title_profile_screen,
            R.drawable.bottom_navigation_profile
        )
}