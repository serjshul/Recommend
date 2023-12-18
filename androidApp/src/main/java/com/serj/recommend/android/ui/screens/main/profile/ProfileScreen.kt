package com.serj.recommend.android.ui.screens.main.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.serj.recommend.android.R

private enum class TabPage { Posts, Saved }

@Composable
fun ProfileScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Gray)
            .wrapContentSize(Alignment.Center)
    ) {
        Text(
            text = stringResource(id = R.string.navigation_title_profile_screen),
            style = MaterialTheme.typography.titleLarge,
            color = Color.White,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            textAlign = TextAlign.Center,
        )

        ProfileScreenContent()
    }
}


@Composable
fun ProfileScreenContent() {
    Column {
        ProfileInfo()
        PostsAndSaved()
    }
}

@Composable
private fun ProfileInfo() {
    Text(text = "profile info")
}

@Composable
private fun PostsAndSaved() {
    val tabPage = remember { mutableStateOf(TabPage.Posts) }
    TabBar(
        tabPage = tabPage.value,
        onTabSelected = { tabPage.value = it }
    )
//    LazyColumn {
//        items() {
//
//        }
//    }
}

@Composable
private fun TabBar(
    tabPage: TabPage,
    onTabSelected: (tabPage: TabPage) -> Unit
) = TabRow(selectedTabIndex = tabPage.ordinal) {
    HomeTab(
        title = stringResource(id = R.string.posts),
        onClick = {
            onTabSelected(TabPage.Posts)
        }
    )
    HomeTab(
        title = stringResource(id = R.string.saved),
        onClick = {
            onTabSelected(TabPage.Saved)
        }
    )
}


@Composable
private fun HomeTab(
    title: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) = Row(
    modifier = modifier
        .clickable(onClick = onClick)
        .padding(16.dp),
    horizontalArrangement = Arrangement.Center,
    verticalAlignment = Alignment.CenterVertically
) {
    Text(text = title)
}


@Preview
@Composable
fun PreviewProfileScreenContent() =
    ProfileScreenContent()
