package com.serj.recommend.android.ui.screens.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.serj.recommend.android.R
import com.serj.recommend.android.common.ext.basicButton
import com.serj.recommend.android.ui.components.authentication.AuthenticationButton
import com.serj.recommend.android.ui.components.splash.AppLogo
import kotlinx.coroutines.delay

private const val SPLASH_TIMEOUT = 1_000L

@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    openAndPopUp: (String, String) -> Unit,
    viewModel: SplashViewModel = hiltViewModel()
) {
    SplashScreenContent(
        modifier = modifier,
        onAppStart = { viewModel.onAppStart(openAndPopUp) },
        shouldShowError = viewModel.addRecommenderSystem.value
    )
}

@Composable
fun SplashScreenContent(
    modifier: Modifier = Modifier,
    onAppStart: () -> Unit,
    shouldShowError: Boolean
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = Color.White)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (shouldShowError) {
            AppLogo(size = 200.dp)
            Text(text = stringResource(
                R.string.generic_error
            ))
            AuthenticationButton(
                text = R.string.try_again,
                action = onAppStart,
                modifier = Modifier.basicButton()
            )
        } else {
            AppLogo(size = 200.dp)
            LaunchedEffect(true) {
                delay(SPLASH_TIMEOUT)
                onAppStart()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    SplashScreenContent(
        onAppStart = { },
        shouldShowError = true
    )
}