package com.serj.recommend.android.ui.screens.authentication.signIn

import androidx.compose.runtime.mutableStateOf
import com.serj.recommend.android.R
import com.serj.recommend.android.RecommendRoutes
import com.serj.recommend.android.common.ext.isValidEmail
import com.serj.recommend.android.common.snackbar.SnackbarManager
import com.serj.recommend.android.model.service.AccountService
import com.serj.recommend.android.model.service.LogService
import com.serj.recommend.android.ui.screens.RecommendViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val accountService: AccountService,
    logService: LogService
) : RecommendViewModel(logService) {
    var uiState = mutableStateOf(SignInUiState())
        private set

    private val email
        get() = uiState.value.email
    private val password
        get() = uiState.value.password

    fun onEmailChange(newValue: String) {
        uiState.value = uiState.value.copy(email = newValue)
    }

    fun onPasswordChange(newValue: String) {
        uiState.value = uiState.value.copy(password = newValue)
    }

    fun onSignInClick(openScreen: (String) -> Unit) {
        if (!email.isValidEmail()) {
            SnackbarManager.showMessage(R.string.email_error)
            return
        }

        if (password.isBlank()) {
            SnackbarManager.showMessage(R.string.empty_password_error)
            return
        }

        launchCatching {
            accountService.signIn(email, password)
            openScreen(RecommendRoutes.MainScreen.name)
        }
    }

    fun onForgotPasswordClick(openAndPopUp: (String, String) -> Unit) {
        openAndPopUp(RecommendRoutes.ResetPasswordScreen.name, RecommendRoutes.SignInScreen.name)
    }

    fun onSignUpClick(openAndPopUp: (String, String) -> Unit) {
        openAndPopUp(RecommendRoutes.SignUpScreen.name, RecommendRoutes.SignInScreen.name)
    }
}