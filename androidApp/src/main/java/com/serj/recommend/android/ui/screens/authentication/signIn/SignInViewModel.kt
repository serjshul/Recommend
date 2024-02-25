package com.serj.recommend.android.ui.screens.authentication.signIn

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import com.serj.recommend.android.R
import com.serj.recommend.android.RecommendRoutes
import com.serj.recommend.android.common.ext.isValidEmail
import com.serj.recommend.android.services.AccountService
import com.serj.recommend.android.services.LogService
import com.serj.recommend.android.ui.components.snackbar.SnackbarManager
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

    fun onSignInClick(clearAndOpen: (String) -> Unit,) {
        if (!email.isValidEmail()) {
            SnackbarManager.showMessage(R.string.email_error)
            Log.v(TAG, "I'm here")
            return
        }

        if (password.isBlank()) {
            SnackbarManager.showMessage(R.string.empty_password_error)
            return
        }

        launchCatching {
            accountService.signIn(email, password)
            clearAndOpen(RecommendRoutes.MainScreen.name)
        }
    }

    fun onForgotPasswordClick(openScreen: (String) -> Unit) {
        openScreen(RecommendRoutes.ResetPasswordScreen.name)
    }

    fun onSignUpClick(openScreen: (String) -> Unit) {
        openScreen(RecommendRoutes.SignUpScreen.name)
    }
}