package com.serj.recommend.android.ui.screens.authentication.createProfile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.serj.recommend.android.ui.screens.authentication.createProfile.components.CreateProfileInput
import com.serj.recommend.android.ui.screens.authentication.createProfile.components.CreateProfileTopBar
import com.serj.recommend.android.ui.screens.authentication.createProfile.components.CreateProfileUserPhoto

@Composable
fun CreateProfileScreen(
    modifier: Modifier = Modifier,
    viewModel: CreateProfileViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState

    CreateProfileContent(
        modifier = modifier,
        backgroundColor = viewModel.backgroundColor
    )
}

@Composable
fun CreateProfileContent(
    modifier: Modifier = Modifier,
    backgroundColor: Color,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            CreateProfileTopBar(
                 isDataValid = true
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
                    text = "",
                    label = "Name",
                    isError = false,
                    maxLines = 1,
                    onValueChange = { }
                )
            }

            item {
                CreateProfileInput(
                    text = "",
                    label = "Nickname",
                    isError = false,
                    maxLines = 1,
                    onValueChange = { }
                )
            }

            item {
                CreateProfileInput(
                    text = "",
                    label = "Bio",
                    isError = false,
                    maxLines = 3,
                    onValueChange = { }
                )
            }

        }
    }
}

@Preview
@Composable
fun CreateProfileScreenPreview() {
    CreateProfileContent(
        backgroundColor = Color.White
    )
}