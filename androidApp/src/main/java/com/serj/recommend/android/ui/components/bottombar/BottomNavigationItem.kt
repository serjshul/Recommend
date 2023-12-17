package com.serj.recommend.android.ui.components.bottombar

import com.serj.recommend.android.R
import com.serj.recommend.android.RecommendRoutes

sealed class BottomNavigationItem(
    val route: String,
    var title: Int,
    var icon: Int
) {
    data object Home :
        BottomNavigationItem(
            RecommendRoutes.HomeScreen.name,
            R.string.navigation_title_home_screen,
            R.drawable.icon_home
        )

    data object Feed :
        BottomNavigationItem(
            RecommendRoutes.FeedScreen.name,
            R.string.navigation_title_feed_screen,
            R.drawable.icon_feed
        )

    data object Rec :
        BottomNavigationItem(
            RecommendRoutes.RecScreen.name,
            R.string.rec_screen,
            R.drawable.icon_add
        )

    data object Search :
        BottomNavigationItem(
            RecommendRoutes.SearchScreen.name,
            R.string.navigation_title_search_screen,
            R.drawable.icon_search
        )

    data object Profile :
        BottomNavigationItem(
            RecommendRoutes.ProfileScreen.name,
            R.string.navigation_title_profile_screen,
            R.drawable.icon_profile
        )
}