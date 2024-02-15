package com.serj.recommend.android.ui.screens.authentication.createProfile

import androidx.compose.runtime.mutableStateOf
import com.serj.recommend.android.services.AccountService
import com.serj.recommend.android.services.LogService
import com.serj.recommend.android.services.StorageService
import com.serj.recommend.android.ui.screens.RecommendViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CreateProfileViewModel @Inject constructor(
    logService: LogService,
    private val accountService: AccountService,
    private val storageService: StorageService
) : RecommendViewModel(logService) {
    var uiState = mutableStateOf(CreateProfileUiState())
        private set

    private val name
        get() = uiState.value.name
    private val nickname
        get() = uiState.value.nickname
    private val bio
        get() = uiState.value.bio
    private val dateOfBirth
        get() = uiState.value.dateOfBirth
    private val gender
        get() = uiState.value.gender
    private val profileImageUri
        get() = uiState.value.profileImageUri

}