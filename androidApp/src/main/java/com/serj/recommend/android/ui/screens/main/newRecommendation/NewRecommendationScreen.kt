package com.serj.recommend.android.ui.screens.main.newRecommendation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.serj.recommend.android.model.User
import com.serj.recommend.android.ui.screens.common.recommendation.components.HeaderBackground
import com.serj.recommend.android.ui.screens.main.newRecommendation.components.NewRecommendationDescription
import com.serj.recommend.android.ui.screens.main.newRecommendation.components.NewRecommendationHeader
import com.serj.recommend.android.ui.screens.main.newRecommendation.components.NewRecommendationTopBar

@Composable
fun NewRecommendationScreen(
    viewModel: NewRecommendationViewModel = hiltViewModel()
) {
    NewRecommendationScreenContent(
        currentUser = viewModel.currentUser,
        title = viewModel.title,
        type = viewModel.type,
        creator = viewModel.creator,
        tags = viewModel.tags,
        year = viewModel.year,
        description = viewModel.description,
        onTitleValueChange = viewModel::onTitleValueChange,
        onCreatorValueChange = viewModel::onCreatorValueChange,
        onTagsValueChange = viewModel::onTagsValueChange,
        onYearValueChange = viewModel::onYearValueChange,
        onDescriptionValueChange = viewModel::onDescriptionValueChange
    )
}

@Composable
fun NewRecommendationScreenContent(
    modifier: Modifier = Modifier,
    currentUser: User?,
    title: String,
    type: String,
    creator: String,
    tags: String,
    year: String,
    description: String,
    onTitleValueChange: (String) -> Unit,
    onCreatorValueChange: (String) -> Unit,
    onTagsValueChange: (String) -> Unit,
    onYearValueChange: (String) -> Unit,
    onDescriptionValueChange: (String) -> Unit
) {
    Scaffold(
        topBar = {
            NewRecommendationTopBar(isValid = false)
        },
        containerColor = Color.White,
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            HeaderBackground(
                modifier = Modifier
                    .align(Alignment.TopCenter),
                color = Color.Gray,
                backgroundImageReference = null,
                backgroundVideoReference = null
            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.TopCenter)
            ) {
                item {
                    if (currentUser != null) {
                        NewRecommendationHeader(
                            modifier = Modifier
                                .padding(top = 40.dp),
                            title = title,
                            creator = creator,
                            tags = tags,
                            year = year,
                            photoReference = currentUser.photoReference,
                            nickname = currentUser.nickname,
                            onTitleValueChange = onTitleValueChange,
                            onCreatorValueChange = onCreatorValueChange,
                            onTagsValueChange = onTagsValueChange,
                            onYearValueChange = onYearValueChange
                        )
                    }
                }

                item {
                    NewRecommendationDescription(
                        description = description,
                        onDescriptionValueChange = onDescriptionValueChange
                    )
                }
            }
        }
    }


    /*

    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
        Log.v(TAG, uri.toString())
    }
    Button(
        onClick = { launcher.launch("image/*") }
    ) {
        Text(text = "select image")
    }
     */
     */
}

@Preview
@Composable
fun NewRecommendationScreenContentPreview() {
    NewRecommendationScreenContent(
        currentUser = User(
            photoReference = null,
            nickname = "nickname"
        ),
        title = "The White Lotus",
        type = "Series",
        creator = "Mike White",
        tags = "Comedy, Drama",
        year = "2021",
        description = "description description ",
        onTitleValueChange = { },
        onCreatorValueChange = { },
        onTagsValueChange = { },
        onYearValueChange = { },
        onDescriptionValueChange = { }
    )
}