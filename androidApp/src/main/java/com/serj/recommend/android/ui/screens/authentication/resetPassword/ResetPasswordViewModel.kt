package com.serj.recommend.android.ui.screens.authentication.resetPassword

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
class ResetPasswordViewModel @Inject constructor(
    private val accountService: AccountService,
    logService: LogService
) : RecommendViewModel(logService) {
    var uiState = mutableStateOf(ResetPasswordUiState())
        private set

    private val email
        get() = uiState.value.email

    fun onEmailChange(newValue: String) {
        uiState.value = uiState.value.copy(email = newValue)
    }

    fun onResetPasswordClick(openAndPopUp: (String, String) -> Unit) {
        if (!email.isValidEmail()) {
            SnackbarManager.showMessage(R.string.email_error)
            return
        }
        launchCatching {
            accountService.sendPasswordResetEmail(email)
            SnackbarManager.showMessage(R.string.recovery_email_sent)
            openAndPopUp(RecommendRoutes.SignInScreen.name, RecommendRoutes.ResetPasswordScreen.name)
        }
    }
}