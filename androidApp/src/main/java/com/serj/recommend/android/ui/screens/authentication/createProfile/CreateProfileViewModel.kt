package com.serj.recommend.android.ui.screens.authentication.createProfile

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.serj.recommend.android.R
import com.serj.recommend.android.RecommendRoutes
import com.serj.recommend.android.common.ext.isBioValid
import com.serj.recommend.android.common.ext.isGenderValid
import com.serj.recommend.android.common.ext.isNameValid
import com.serj.recommend.android.common.ext.isNicknameValid
import com.serj.recommend.android.model.collections.User
import com.serj.recommend.android.services.AccountService
import com.serj.recommend.android.services.LogService
import com.serj.recommend.android.services.StorageService
import com.serj.recommend.android.services.model.Response
import com.serj.recommend.android.ui.components.snackbar.SnackbarManager
import com.serj.recommend.android.ui.screens.RecommendViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class CreateProfileViewModel @Inject constructor(
    logService: LogService,
    private val accountService: AccountService,
    private val storageService: StorageService
) : RecommendViewModel(logService) {

    private val currentUserId = mutableStateOf<String?>(null)

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
    private val userImageUri
        get() = uiState.value.userImageUri

    var isGenderOptionsExpanded by mutableStateOf(false)
        private set
    val genderOptions = listOf("Male", "Female", "Prefer not to say")

    var isDataPickerShown by mutableStateOf(false)
        private set

    var isStartedValidation by mutableStateOf(false)
        private set
    val isErrors = mutableStateMapOf(
        "name" to false,
        "nickname" to false,
        "bio" to false,
        "dateOfBirth" to false,
        "gender" to false
    )

    init {
        launchCatching {
            currentUserId.value = accountService.currentUid
        }
    }

    fun onNameValueChange(input: String) {
        uiState.value = uiState.value.copy(name = input)
        if (isStartedValidation) {
            isErrors["name"] = !name.isNameValid()
        }
    }

    fun onNicknameValueChange(input: String) {
        uiState.value = uiState.value.copy(nickname = input)
        if (isStartedValidation) {
            isErrors["nickname"] = !nickname.isNicknameValid()
        }
    }

    fun onBioValueChange(input: String) {
        uiState.value = uiState.value.copy(bio = input)
        if (isStartedValidation) {
            isErrors["bio"] = !bio.isBioValid()
        }
    }

    fun onDateOfBirthValueChange(input: Date) {
        uiState.value = uiState.value.copy(dateOfBirth = input)
        if (isStartedValidation) {
            isErrors["dateOfBirth"] = dateOfBirth == null
        }
    }

    fun onGenderValueChange(input: String) {
        uiState.value = uiState.value.copy(gender = input)
        if (isStartedValidation) {
            isErrors["gender"] = !gender.isGenderValid()
        }
    }

    fun onProfileImageUriValueChange(input: Uri) {
        uiState.value = uiState.value.copy(userImageUri = input)
    }

    fun onGenderOptionsClick() {
        isGenderOptionsExpanded = true
    }

    fun onGenderDismissRequest() {
        isGenderOptionsExpanded = false
    }

    fun onDatePickerClick() {
        isDataPickerShown = true
    }

    fun onDatePickerDismissRequest() {
        isDataPickerShown = false
    }

    fun onProfileImageUriDisable() {
        uiState.value = uiState.value.copy(userImageUri = null)
    }

    fun onCreateProfileClick(
        context: Context,
        clearAndOpen: (String) -> Unit
    ) {
        isStartedValidation = true
        var isValid = true

        if (!name.isNameValid()) {
            isErrors["name"] = true
            isValid = false
        }
        if (!nickname.isNicknameValid()) {
            isErrors["nickname"] = true
            isValid = false
        }
        if (!bio.isBioValid()) {
            isErrors["bio"] = true
            isValid = false
        }
        if (!gender.isGenderValid()) {
            isErrors["gender"] = true
            isValid = false
        }
        if (dateOfBirth == null) {
            isErrors["dateOfBirth"] = true
            isValid = false
        }

        if (isValid) {
            launchCatching {
                if (currentUserId.value != null) {
                    val user = User(
                        uid = currentUserId.value!!,
                        name = name,
                        nickname = nickname,
                        bio = bio,
                        dateOfBirth = dateOfBirth
                    )
                    val uploadUserResponse = storageService.uploadUser(user)
                    if (uploadUserResponse is Response.Success) {
                        if (userImageUri != null) {
                            val uploadUserPhotoResponse = storageService.uploadUserPhoto(
                                userId = currentUserId.value!!,
                                uri = userImageUri!!,
                                context = context
                            )
                            if (uploadUserPhotoResponse !is Response.Success) {
                                SnackbarManager.showMessage(R.string.create_account_uploading_photo_error)
                            }
                        }
                        clearAndOpen(RecommendRoutes.MainScreen.name)
                    } else {
                        SnackbarManager.showMessage(R.string.create_account_uploading_user_info_error)
                    }
                } else {
                    SnackbarManager.showMessage(R.string.create_account_uploading_user_info_error)
                }
            }
        }
    }
}