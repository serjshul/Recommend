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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
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

    private val coroutineScope: CoroutineScope =
        CoroutineScope(
            SupervisorJob()
                    + Dispatchers.Main.immediate
        )

    init {
        launchCatching {
            accountService.currentUser.collect { user ->
                profileUser = user
                currentUser = user
            }
        }
    }

    fun changeBio() {

    }

    fun changeName() {

    }

    fun changeEmail() {

    }

    fun changeUsername() {

    }

    fun getPosts() {

    }

    fun getSaved() {
        // firebase request to users.this.saved_hash_ids
        // get all (part, first some)

        // firebase request to places where we saved
    }

    override fun onCleared() {
        coroutineScope.cancel()
    }
}