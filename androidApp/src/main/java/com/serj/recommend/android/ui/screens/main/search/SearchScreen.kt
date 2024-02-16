package com.serj.recommend.android.ui.screens.main.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.serj.recommend.android.R
import com.serj.recommend.android.model.RecommendationType
import com.serj.recommend.android.model.getColor


@Composable
fun SearchScreen(
    viewModel: SearchViewModel = hiltViewModel()
) {
    SearchScreenContent(
        query = viewModel.query,
        onSearch = viewModel::onSearch,
        isSearchBarActive = viewModel.isSearchBarActive
    )
}

@Composable
private fun SearchScreenContent(
    query: MutableState<String>,
    onSearch: (String) -> Unit,
    isSearchBarActive: MutableState<Boolean>
) {
    Column {
        ScreenName()
        CustomSearchBar(
            query = query,
            onSearch = onSearch,
            isSearchBarActive = isSearchBarActive
        )
        RecommendationTypePads()
    }
}

@Composable
private fun ScreenName(
    modifier: Modifier = Modifier
) = Text(
    text = stringResource(id = R.string.navigation_title_search_screen),
    modifier = modifier,
    style = MaterialTheme.typography.titleLarge,
    color = Color.White
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CustomSearchBar(
    query: MutableState<String>,
    onSearch: (String) -> Unit,
    isSearchBarActive: MutableState<Boolean>
) {
    SearchBar(
        query = query.value,
        onQueryChange = {
            query.value = it
        },
        onSearch = onSearch,
        active = isSearchBarActive.value,
        onActiveChange = {
            isSearchBarActive.value = it
        }
    ) {

    }
}

@Composable
private fun RecommendationTypePads(
    types: List<RecommendationType> = RecommendationType.entries
) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp

    LazyVerticalGrid(
        columns = GridCells.Adaptive(
            minSize = screenWidth / 2 - 30.dp
        )
    ) {
        items(types) {
            TypePad(it)
        }
    }
}

@Composable
private fun TypePad(type: RecommendationType) {
    // TODO: maybe not use a card?
    Card(
        colors = CardDefaults.cardColors(
            containerColor = type.getColor()
        )
    ) {
        Text(text = type.name)
    }
}


@Preview
@Composable
private fun SearchScreenPreview() {
    val query =
        remember { mutableStateOf("") }
    val isSearchBarActive =
        remember { mutableStateOf(true) }

    SearchScreenContent(
        query = query,
        isSearchBarActive = isSearchBarActive,
        onSearch = {

        }
    )
}