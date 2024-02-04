package com.serj.recommend.android.ui.screens.main.newRecommendation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
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
import com.serj.recommend.android.ui.screens.common.recommendation.components.Description
import com.serj.recommend.android.ui.screens.common.recommendation.components.HeaderBackground
import com.serj.recommend.android.ui.screens.main.newRecommendation.components.NewRecommendationHeader
import com.serj.recommend.android.ui.screens.main.newRecommendation.components.NewRecommendationTopBar

@Composable
fun NewRecommendationScreen(
    viewModel: NewRecommendationViewModel = hiltViewModel()
) {
    NewRecommendationScreenContent(
        title = viewModel.title,
        type = viewModel.type,
        creator = viewModel.creator,
        tags = viewModel.tags,
        year = viewModel.year,
        onTitleValueChange = viewModel::onTitleValueChange,
        onCreatorValueChange = viewModel::onCreatorValueChange,
        onYearValueChange = viewModel::onYearValueChange
    )
}

@Composable
fun NewRecommendationScreenContent(
    modifier: Modifier = Modifier,
    title: String,
    type: String,
    creator: String,
    tags: List<String>,
    year: String,
    onTitleValueChange: (String) -> Unit,
    onCreatorValueChange: (String) -> Unit,
    onYearValueChange: (String) -> Unit
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
                    NewRecommendationHeader(
                        modifier = Modifier
                            .padding(top = 50.dp),
                        title = title,
                        creator = creator,
                        tags = tags,
                        year = year,
                        photoReference = null,
                        nickname = "nickname",
                        onTitleValueChange = onTitleValueChange,
                        onCreatorValueChange = onCreatorValueChange,
                        onYearValueChange = onYearValueChange
                    )
                }

                item {
                    Description(
                        modifier = Modifier.height(100.dp),
                        description = "recommendation.description"
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
        title = "The White Lotus",
        type = "Series",
        creator = "Mike White",
        tags = listOf("Comedy", "Drama"),
        year = "2021",
        onTitleValueChange = { },
        onCreatorValueChange = { },
        onYearValueChange = { }
    )
}