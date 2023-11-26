package com.serj.recommend.android.datalayer.navigation

import com.serj.recommend.android.R
import com.serj.recommend.datalayer.navigation.Navigation

sealed class BottomNavigationItem(
    val route: String,
    var title: Int,
    var icon: Int
) {
    data object Home :
        BottomNavigationItem(
            Navigation.HomeScreen.name,
            R.string.navigation_title_home_screen,
            R.drawable.icon_home
        )

    data object Feed :
        BottomNavigationItem(
            Navigation.FeedScreen.name,
            R.string.navigation_title_feed_screen,
            R.drawable.icon_feed
        )

    data object Search :
        BottomNavigationItem(
            Navigation.SearchScreen.name,
            R.string.navigation_title_search_screen,
            R.drawable.icon_search
        )

    data object Profile :
        BottomNavigationItem(
            Navigation.ProfileScreen.name,
            R.string.navigation_title_profile_screen,
            R.drawable.icon_profile
        )
}