package com.serj.recommend.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.serj.recommend.android.ui.BottomNavigationBar
import com.serj.recommend.android.ui.NavigationGraph
import com.serj.recommend.android.ui.styles.MyApplicationTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                val navController: NavHostController = rememberNavController()
                val buttonsVisible = remember { mutableStateOf(true) }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        BottomNavigationBar(
                            navController = navController,
                            state = buttonsVisible,
                            modifier = Modifier
                        )
                    }) { paddingValues ->
                    Box(
                        modifier = Modifier.padding(paddingValues)
                    ) {
                        NavigationGraph(navController)
                    }
                }
            }
        }
    }
}

sealed class BottomNavigationItem(
    val route: Int,
    var title: Int,
    var icon: Int
) {
    data object Home :
        BottomNavigationItem(
            R.string.navigation_route_home_screen,
            R.string.navigation_title_home_screen,
            R.drawable.icon_home
        )

    data object Feed :
        BottomNavigationItem(
            R.string.navigation_route_feed_screen,
            R.string.navigation_title_feed_screen,
            R.drawable.icon_feed
        )

    data object Search :
        BottomNavigationItem(
            R.string.navigation_route_search_screen,
            R.string.navigation_title_search_screen,
            R.drawable.icon_search
        )

    data object Profile :
        BottomNavigationItem(
            R.string.navigation_route_profile_screen,
            R.string.navigation_title_profile_screen,
            R.drawable.icon_profile
        )
}
