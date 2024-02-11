package com.serj.recommend.android.ui.screens.main.newRecommendation

import android.content.Context
import android.net.Uri
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
import com.serj.recommend.android.ui.screens.main.newRecommendation.components.NewRecommendationCover
import com.serj.recommend.android.ui.screens.main.newRecommendation.components.NewRecommendationDescription
import com.serj.recommend.android.ui.screens.main.newRecommendation.components.NewRecommendationHeader
import com.serj.recommend.android.ui.screens.main.newRecommendation.components.NewRecommendationHeaderBackground
import com.serj.recommend.android.ui.screens.main.newRecommendation.components.NewRecommendationParagraph
import com.serj.recommend.android.ui.screens.main.newRecommendation.components.NewRecommendationQuote
import com.serj.recommend.android.ui.screens.main.newRecommendation.components.NewRecommendationTopBar

// TODO: Read this https://refactoring.guru/smells/long-parameter-list,
//  in compose functions, sometimes it can be more,
//  but anyway we must only ask yourself - maybe we can make less parameters,
//  Sometimes we can pack few parameters in to some class (for example, pack params by logic)...

// TODO: Why we need NewRecommendationScreenContent? maybe its more good just make what u do
//  inside of NewRecommendationScreenContent, right in NewRecommendationScreen? (call reasons)
@Composable
fun NewRecommendationScreen(
    viewModel: NewRecommendationViewModel = hiltViewModel()
) {
    NewRecommendationScreenContent(
        currentUser = viewModel.currentUser,
        isNewRecommendationValid = viewModel.isNewRecommendationValid,
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
        coverType = viewModel.coverType,
        backgroundImageUri = viewModel.backgroundImageUri.value,
        coverImageUri = viewModel.coverImageUri.value,
        onRecommendButtonCheck = viewModel::onRecommendButtonCheck,
        onTitleValueChange = viewModel::onTitleValueChange,
        onTypeValueChange = viewModel::onTypeValueChange,
        onCreatorValueChange = viewModel::onCreatorValueChange,
        onTagsValueChange = viewModel::onTagsValueChange,
        onYearValueChange = viewModel::onYearValueChange,
        onDescriptionValueChange = viewModel::onDescriptionValueChange,
        enableParagraph = viewModel::enableParagraph,
        disableParagraph = viewModel::disableParagraph,
        changeCurrentParagraph = viewModel::changeCurrentParagraphIndex,
        onParagraphTitleValueChange = viewModel::onParagraphTitleValueChange,
        onParagraphTextValueChange = viewModel::onParagraphTextValueChange,
        onQuoteValueChange = viewModel::onQuoteValueChange,
        enableQuote = viewModel::enableQuote,
        disableQuote = viewModel::disableQuote,
        onCoverTypeValueChange = viewModel::onCoverTypeValueChange,
        onAddBackgroundImage = viewModel::onAddBackgroundImage,
        onRemoveBackgroundImage = viewModel::onRemoveBackgroundImage,
        onAddCoverImage = viewModel::onAddCoverImage,
        onRemoveCoverImage = viewModel::onRemoveCoverImage
    )
}

@Composable
fun NewRecommendationScreenContent(
    modifier: Modifier = Modifier,
    currentUser: User?,
    isNewRecommendationValid: Boolean,
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
    coverType: String,
    backgroundImageUri: Uri?,
    coverImageUri: Uri?,
    onRecommendButtonCheck: (Context) -> Unit,
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
    disableQuote: () -> Unit,
    onCoverTypeValueChange: (String) -> Unit,
    onAddBackgroundImage: (Uri) -> Unit,
    onRemoveBackgroundImage: () -> Unit,
    onAddCoverImage: (Uri) -> Unit,
    onRemoveCoverImage: () -> Unit
) {
    if (currentUser != null) {
        Scaffold(
            topBar = {
                NewRecommendationTopBar(
                    isValid = isNewRecommendationValid,
                    onRecommendButtonCheck = onRecommendButtonCheck
                )
            },
            containerColor = Color.White,
            modifier = modifier
        ) {
            Box(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
            ) {
                NewRecommendationHeaderBackground(
                    modifier = Modifier
                        .align(Alignment.TopCenter),
                    backgroundImageUri = backgroundImageUri
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
                            backgroundImageUri = backgroundImageUri,
                            photoReference = currentUser.photoReference,
                            nickname = currentUser.nickname,
                            onTitleValueChange = onTitleValueChange,
                            onTypeValueChange = onTypeValueChange,
                            onCreatorValueChange = onCreatorValueChange,
                            onTagsValueChange = onTagsValueChange,
                            onYearValueChange = onYearValueChange,
                            onAddBackgroundImage = onAddBackgroundImage,
                            onRemoveBackgroundImage = onRemoveBackgroundImage
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

                    item {
                        NewRecommendationCover(
                            coverType = coverType,
                            coverImageUri = coverImageUri,
                            onCoverTypeChange = onCoverTypeValueChange,
                            onAddCoverImage = onAddCoverImage,
                            onRemoveCoverImage = onRemoveCoverImage
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun NewRecommendationScreenContentPreview() {
    NewRecommendationScreenContent(
        currentUser = User(
            photoReference = null,
            nickname = "nickname"
        ),
        isNewRecommendationValid = false,
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
        coverType = "",
        backgroundImageUri = null,
        coverImageUri = null,
        onRecommendButtonCheck = { },
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
        onCoverTypeValueChange = { },
        onAddBackgroundImage = { },
        onRemoveBackgroundImage = { },
        onAddCoverImage = { },
        onRemoveCoverImage = { }
    )
}