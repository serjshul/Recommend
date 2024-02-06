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
import androidx.hilt.navigation.compose.hiltViewModel
import com.serj.recommend.android.model.User
import com.serj.recommend.android.ui.screens.common.recommendation.components.HeaderBackground
import com.serj.recommend.android.ui.screens.main.newRecommendation.components.NewRecommendationDescription
import com.serj.recommend.android.ui.screens.main.newRecommendation.components.NewRecommendationHeader
import com.serj.recommend.android.ui.screens.main.newRecommendation.components.NewRecommendationParagraph
import com.serj.recommend.android.ui.screens.main.newRecommendation.components.NewRecommendationQuote
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
        paragraphs = viewModel.paragraphs,
        isParagraphsEnabled = viewModel.isParagraphsEnabled,
        quote = viewModel.quote,
        isQuoteEnabled = viewModel.isQuoteEnabled,
        onTitleValueChange = viewModel::onTitleValueChange,
        onTypeValueChange = viewModel::onTypeValueChange,
        onCreatorValueChange = viewModel::onCreatorValueChange,
        onTagsValueChange = viewModel::onTagsValueChange,
        onYearValueChange = viewModel::onYearValueChange,
        onDescriptionValueChange = viewModel::onDescriptionValueChange,
        enableParagraph = viewModel::enableParagraph,
        disableParagraph = viewModel::disableParagraph,
        changeCurrentParagraph = viewModel::changeCurrentParagraph,
        onParagraphTitleValueChange = viewModel::onParagraphTitleValueChange,
        onParagraphTextValueChange = viewModel::onParagraphTextValueChange,
        onQuoteValueChange = viewModel::onQuoteValueChange,
        enableQuote = viewModel::enableQuote,
        disableQuote = viewModel::disableQuote
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
    paragraphs: List<Map<String, String>>,
    isParagraphsEnabled: List<Boolean>,
    quote: String,
    isQuoteEnabled: Boolean,
    onTitleValueChange: (String) -> Unit,
    onTypeValueChange: (String) -> Unit,
    onCreatorValueChange: (String) -> Unit,
    onTagsValueChange: (String) -> Unit,
    onYearValueChange: (String) -> Unit,
    onDescriptionValueChange: (String) -> Unit,
    enableParagraph: (Int) -> Unit,
    disableParagraph: (Int) -> Unit,
    changeCurrentParagraph: (Int) -> Unit,
    onParagraphTitleValueChange: (String) -> Unit,
    onParagraphTextValueChange: (String) -> Unit,
    onQuoteValueChange: (String) -> Unit,
    enableQuote: () -> Unit,
    disableQuote: () -> Unit
) {
    if (currentUser != null) {
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
                            title = title,
                            type = type,
                            creator = creator,
                            tags = tags,
                            year = year,
                            photoReference = currentUser.photoReference,
                            nickname = currentUser.nickname,
                            onTitleValueChange = onTitleValueChange,
                            onTypeValueChange = onTypeValueChange,
                            onCreatorValueChange = onCreatorValueChange,
                            onTagsValueChange = onTagsValueChange,
                            onYearValueChange = onYearValueChange
                        )
                    }

                    item {
                        NewRecommendationDescription(
                            description = description,
                            onDescriptionValueChange = onDescriptionValueChange
                        )
                    }

                    items(paragraphs.size) {index ->
                        NewRecommendationParagraph(
                            index = index,
                            enabled = isParagraphsEnabled[index],
                            title = paragraphs[index]["title"]!!,
                            //imageReference = null,
                            //videoReference = null,
                            text = paragraphs[index]["text"]!!,
                            enableParagraph = enableParagraph,
                            disableParagraph = disableParagraph,
                            changeCurrentParagraph = changeCurrentParagraph,
                            onTitleValueChange = onParagraphTitleValueChange,
                            onTextValueChange = onParagraphTextValueChange
                        )
                    }

                    item {
                        NewRecommendationQuote(
                            quote = quote,
                            enabled = isQuoteEnabled,
                            onQuoteValueChange = onQuoteValueChange,
                            enableQuote = enableQuote,
                            disableQuote = disableQuote
                        )
                    }
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
        title = "",
        type = "",
        creator = "",
        tags = "",
        year = "",
        description = "",
        paragraphs = listOf(),
        isParagraphsEnabled = listOf(),
        quote = "",
        isQuoteEnabled = false,
        onTitleValueChange = { },
        onTypeValueChange = { },
        onCreatorValueChange = { },
        onTagsValueChange = { },
        onYearValueChange = { },
        onDescriptionValueChange = { },
        enableParagraph = { },
        disableParagraph = { },
        changeCurrentParagraph = { },
        onParagraphTitleValueChange = { },
        onParagraphTextValueChange = { },
        onQuoteValueChange = { },
        enableQuote = { },
        disableQuote = { },
    )
}