package com.serj.recommend.android

import android.content.res.Resources
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
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
import com.serj.recommend.android.common.Constants.BANNER_ID
import com.serj.recommend.android.common.Constants.BANNER_ID_ARG
import com.serj.recommend.android.common.Constants.CATEGORY_ID
import com.serj.recommend.android.common.Constants.CATEGORY_ID_ARG
import com.serj.recommend.android.common.Constants.RECOMMENDATION_ID
import com.serj.recommend.android.common.Constants.RECOMMENDATION_ID_ARG
import com.serj.recommend.android.ui.components.snackbar.SnackbarManager
import com.serj.recommend.android.ui.screens.authentication.createProfile.CreateProfileScreen
import com.serj.recommend.android.ui.screens.authentication.resetPassword.ResetPasswordScreen
import com.serj.recommend.android.ui.screens.authentication.signIn.SignInScreen
import com.serj.recommend.android.ui.screens.authentication.signUp.SignUpScreen
import com.serj.recommend.android.ui.screens.common.banner.BannerScreen
import com.serj.recommend.android.ui.screens.common.category.CategoryScreen
import com.serj.recommend.android.ui.screens.common.recommendation.RecommendationScreen
import com.serj.recommend.android.ui.screens.main.MainScreen
import com.serj.recommend.android.ui.screens.splash.SplashScreen
import com.serj.recommend.android.ui.styles.RecommendTheme
import kotlinx.coroutines.CoroutineScope

// TODO: Start use Material3 (M3) only! Instead of mix Material + M3
// TODO: Resolve why part of dependencies in .toml file, part in .gradle
// TODO: Upgrade dependencies - but also test before and after, how app is work - for this we need good size of tests!
@Composable
fun RecommendApp() {
    RecommendTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            val appState = rememberAppState()

            Scaffold(
                modifier = Modifier.fillMaxSize(),
                snackbarHost = {
                    SnackBar(hostState = appState.snackbarHostState)
                },
                // TODO: @serjshul, if you think this floating button is useless - delete it
                floatingActionButton = {
                    RecommendFloatingActionBar {
                        appState.navController.navigate(
                            RecommendRoutes.NewRecommendationScreen.name
                        )
                    }
                }
            ) { paddingValues ->
                NavHost(
                    modifier = Modifier.padding(paddingValues),
                    navController = appState.navController,
                    startDestination = RecommendRoutes.SplashScreen.name
                ) {
                    recommendGraph(appState = appState)
                }
            }
        }
    }
}

@Composable
fun SnackBar(hostState: SnackbarHostState) = SnackbarHost(
    hostState = hostState,
    modifier = Modifier.padding(8.dp),
    snackbar = { snackbarData ->
        Snackbar(
            snackbarData = snackbarData,
            contentColor = MaterialTheme.colorScheme.onPrimary
        )
    }
)

@Composable
fun rememberAppState(
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    navController: NavHostController = rememberNavController(),
    snackbarManager: SnackbarManager = SnackbarManager,
    resources: Resources = resources(),
    coroutineScope: CoroutineScope = rememberCoroutineScope()
) = remember(
    snackbarHostState, navController, snackbarManager,
    resources, coroutineScope
) {
    RecommendAppState(
        snackbarHostState, navController, snackbarManager,
        resources, coroutineScope
    )
}

@Composable
@ReadOnlyComposable
fun resources(): Resources {
    LocalConfiguration.current // TODO: Isn't this are unusable? (can we delete this code?)
    return LocalContext.current.resources
}

fun NavGraphBuilder.recommendGraph(
    modifier: Modifier = Modifier,
    appState: RecommendAppState
) {
    composable(RecommendRoutes.SplashScreen.name) {
        SplashScreen(
            modifier = Modifier,
            openAndPopUp = { route, popUp ->
                appState.navigateAndPopUp(route, popUp)
            }
        )
    }
    composable(RecommendRoutes.SignUpScreen.name) {
        SignUpScreen(
            modifier = Modifier,
            openScreen = { route ->
                appState.navigate(route)
            },
        )
    }
    composable(RecommendRoutes.CreateProfileScreen.name) {
        CreateProfileScreen(
            modifier = Modifier,
            clearAndOpen = { route ->
                appState.clearAndNavigate(route)
            }
        )
    }
    composable(RecommendRoutes.SignInScreen.name) {
        SignInScreen(
            modifier = Modifier,
            openScreen = { route ->
                appState.navigate(route)
            },
            clearAndOpen = { route ->
                appState.clearAndNavigate(route)
            }
        )
    }
    composable(RecommendRoutes.ResetPasswordScreen.name) {
        ResetPasswordScreen(
            modifier = Modifier,
            openAndPopUp = { route, popUp ->
                appState.navigateAndPopUp(route, popUp)
            }
        )
    }
    composable(RecommendRoutes.MainScreen.name) {
        MainScreen(
            modifier = Modifier,
            appState = appState
        )
    }
    composable(
        route = "${RecommendRoutes.RecommendationScreen.name}$RECOMMENDATION_ID_ARG",
        arguments = listOf(navArgument(RECOMMENDATION_ID) {
            nullable = true
            defaultValue = null
        })
    ) {
        RecommendationScreen(
            modifier = Modifier,
            popUpScreen = { appState.popUp() }
        )
    }
    composable(
        route = "${RecommendRoutes.BannerScreen.name}$BANNER_ID_ARG",
        arguments = listOf(navArgument(BANNER_ID) {
            nullable = true
            defaultValue = null
        })
    ) {
        BannerScreen(
            modifier = Modifier,
            openScreen = { route ->
                appState.navigate(route)
            },
            popUpScreen = { appState.popUp() }
        )
    }
    composable(
        route = "${RecommendRoutes.CategoryScreen.name}$CATEGORY_ID_ARG",
        arguments = listOf(navArgument(CATEGORY_ID) {
            nullable = true
            defaultValue = null
        })
    ) {
        CategoryScreen(
            modifier = Modifier,
            openScreen = { route ->
                appState.navigate(route)
            },
            popUpScreen = { appState.popUp() }
        )
    }
}