package com.serj.recommend.android.ui.screens.common.recommendation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.serj.recommend.android.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecommendationTopBar(
    modifier: Modifier = Modifier,
    type: String,
    isBackgroundHidden: Boolean,
    popUpScreen: () -> Unit
) {
    val containerColor =
        if (!isBackgroundHidden)
            Color.Transparent
        else
            Color.White
    val titleContentColor =
        if (!isBackgroundHidden)
            Color.White
        else
            Color.Black

    CenterAlignedTopAppBar(
        modifier = modifier,
        colors =  if (!isBackgroundHidden)
            TopAppBarDefaults.topAppBarColors(
                containerColor = containerColor,
                titleContentColor = titleContentColor
            )
        else
            TopAppBarDefaults.topAppBarColors(
                containerColor = containerColor,
                titleContentColor = titleContentColor,
            ),
        navigationIcon = {
            IconButton(onClick = { popUpScreen() }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    tint = titleContentColor,
                    contentDescription = "Localized description"
                )
            }
        },
        title = {
            Text(
                text = type,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                fontSize = 16.sp
            )
        },
        actions = {
            IconButton(onClick = { /* do something */ }) {
                Icon(
                    ImageVector.vectorResource(
                        id = R.drawable.icon_unsaved
                    ),
                    tint = titleContentColor,
                    contentDescription = "Localized description"
                )
            }
        },
    )
}

@Preview
@Composable
fun RecommendationTopBarPreview() {
    RecommendationTopBar(
        type = "Type",
        isBackgroundHidden = false,
        popUpScreen = { }
    )
}