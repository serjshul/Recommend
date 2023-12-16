package com.serj.recommend.android.ui.screens.authentication.signIn

import androidx.compose.runtime.mutableStateOf
import com.serj.recommend.android.HOME_SCREEN
import com.serj.recommend.android.R
import com.serj.recommend.android.RESET_PASSWORD_SCREEN
import com.serj.recommend.android.SIGN_IN_SCREEN
import com.serj.recommend.android.SIGN_UP_SCREEN
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

    fun onSignInClick(openAndPopUp: (String, String) -> Unit) {
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
            openAndPopUp(HOME_SCREEN, SIGN_IN_SCREEN)
        }
    }

    fun onForgotPasswordClick(openAndPopUp: (String, String) -> Unit) {
        openAndPopUp(RESET_PASSWORD_SCREEN, SIGN_IN_SCREEN)
    }

    fun onSignUpClick(openAndPopUp: (String, String) -> Unit) {
        openAndPopUp(SIGN_UP_SCREEN, SIGN_IN_SCREEN)
    }
}