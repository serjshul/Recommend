package com.serj.recommend.android.ui.screens.authentication.signUp

import androidx.compose.runtime.mutableStateOf
import com.serj.recommend.android.R
import com.serj.recommend.android.RecommendRoutes
import com.serj.recommend.android.common.ext.isValidEmail
import com.serj.recommend.android.common.ext.isValidPassword
import com.serj.recommend.android.common.ext.passwordMatches
import com.serj.recommend.android.common.snackbar.SnackbarManager
import com.serj.recommend.android.model.service.AccountService
import com.serj.recommend.android.model.service.LogService
import com.serj.recommend.android.ui.screens.RecommendViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val accountService: AccountService,
    logService: LogService
) : RecommendViewModel(logService) {
    var uiState = mutableStateOf(SignUpUiState())
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

    fun onRepeatPasswordChange(newValue: String) {
        uiState.value = uiState.value.copy(repeatPassword = newValue)
    }

    fun onSignUpClick(openAndPopUp: (String, String) -> Unit) {
        if (!email.isValidEmail()) {
            SnackbarManager.showMessage(R.string.email_error)
            return
        }

        if (!password.isValidPassword()) {
            SnackbarManager.showMessage(R.string.password_error)
            return
        }

        if (!password.passwordMatches(uiState.value.repeatPassword)) {
            SnackbarManager.showMessage(R.string.password_match_error)
            return
        }

        launchCatching {
            accountService.signUp(email, password)
            openAndPopUp(RecommendRoutes.MainScreen.name, RecommendRoutes.SignUpScreen.name)
        }
    }
}