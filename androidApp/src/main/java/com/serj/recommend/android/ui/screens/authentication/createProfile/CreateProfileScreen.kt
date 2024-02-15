package com.serj.recommend.android.ui.screens.authentication.createProfile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.serj.recommend.android.ui.screens.authentication.createProfile.components.CreateProfileDatePicker
import com.serj.recommend.android.ui.screens.authentication.createProfile.components.CreateProfileInput
import com.serj.recommend.android.ui.screens.authentication.createProfile.components.CreateProfileList
import com.serj.recommend.android.ui.screens.authentication.createProfile.components.CreateProfileTopBar
import com.serj.recommend.android.ui.screens.authentication.createProfile.components.CreateProfileUserPhoto
import java.util.Date

@Composable
fun CreateProfileScreen(
    modifier: Modifier = Modifier,
    clearAndOpen: (String) -> Unit,
    viewModel: CreateProfileViewModel = hiltViewModel()
) {
    CreateProfileContent(
        modifier = modifier,
        uiState = viewModel.uiState.value,
        backgroundColor = viewModel.backgroundColor,
        isDataPickerShown = viewModel.isDataPickerShown,
        isGenderOptionsExpanded = viewModel.isGenderOptionsExpanded,
        genderOptions = viewModel.genderOptions,
        onNameValueChange = viewModel::onNameValueChange,
        onNicknameValueChange = viewModel::onNicknameValueChange,
        onBioValueChange = viewModel::onBioValueChange,
        onDateOfBirthValueChange = viewModel::onDateOfBirthValueChange,
        onGenderValueChange = viewModel::onGenderValueChange,
        onDatePickerClick = viewModel::onDatePickerClick,
        onDatePickerDismissRequest = viewModel::onDatePickerDismissRequest,
        onGenderOptionsClick = viewModel::onGenderOptionsClick,
        onGenderDismissRequest = viewModel::onGenderDismissRequest,
        onCreateProfileClick = { viewModel.onCreateProfileClick(clearAndOpen) }
    )
}

@Composable
fun CreateProfileContent(
    modifier: Modifier = Modifier,
    uiState: CreateProfileUiState,
    backgroundColor: Color,
    isDataPickerShown: Boolean,
    isGenderOptionsExpanded: Boolean,
    genderOptions: List<String>,
    onNameValueChange: (String) -> Unit,
    onNicknameValueChange: (String) -> Unit,
    onBioValueChange: (String) -> Unit,
    onDateOfBirthValueChange: (Date) -> Unit,
    onGenderValueChange: (String) -> Unit,
    onDatePickerClick: () -> Unit,
    onDatePickerDismissRequest: () -> Unit,
    onGenderOptionsClick: () -> Unit,
    onGenderDismissRequest: () -> Unit,
    onCreateProfileClick: () -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            CreateProfileTopBar(
                isDataValid = true,
                onCreateProfileClick = onCreateProfileClick
            )
        },
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(backgroundColor)
        ) {
            item {
                CreateProfileUserPhoto(
                    userPhotoUri = null,
                    onAddUserPhoto = { }
                )
            }


            item {
                CreateProfileInput(
                    text = uiState.name,
                    label = "Name",
                    isError = false,
                    maxLines = 1,
                    onValueChange = onNameValueChange
                )
            }

            item {
                CreateProfileInput(
                    text = uiState.nickname,
                    label = "Nickname",
                    isError = false,
                    maxLines = 1,
                    onValueChange = onNicknameValueChange
                )
            }

            item {
                CreateProfileInput(
                    text = uiState.bio,
                    label = "Bio",
                    isError = false,
                    maxLines = 3,
                    onValueChange = onBioValueChange
                )
            }
            
            item {
                Row {
                    CreateProfileDatePicker(
                        dateOfBirth = uiState.dateOfBirth.toString(),
                        label = "Date of birth",
                        isError = false,
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
                        isError = false,
                        expanded = isGenderOptionsExpanded,
                        onValueChange = onGenderValueChange,
                        onOptionsClick = onGenderOptionsClick,
                        onDismissRequest = onGenderDismissRequest,
                        modifier = Modifier.weight(1f)
                    )
                }

            }

        }
    }
}

@Preview
@Composable
fun CreateProfileScreenPreview() {
    CreateProfileContent(
        backgroundColor = Color.White,
        uiState = CreateProfileUiState(),
        isDataPickerShown = false,
        isGenderOptionsExpanded = false,
        genderOptions = listOf("item1", "item2"),
        onNameValueChange = { },
        onNicknameValueChange = { },
        onBioValueChange = { },
        onDateOfBirthValueChange = { },
        onDatePickerClick = { },
        onDatePickerDismissRequest = { },
        onGenderValueChange = { },
        onGenderOptionsClick = { },
        onGenderDismissRequest = { },
        onCreateProfileClick = { }
    )
}