package com.serj.recommend.android.ui.components.bottombar

import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.serj.recommend.android.ui.components.bottombar.BottomNavigation

@Composable
fun BottomNavigationBar(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val screens = listOf(
        BottomNavigation.Home,
        BottomNavigation.Feed,
        BottomNavigation.Rec,
        BottomNavigation.Search,
        BottomNavigation.Profile
    )

    NavigationBar(
        modifier = modifier.height(50.dp),
        containerColor = White
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        screens.forEach { screen ->
            val screenTitle = stringResource(id = screen.title)
            val screenRoute = screen.route

            NavigationBarItem(
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
                    indicatorColor = White,
                    unselectedIconColor = LightGray,
                    selectedIconColor = Black
                ),
            )
        }
    }
}

@Preview
@Composable
fun BottomNavigationBarPreview() {
    BottomNavigationBar(
        navController = rememberNavController()
    )
}