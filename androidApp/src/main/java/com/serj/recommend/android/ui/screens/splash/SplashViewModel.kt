package com.serj.recommend.android.ui.screens.splash

import androidx.compose.runtime.mutableStateOf
import com.serj.recommend.android.RecommendRoutes
import com.serj.recommend.android.services.AccountService
import com.serj.recommend.android.services.ConfigurationService
import com.serj.recommend.android.services.LogService
import com.serj.recommend.android.ui.screens.RecommendViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    configurationService: ConfigurationService,
    private val accountService: AccountService,
    logService: LogService
) : RecommendViewModel(logService) {

    val addRecommenderSystem =
        mutableStateOf(false)

    init {
        launchCatching {
            addRecommenderSystem.value =
                configurationService
                    .fetchConfiguration()
        }
    }

    fun onAppStart(openAndPopUp: (String, String) -> Unit) =
        if (accountService.hasUser) openAndPopUp(
            RecommendRoutes.MainScreen.name,
            RecommendRoutes.SplashScreen.name
        ) else openAndPopUp(
            RecommendRoutes.SignInScreen.name,
            RecommendRoutes.SplashScreen.name
        )
}