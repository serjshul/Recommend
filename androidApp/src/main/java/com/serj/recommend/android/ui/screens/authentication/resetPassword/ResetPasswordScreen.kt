package com.serj.recommend.android.ui.screens.authentication.resetPassword

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.serj.recommend.android.R
import com.serj.recommend.android.common.ext.basicButton
import com.serj.recommend.android.common.ext.fieldModifier
import com.serj.recommend.android.ui.components.authentication.AuthenticationButton
import com.serj.recommend.android.ui.components.authentication.EmailField
import com.serj.recommend.android.ui.components.splash.AppLogo

@Composable
fun ResetPasswordScreen(
    modifier: Modifier = Modifier,
    openScreen: (String) -> Unit,
    viewModel: ResetPasswordViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState

    ResetPasswordScreenContent(
        modifier = modifier,
        uiState = uiState,
        onEmailChange = viewModel::onEmailChange,
        onResetPasswordClick = { viewModel.onResetPasswordClick(openScreen) }
    )
}

@Composable
fun ResetPasswordScreenContent(
    modifier: Modifier = Modifier,
    uiState: ResetPasswordUiState,
    onEmailChange: (String) -> Unit,
    onResetPasswordClick: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
    ) {
        Column(
            modifier = Modifier
                .weight(5f)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AppLogo()
        }

        Column(
            modifier = Modifier
                .weight(5f)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.padding(bottom = 30.dp),
                text = "Enter email address linked to your profile",
                fontSize = 16.sp,
                maxLines = 2,
                textAlign = TextAlign.Center
            )

            EmailField(
                value = uiState.email,
                onNewValue = onEmailChange,
                modifier = Modifier.fieldModifier()
            )

            AuthenticationButton(
                text = R.string.reset_password,
                action = onResetPasswordClick,
                modifier = Modifier.basicButton()
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SignInScreenPreview() {
    val uiState = ResetPasswordUiState(
        email = "email@test.com"
    )

    ResetPasswordScreenContent(
        uiState = uiState,
        onEmailChange = { },
        onResetPasswordClick = { }
    )
}