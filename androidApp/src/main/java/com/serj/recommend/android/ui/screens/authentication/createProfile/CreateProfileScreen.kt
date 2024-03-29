package com.serj.recommend.android.ui.screens.authentication.createProfile

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.serj.recommend.android.R
import com.serj.recommend.android.common.getDateOfBirth
import com.serj.recommend.android.ui.screens.authentication.createProfile.components.CreateProfileButton
import com.serj.recommend.android.ui.screens.authentication.createProfile.components.CreateProfileDatePicker
import com.serj.recommend.android.ui.screens.authentication.createProfile.components.CreateProfileInput
import com.serj.recommend.android.ui.screens.authentication.createProfile.components.CreateProfileList
import com.serj.recommend.android.ui.screens.authentication.createProfile.components.CreateProfileUserPhoto
import java.util.Date

@Composable
fun CreateProfileScreen(
    modifier: Modifier = Modifier,
    clearAndOpen: (String) -> Unit,
    viewModel: CreateProfileViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    CreateProfileContent(
        modifier = modifier,
        uiState = viewModel.uiState.value,
        isErrors = viewModel.isErrors,
        isDataPickerShown = viewModel.isDataPickerShown,
        isGenderOptionsExpanded = viewModel.isGenderOptionsExpanded,
        genderOptions = viewModel.genderOptions,
        onProfileImageUriValueChange = viewModel::onProfileImageUriValueChange,
        onNameValueChange = viewModel::onNameValueChange,
        onNicknameValueChange = viewModel::onNicknameValueChange,
        onBioValueChange = viewModel::onBioValueChange,
        onDateOfBirthValueChange = viewModel::onDateOfBirthValueChange,
        onGenderValueChange = viewModel::onGenderValueChange,
        onDatePickerClick = viewModel::onDatePickerClick,
        onDatePickerDismissRequest = viewModel::onDatePickerDismissRequest,
        onGenderOptionsClick = viewModel::onGenderOptionsClick,
        onGenderDismissRequest = viewModel::onGenderDismissRequest,
        onProfileImageUriDisable = viewModel::onProfileImageUriDisable,
        onCreateProfileClick = { viewModel.onCreateProfileClick(context, clearAndOpen) }
    )
}

@Composable
fun CreateProfileContent(
    modifier: Modifier = Modifier,
    uiState: CreateProfileUiState,
    isErrors: Map<String, Boolean>,
    isDataPickerShown: Boolean,
    isGenderOptionsExpanded: Boolean,
    genderOptions: List<String>,
    onProfileImageUriValueChange: (Uri) -> Unit,
    onNameValueChange: (String) -> Unit,
    onNicknameValueChange: (String) -> Unit,
    onBioValueChange: (String) -> Unit,
    onDateOfBirthValueChange: (Date) -> Unit,
    onGenderValueChange: (String) -> Unit,
    onDatePickerClick: () -> Unit,
    onDatePickerDismissRequest: () -> Unit,
    onGenderOptionsClick: () -> Unit,
    onGenderDismissRequest: () -> Unit,
    onProfileImageUriDisable: () -> Unit,
    onCreateProfileClick: () -> Unit
) {
    Scaffold(
        modifier =
            if (isDataPickerShown)
                modifier.blur(radius = 8.dp)
            else
                modifier
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(Color.White)
        ) {
            CreateProfileUserPhoto(
                userPhotoUri = uiState.userImageUri,
                onAddUserPhoto = onProfileImageUriValueChange,
                onProfileImageUriDisable = onProfileImageUriDisable,
                modifier = Modifier
                    .weight(4f)
            )

            Column(
                modifier = Modifier.weight(6f)
            ) {
                CreateProfileInput(
                    text = uiState.name,
                    label = "Name",
                    supportingText = R.string.create_account_name_error,
                    isError = isErrors["name"]!!,
                    maxLines = 1,
                    onValueChange = onNameValueChange
                )
                CreateProfileInput(
                    text = uiState.nickname,
                    label = "Nickname",
                    supportingText = R.string.create_account_nickname_error,
                    isError = isErrors["nickname"]!!,
                    maxLines = 1,
                    onValueChange = onNicknameValueChange
                )
                CreateProfileInput(
                    text = uiState.bio,
                    label = "Bio",
                    supportingText = R.string.create_account_bio_error,
                    isError = isErrors["bio"]!!,
                    maxLines = 5,
                    onValueChange = onBioValueChange
                )
                Row {
                    CreateProfileDatePicker(
                        dateOfBirth = getDateOfBirth(uiState.dateOfBirth),
                        label = "Date of birth",
                        supportingText = R.string.create_account_date_of_birth_error,
                        isError = isErrors["dateOfBirth"]!!,
                        showDatePicker = isDataPickerShown,
                        onValueChange = onDateOfBirthValueChange,
                        onIconClick = onDatePickerClick,
                        onDismissRequest = onDatePickerDismissRequest,
                        modifier = Modifier.weight(1f)
                    )
                    CreateProfileList(
                        chosenOption = uiState.gender,
                        options = genderOptions,
                        label = "Gender",
                        supportingText = R.string.create_account_gender_error,
                        isError = isErrors["gender"]!!,
                        expanded = isGenderOptionsExpanded,
                        onValueChange = onGenderValueChange,
                        onOptionsClick = onGenderOptionsClick,
                        onDismissRequest = onGenderDismissRequest,
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            CreateProfileButton(
                text = R.string.create_account_button,
                action = onCreateProfileClick,
                modifier = Modifier
                    .padding(start = 15.dp, end = 15.dp, bottom = 20.dp)
                    .fillMaxWidth()
            )
        }
    }
}

@Preview
@Composable
fun CreateProfileScreenPreview() {
    CreateProfileContent(
        uiState = CreateProfileUiState(),
        isErrors = hashMapOf(
            "name" to true,
            "nickname" to true,
            "bio" to true,
            "dateOfBirth" to true,
            "gender" to true
        ),
        isDataPickerShown = false,
        isGenderOptionsExpanded = false,
        genderOptions = listOf("item1", "item2"),
        onProfileImageUriValueChange = { },
        onNameValueChange = { },
        onNicknameValueChange = { },
        onBioValueChange = { },
        onDateOfBirthValueChange = { },
        onDatePickerClick = { },
        onDatePickerDismissRequest = { },
        onGenderValueChange = { },
        onGenderOptionsClick = { },
        onGenderDismissRequest = { },
        onProfileImageUriDisable = { },
        onCreateProfileClick = { }
    )
}