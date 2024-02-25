package com.serj.recommend.android.ui.screens.authentication.signIn

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.serj.recommend.android.R
import com.serj.recommend.android.common.ext.fieldModifier
import com.serj.recommend.android.common.ext.textButton
import com.serj.recommend.android.ui.components.authentication.AuthenticationButton
import com.serj.recommend.android.ui.components.authentication.AuthenticationTextButton
import com.serj.recommend.android.ui.components.authentication.EmailField
import com.serj.recommend.android.ui.components.authentication.PasswordField
import com.serj.recommend.android.ui.components.splash.AppLogo

val AUTHENTICATION_SIGN_IN_BUTTON_TT = "authenticationSignInButtonTT"
val AUTHENTICATION_SIGN_UP_BUTTON_TT = "authenticationSignUPButtonTT"
val AUTHENTICATION_FORGOT_PASSWORD_BUTTON_TT = "authenticationForgotPasswordButtonTT"

@Composable
fun SignInScreen(
    modifier: Modifier = Modifier,
    viewModel: SignInViewModel = hiltViewModel(),
    openScreen: (String) -> Unit,
    clearAndOpen: (String) -> Unit,
) {
    val uiState by viewModel.uiState

    SignInScreenContent(
        modifier = modifier,
        uiState = uiState,
        onEmailChange = viewModel::onEmailChange,
        onPasswordChange = viewModel::onPasswordChange,
        onSignInClick = { viewModel.onSignInClick(clearAndOpen) },
        onSignUpClick = { viewModel.onSignUpClick(openScreen) },
        onForgotPasswordClick = { viewModel.onForgotPasswordClick(openScreen) }
    )
}

@Composable
fun SignInScreenContent(
    modifier: Modifier = Modifier,
    uiState: SignInUiState,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onSignInClick: () -> Unit,
    onSignUpClick: () -> Unit,
    onForgotPasswordClick: () -> Unit
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
            EmailField(
                value = uiState.email,
                onNewValue = onEmailChange,
                modifier = Modifier
                    .fieldModifier()
            )

            PasswordField(
                value = uiState.password,
                placeholder = R.string.password,
                onNewValue = onPasswordChange,
                modifier = Modifier
                    .fieldModifier()
            )

            AuthenticationButton(
                text = R.string.sign_in,
                action = onSignInClick,
                modifier = Modifier
                    .padding(start = 15.dp, top = 50.dp, end = 15.dp)
                    .fillMaxWidth()
                    .testTag(AUTHENTICATION_SIGN_IN_BUTTON_TT)
            )

            AuthenticationTextButton(
                text = R.string.forgot_password,
                modifier = Modifier
                    .textButton()
                    .testTag(AUTHENTICATION_FORGOT_PASSWORD_BUTTON_TT),
                action = onForgotPasswordClick
            )
        }

        Row(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text = "Don't have an account?")

            AuthenticationTextButton(
                modifier = Modifier.testTag(AUTHENTICATION_SIGN_UP_BUTTON_TT),
                text = R.string.sign_up,
                action = onSignUpClick
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SignInScreenPreview() {
    val uiState = SignInUiState(
        email = ""
    )

    SignInScreenContent(
        uiState = uiState,
        onEmailChange = { },
        onPasswordChange = { },
        onSignInClick = { },
        onSignUpClick = { },
        onForgotPasswordClick = { }
    )
}