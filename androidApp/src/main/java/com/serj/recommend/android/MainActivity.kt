package com.serj.recommend.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.serj.recommend.android.ui.BottomNavigationBar
import com.serj.recommend.android.ui.NavigationGraph
import com.serj.recommend.android.ui.screens.FeedScreen
import com.serj.recommend.android.ui.screens.HomeScreen
import com.serj.recommend.android.ui.screens.SavedScreen
import com.serj.recommend.android.ui.screens.SearchScreen
import com.serj.recommend.android.ui.styles.MyApplicationTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //

        setContent {
            MyApplicationTheme {
                val navController: NavHostController = rememberNavController()
                val bottomBarHeight = 45.dp
                val bottomBarOffsetHeightPx = remember { mutableFloatStateOf(0f) }
                var buttonsVisible = remember { mutableStateOf(true) }

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

        //
    }
}

sealed class BottomNavigationItem(
    val route: String,
    var title: String,
    var icon: Int
) {
    data object Home :
        BottomNavigationItem(
            "home_screen",
            "Home",
            R.drawable.logo_home
        )

    data object Feed :
        BottomNavigationItem(
            "feed_screen",
            "Feed",
            R.drawable.logo_feed
        )

    data object Search :
        BottomNavigationItem(
            "search_screen",
            "Search",
            R.drawable.logo_search
        )

    data object Saved :
        BottomNavigationItem(
            "saved_screen",
            "Saved",
            R.drawable.logo_saved
        )
}
