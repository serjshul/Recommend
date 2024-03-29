package com.serj.recommend.android.ui.screens.authentication.signUp

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
import androidx.compose.ui.platform.testTag
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
import com.serj.recommend.android.ui.components.authentication.PasswordField
import com.serj.recommend.android.ui.components.authentication.RepeatPasswordField
import com.serj.recommend.android.ui.components.splash.AppLogo

val SIGNUP_SCREEN_SIGN_UP_BUTTON_TT = "SignUpScreenSignUpButton"
val SIGN_UP_PASSWORDS_FIELD_TT = "signUpPasswordsFieldTT"
val SIGN_UP_REPEAT_PASSWORDS_FIELD_TT = "signUpRepeatPasswordsFieldTT"

@Composable
fun SignUpScreen(
    modifier: Modifier = Modifier,
    openScreen: (String) -> Unit,
    viewModel: SignUpViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState

    SignUpScreenContent(
        modifier = modifier,
        uiState = uiState,
        onEmailChange = viewModel::onEmailChange,
        onPasswordChange = viewModel::onPasswordChange,
        onRepeatPasswordChange = viewModel::onRepeatPasswordChange,
        onSignUpClick = { viewModel.onSignUpClick(openScreen) }
    )
}

@Composable
fun SignUpScreenContent(
    modifier: Modifier = Modifier,
    uiState: SignUpUiState,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onRepeatPasswordChange: (String) -> Unit,
    onSignUpClick: () -> Unit
) {
    val fieldModifier = Modifier.fieldModifier()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .weight(4f)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AppLogo()
        }

        Column(
            modifier = Modifier
                .weight(6f)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.padding(bottom = 30.dp),
                text = "Sign up to see recommendations\nfrom your friends",
                fontSize = 16.sp,
                textAlign = TextAlign.Center
            )
            EmailField(uiState.email, onEmailChange, fieldModifier)
            PasswordField(
                uiState.password,
                R.string.password,
                onPasswordChange,
                fieldModifier.testTag(SIGN_UP_PASSWORDS_FIELD_TT)
            )

            RepeatPasswordField(
                uiState.repeatPassword,
                onRepeatPasswordChange,
                fieldModifier.testTag(SIGN_UP_REPEAT_PASSWORDS_FIELD_TT)
            )
        }

        AuthenticationButton(
            text = R.string.sign_up_button,
            action = onSignUpClick,
            modifier = Modifier
                .basicButton()
                .testTag(SIGNUP_SCREEN_SIGN_UP_BUTTON_TT)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SignUpScreenPreview() {
    val uiState = SignUpUiState(
        email = "email@test.com"
    )

    SignUpScreenContent(
        uiState = uiState,
        onEmailChange = { },
        onPasswordChange = { },
        onRepeatPasswordChange = { },
        onSignUpClick = { }
    )
}