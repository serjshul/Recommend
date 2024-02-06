package com.serj.recommend.android.ui.screens.main.newRecommendation

import android.content.ContentValues.TAG
import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
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
        onUploadImage = viewModel::onUploadImage
    )
}

@OptIn(ExperimentalGlideComposeApi::class)
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
    onRecommendButtonCheck: () -> Unit,
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
    onUploadImage: (Uri, Context) -> Unit
) {
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
        Log.v(TAG, uri.toString())
        onUploadImage(imageUri!!, context)
    }

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

                    item {
                        Column {
                            Button(
                                onClick = { launcher.launch("image/*") }
                            ) {
                                Text(text = "select image")
                            }

                            GlideImage(
                                model = imageUri,
                                contentDescription = "test"
                            )
                        }
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
        onUploadImage = { _: Uri, _: Context -> }
    )
}