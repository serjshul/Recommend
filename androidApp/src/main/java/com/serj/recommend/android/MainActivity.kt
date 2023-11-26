package com.serj.recommend.android

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.serj.recommend.android.ui.BottomNavigationBar
import com.serj.recommend.android.ui.NavigationGraph
import com.serj.recommend.android.ui.styles.MyApplicationTheme
import com.serj.recommend.whatIsMyName
import com.serj.recommend.Greeting


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //

        setContent {
            MyApplicationTheme {
                Log.d("TEST", whatIsMyName())

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
