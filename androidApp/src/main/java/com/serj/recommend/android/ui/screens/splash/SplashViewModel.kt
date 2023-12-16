package com.serj.recommend.android.ui.screens.splash

import androidx.compose.runtime.mutableStateOf
import com.serj.recommend.android.HOME_SCREEN
import com.serj.recommend.android.SIGN_UP_SCREEN
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
            openAndPopUp(SIGN_UP_SCREEN, SPLASH_SCREEN)
            //createAnonymousAccount(openAndPopUp)
    }
}