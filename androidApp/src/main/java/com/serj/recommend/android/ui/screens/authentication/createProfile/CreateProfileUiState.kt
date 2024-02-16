package com.serj.recommend.android.ui.screens.authentication.createProfile

import android.net.Uri
import java.util.Date

data class CreateProfileUiState(
    val name: String = "",
    val nickname: String = "",
    val bio: String = "",
    val dateOfBirth: Date? = null,
    val gender: String = "",
    val profileImageUri: Uri? = null
)