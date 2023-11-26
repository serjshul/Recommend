package com.serj.recommend.android.ui

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.serj.recommend.android.R
import com.serj.recommend.android.datalayer.navigation.BottomNavigationItem

@Composable
fun BottomNavigationBar(
    navController: NavHostController,
    state: MutableState<Boolean>,
    modifier: Modifier = Modifier
) {
    val screens = listOf(
        BottomNavigationItem.Home,
        BottomNavigationItem.Feed,
        BottomNavigationItem.Search,
        BottomNavigationItem.Profile
    )

    NavigationBar(
        modifier = modifier,
        containerColor = Color.White
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        screens.forEach { screen ->
            val screenTitle = stringResource(id = screen.title)
            val screenRoute = screen.route

            NavigationBarItem(
                label = {
                    Text(text = stringResource(id = screen.title))
                },
                icon = {
                    Icon(
                        imageVector = ImageVector.vectorResource(screen.icon),
                        contentDescription = screenTitle
                    )
                },
                selected = currentRoute == screenRoute,
                onClick = {
                    navController.navigate(screenRoute) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color.White,
                    unselectedTextColor = Color.Gray,
                    unselectedIconColor = Color.Gray,
                    selectedTextColor = colorResource(R.color.tigereye),
                    selectedIconColor = colorResource(R.color.tigereye)
                ),
            )
        }
    }
}