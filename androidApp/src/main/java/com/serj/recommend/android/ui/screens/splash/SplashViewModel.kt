package com.serj.recommend.android.ui.screens.splash

import androidx.compose.runtime.mutableStateOf
import com.google.firebase.auth.FirebaseAuthException
import com.serj.recommend.android.HOME_SCREEN
import com.serj.recommend.android.LOGIN_SCREEN
import com.serj.recommend.android.SPLASH_SCREEN
import com.serj.recommend.android.model.service.AccountService
import com.serj.recommend.android.model.service.ConfigurationService
import com.serj.recommend.android.model.service.LogService
import com.serj.recommend.android.ui.screens.RecommendViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    configurationService: ConfigurationService,
    private val accountService: AccountService,
    logService: LogService
) : RecommendViewModel(logService) {

    val addRecommenderSystem = mutableStateOf(false)

    init {
        launchCatching {
            addRecommenderSystem.value = configurationService.fetchConfiguration()
        }
    }

    fun onAppStart(openAndPopUp: (String, String) -> Unit) {
        if (accountService.hasUser)
            openAndPopUp(HOME_SCREEN, SPLASH_SCREEN)
        else
            openAndPopUp(LOGIN_SCREEN, SPLASH_SCREEN)
            //createAnonymousAccount(openAndPopUp)
    }

    private fun createAnonymousAccount(openAndPopUp: (String, String) -> Unit) {
        launchCatching(snackbar = false) {
            try {
                accountService.createAnonymousAccount()
            } catch (ex: FirebaseAuthException) {
                addRecommenderSystem.value = true
                throw ex
            }
            openAndPopUp(HOME_SCREEN, SPLASH_SCREEN)
        }
    }
}