package com.serj.recommend.android.ui.screens.main.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.serj.recommend.android.model.collections.User
import com.serj.recommend.android.services.AccountService
import com.serj.recommend.android.services.LogService
import com.serj.recommend.android.services.StorageService
import com.serj.recommend.android.ui.screens.RecommendViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    logService: LogService,
    private val storageService: StorageService,
    private val accountService: AccountService
) : RecommendViewModel(logService) {
    var currentUser by mutableStateOf<User?>(null)
        private set

    // TODO: understand where to pick it, its always must be a user,
    //       on who logged
    var profileUser by mutableStateOf<User?>(null)
        private set

    init {
        launchCatching {
            accountService.currentUser.collect { user ->
                profileUser = user
                currentUser = user
            }
        }
    }
}