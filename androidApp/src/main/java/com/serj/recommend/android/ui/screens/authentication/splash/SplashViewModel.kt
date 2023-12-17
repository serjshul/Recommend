package com.serj.recommend.android.ui.screens.authentication.splash

import androidx.compose.runtime.mutableStateOf
import com.serj.recommend.android.RecommendRoutes
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

    fun onAppStart(openScreen: (String) -> Unit) {
        if (accountService.hasUser)
            openScreen(RecommendRoutes.MainScreen.name)
        else
            openScreen(RecommendRoutes.SignInScreen.name)
    }
}