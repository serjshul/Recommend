package com.serj.recommend.android

import android.Manifest
import android.content.res.Resources
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarHost
import androidx.compose.material.Surface
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.serj.recommend.android.common.composable.PermissionDialog
import com.serj.recommend.android.common.composable.RationaleDialog
import com.serj.recommend.android.ui.BottomNavigationBar
import com.serj.recommend.android.ui.components.snackbar.SnackbarManager
import com.serj.recommend.android.ui.screens.FeedScreen
import com.serj.recommend.android.ui.screens.common.banner.BannerScreen
import com.serj.recommend.android.ui.screens.main.home.HomeScreen
import com.serj.recommend.android.ui.screens.main.profile.ProfileScreen
import com.serj.recommend.android.ui.screens.main.rec.RecScreen
import com.serj.recommend.android.ui.screens.common.recommendation.RecommendationScreen
import com.serj.recommend.android.ui.screens.main.search.SearchScreen
import com.serj.recommend.android.ui.screens.authentication.signIn.SignInScreen
import com.serj.recommend.android.ui.screens.authentication.signUp.SignUpScreen
import com.serj.recommend.android.ui.screens.authentication.splash.SplashScreen
import com.serj.recommend.android.ui.styles.MyApplicationTheme
import kotlinx.coroutines.CoroutineScope

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RecommendApp() {
    MyApplicationTheme {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            RequestNotificationPermissionDialog()
        }

        val buttonsVisible = remember { mutableStateOf(true) }

        Surface(color = MaterialTheme.colors.background) {
            val appState = rememberAppState()

            Scaffold(
                modifier = Modifier.fillMaxSize(),
                bottomBar = {
                    BottomNavigationBar(
                        navController = appState.navController,
                        state = buttonsVisible,
                        modifier = Modifier
                    )
                },
                snackbarHost = {
                    SnackbarHost(
                        hostState = it,
                        modifier = Modifier.padding(8.dp),
                        snackbar = { snackbarData ->
                            Snackbar(snackbarData, contentColor = MaterialTheme.colors.onPrimary)
                        }
                    )
                },
                scaffoldState = appState.scaffoldState
            ) { paddingValues ->
                NavHost(
                    modifier = Modifier.padding(paddingValues),
                    navController = appState.navController,
                    startDestination = SPLASH_SCREEN
                ) {
                    recommendGraph(appState=appState)
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RequestNotificationPermissionDialog() {
    val permissionState = rememberPermissionState(permission = Manifest.permission.POST_NOTIFICATIONS)

    if (!permissionState.status.isGranted) {
        if (permissionState.status.shouldShowRationale) RationaleDialog()
        else PermissionDialog { permissionState.launchPermissionRequest() }
    }
}

@Composable
fun rememberAppState(
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    navController: NavHostController = rememberNavController(),
    snackbarManager: SnackbarManager = SnackbarManager,
    resources: Resources = resources(),
    coroutineScope: CoroutineScope = rememberCoroutineScope()
) =
    remember(scaffoldState, navController, snackbarManager, resources, coroutineScope) {
        RecommendAppState(scaffoldState, navController, snackbarManager, resources, coroutineScope)
    }

@Composable
@ReadOnlyComposable
fun resources(): Resources {
    LocalConfiguration.current
    return LocalContext.current.resources
}

@ExperimentalMaterialApi
fun NavGraphBuilder.recommendGraph(
    modifier: Modifier = Modifier,
    appState: RecommendAppState
) {
    composable(SPLASH_SCREEN) {
        SplashScreen(openAndPopUp = { route, popUp -> appState.navigateAndPopUp(route, popUp) })
    }
    composable(SIGN_UP_SCREEN) {
        SignUpScreen(openAndPopUp = { route, popUp -> appState.navigateAndPopUp(route, popUp) })
    }
    composable(SIGN_IN_SCREEN) {
        SignInScreen(openAndPopUp = { route, popUp -> appState.navigateAndPopUp(route, popUp) })
    }
    composable(HOME_SCREEN) {
        HomeScreen(
            openScreen = { route -> appState.navigate(route) }
        )
    }
    composable(FEED_SCREEN) {
        FeedScreen()
    }
    composable(REC_SCREEN) {
        RecScreen()
    }
    composable(SEARCH_SCREEN) {
        SearchScreen()
    }
    composable(PROFILE_SCREEN) {
        ProfileScreen()
    }
    composable(
        route = "$RECOMMENDATION_SCREEN$RECOMMENDATION_ID_ARG",
        arguments = listOf(navArgument(RECOMMENDATION_ID) {
            nullable = true
            defaultValue = null
        })
    ) {
        RecommendationScreen(
            popUpScreen = { appState.popUp() }
        )
    }
    composable(
        route = "$BANNER_SCREEN$BANNER_ID_ARG",
        arguments = listOf(navArgument(BANNER_ID) {
            nullable = true
            defaultValue = null
        })
    ) {
        BannerScreen(
            openScreen = { route -> appState.navigate(route) },
            popUpScreen = { appState.popUp() }
        )
    }
}