package com.serj.recommend.android.ui.screens.authentication.createProfile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.serj.recommend.android.ui.screens.authentication.createProfile.components.CreateProfileInput
import com.serj.recommend.android.ui.styles.outline

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

        },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(backgroundColor)
        ) {
            CreateProfileInput(
                text = "",
                label = "Title",
                placeholder = "Title",
                textColor = Color.Black,
                labelColor = outline,
                placeHolderColor = Color.Gray,
                borderColor = outline,
                isError = false,
                maxLines = 1,
                onValueChange = { }
            )
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