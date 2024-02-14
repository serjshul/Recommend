package com.serj.recommend.android.ui.screens.common.recommendation.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.serj.recommend.android.ui.styles.secondary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecommendationTopBar(
    modifier: Modifier = Modifier,
    title: String,
    type: String,
    color: Color?,
    isBackgroundHidden: Boolean,
    popUpScreen: () -> Unit
) {
    val containerColor =
        if (!isBackgroundHidden)
            Color.Transparent
        else
            Color.White
    val titleContentColor = when {
        !isBackgroundHidden -> Color.White
        color != null -> color
        else -> secondary
    }
    val shadowElevation: Dp by animateDpAsState(
        if (isBackgroundHidden) 6.dp else 0.dp,
        label = "shadowElevation"
    )

    Surface(
        modifier = modifier,
        color = Color.Transparent,
        shadowElevation = shadowElevation
    ) {
        CenterAlignedTopAppBar(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = containerColor,
                titleContentColor = titleContentColor
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
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(
                        modifier = Modifier
                            .padding(15.dp, 0.dp)
                            .fillMaxWidth()
                            .align(Alignment.Center),
                        text = if (isBackgroundHidden) title else type,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        fontSize = 16.sp,
                        lineHeight = 1.2.em,
                        maxLines = 2
                    )
                }
            }
            // TODO: add the save action
            /*
            actions = {
                IconButton(onClick = { /* do something */ }) {
                    Icon(
                        ImageVector.vectorResource(id = R.drawable.icon_unsaved),
                        tint = titleContentColor,
                        contentDescription = "Save"
                    )
                }
            }
            
             */
        )
    }
}

@Preview
@Composable
fun RecommendationTopBarPreview() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Gray)
    ) {
        RecommendationTopBar(
            modifier = Modifier.align(Alignment.TopCenter),
            title = "Title",
            type = "Type",
            color = Color.Red,
            isBackgroundHidden = true,
            popUpScreen = { }
        )
    }
}