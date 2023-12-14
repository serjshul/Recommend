package com.serj.recommend.android.ui.components.bottombar

import com.serj.recommend.android.FEED_SCREEN
import com.serj.recommend.android.HOME_SCREEN
import com.serj.recommend.android.PROFILE_SCREEN
import com.serj.recommend.android.R
import com.serj.recommend.android.REC_SCREEN
import com.serj.recommend.android.SEARCH_SCREEN

sealed class BottomNavigationItem(
    val route: String,
    var title: Int,
    var icon: Int
) {
    data object Home :
        BottomNavigationItem(
            HOME_SCREEN,
            R.string.navigation_title_home_screen,
            R.drawable.icon_home
        )

    data object Feed :
        BottomNavigationItem(
            FEED_SCREEN,
            R.string.navigation_title_feed_screen,
            R.drawable.icon_feed
        )

    data object Rec :
        BottomNavigationItem(
            REC_SCREEN,
            R.string.rec_screen,
            R.drawable.icon_add
        )

    data object Search :
        BottomNavigationItem(
            SEARCH_SCREEN,
            R.string.navigation_title_search_screen,
            R.drawable.icon_search
        )

    data object Profile :
        BottomNavigationItem(
            PROFILE_SCREEN,
            R.string.navigation_title_profile_screen,
            R.drawable.icon_profile
        )
}