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
import com.serj.recommend.android.ui.components.snackbar.SnackbarManager
import com.serj.recommend.android.ui.screens.authentication.resetPassword.ResetPasswordScreen
import com.serj.recommend.android.ui.screens.authentication.signIn.SignInScreen
import com.serj.recommend.android.ui.screens.authentication.signUp.SignUpScreen
import com.serj.recommend.android.ui.screens.authentication.splash.SplashScreen
import com.serj.recommend.android.ui.screens.common.banner.BannerScreen
import com.serj.recommend.android.ui.screens.common.recommendation.RecommendationScreen
import com.serj.recommend.android.ui.screens.main.MainScreen
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
                    startDestination = RecommendRoutes.SplashScreen.name
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
    composable(RecommendRoutes.SplashScreen.name) {
        SplashScreen(
            openScreen = { route -> appState.navigate(route) }
        )
    }
    composable(RecommendRoutes.SignUpScreen.name) {
        SignUpScreen(
            openAndPopUp = { route, popUp -> appState.navigateAndPopUp(route, popUp) }
        )
    }
    composable(RecommendRoutes.SignInScreen.name) {
        SignInScreen(
            openAndPopUp = { route, popUp -> appState.navigateAndPopUp(route, popUp) }
        )
    }
    composable(RecommendRoutes.ResetPasswordScreen.name) {
        ResetPasswordScreen(
            openScreen = { route -> appState.navigate(route) }
        )
    }
    composable(RecommendRoutes.MainScreen.name) {
        MainScreen(
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
            openScreen = { route -> appState.navigate(route) },
            popUpScreen = { appState.popUp() }
        )
    }
}