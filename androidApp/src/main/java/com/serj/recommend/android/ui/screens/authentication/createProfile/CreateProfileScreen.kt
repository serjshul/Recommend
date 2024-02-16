package com.serj.recommend.android.ui.screens.authentication.createProfile

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun CreateProfileScreen(
    modifier: Modifier = Modifier,
    viewModel: CreateProfileViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState

    CreateProfileContent(
        modifier = modifier,
    )
}

@Composable
fun CreateProfileContent(
    modifier: Modifier = Modifier
) {

}