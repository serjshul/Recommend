package com.serj.recommend.android

import android.content.res.Resources
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
import com.serj.recommend.android.ui.components.snackbar.SnackbarManager
import com.serj.recommend.android.ui.screens.authentication.resetPassword.ResetPasswordScreen
import com.serj.recommend.android.ui.screens.authentication.signIn.SignInScreen
import com.serj.recommend.android.ui.screens.authentication.signUp.SignUpScreen
import com.serj.recommend.android.ui.screens.authentication.splash.SplashScreen
import com.serj.recommend.android.ui.screens.common.recommendation.RecommendationScreen
import com.serj.recommend.android.ui.screens.main.MainScreen
import com.serj.recommend.android.ui.screens.common.banner.BannerScreen
import com.serj.recommend.android.ui.screens.common.category.CategoryScreen
import com.serj.recommend.android.ui.styles.MyApplicationTheme
import kotlinx.coroutines.CoroutineScope

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RecommendApp() {
    MyApplicationTheme {
        Surface(color = MaterialTheme.colors.background) {
            val appState = rememberAppState()

            Scaffold(
                modifier = Modifier.fillMaxSize(),
                snackbarHost = {
                    SnackbarHost(
                        hostState = it,
                        modifier = Modifier.padding(8.dp),
                        snackbar = { snackbarData ->
                            Snackbar(
                                snackbarData = snackbarData,
                                contentColor = MaterialTheme.colors.onPrimary
                            )
                        }
                    )
                },
                scaffoldState = appState.scaffoldState
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
fun rememberAppState(
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    navController: NavHostController = rememberNavController(),
    snackbarManager: SnackbarManager = SnackbarManager,
    resources: Resources = resources(),
    coroutineScope: CoroutineScope = rememberCoroutineScope()
) = remember(
    scaffoldState, navController, snackbarManager,
    resources, coroutineScope
) {
    RecommendAppState(
        scaffoldState, navController, snackbarManager,
        resources, coroutineScope
    )
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
    composable(RecommendRoutes.SplashScreen.name) {
        SplashScreen(
            modifier = modifier,
            openScreen = { route -> appState.navigate(route) }
        )
    }
    composable(RecommendRoutes.SignUpScreen.name) {
        SignUpScreen(
            modifier = modifier,
            openScreen = { route -> appState.navigate(route) }
        )
    }
    composable(RecommendRoutes.SignInScreen.name) {
        SignInScreen(
            modifier = modifier,
            openScreen = { route -> appState.navigate(route) },
            openAndPopUp = { route, popUp -> appState.navigateAndPopUp(route, popUp) }
        )
    }
    composable(RecommendRoutes.ResetPasswordScreen.name) {
        ResetPasswordScreen(
            modifier = modifier,
            openScreen = { route -> appState.navigate(route) }
        )
    }
    composable(RecommendRoutes.MainScreen.name) {
        MainScreen(
            modifier = modifier,
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
            modifier = modifier,
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
            modifier = modifier,
            openScreen = { route -> appState.navigate(route) },
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
            modifier = modifier,
            openScreen = { route -> appState.navigate(route) },
            popUpScreen = { appState.popUp() }
        )
    }
}