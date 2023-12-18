package com.serj.recommend.android.ui.screens.main.profile

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel

class ProfileViewModel(
    private val coroutineScope: CoroutineScope =
        CoroutineScope(
            SupervisorJob()
                    + Dispatchers.Main.immediate
        )
) : ViewModel() {
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