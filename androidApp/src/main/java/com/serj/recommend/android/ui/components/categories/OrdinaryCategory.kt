package com.serj.recommend.android.ui.components.categories

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.serj.recommend.android.R
import com.serj.recommend.android.common.ext.categoryItemsInterval
import com.serj.recommend.android.common.ext.itemsInterval
import com.serj.recommend.android.common.ext.screenPaddingsInner
import com.serj.recommend.android.model.collections.Category
import com.serj.recommend.android.model.collections.Recommendation
import com.serj.recommend.android.ui.components.loadingIndicators.SmallLoadingIndicator
import com.serj.recommend.android.ui.components.recommendationPreviews.ItemsShapes
import com.serj.recommend.android.ui.components.recommendationPreviews.transparent.HorizontalItemTransparent
import com.serj.recommend.android.ui.components.recommendationPreviews.transparent.SquareItemTransparent
import com.serj.recommend.android.ui.components.recommendationPreviews.transparent.VerticalItemTransparent
import com.serj.recommend.android.ui.components.snackbar.SnackbarManager
import com.serj.recommend.android.ui.screens.main.home.HomeViewModel
import com.serj.recommend.android.ui.screens.main.home.components.ShowAllButton

@Composable
fun OrdinaryCategory(
    modifier: Modifier = Modifier,
    category: Category,
    openScreen: (String) -> Unit,
    onRecommendationClick: ((String) -> Unit, Recommendation) -> Unit,
    onCategoryClick: ((String) -> Unit, String) -> Unit
) {
    var isLoading by rememberSaveable { mutableStateOf(true) }
    var currentItemsAmount = 0

    if (category.title != null) {
        Column(
            modifier = modifier.itemsInterval()
        ) {
            Text(
                modifier = Modifier
                    .screenPaddingsInner()
                    .padding(start = 4.dp, bottom = 10.dp)
                    .clickable { onCategoryClick(openScreen, category.id) },
                text = category.title,
                color = Color.Black,
                fontSize = 22.sp,
                maxLines = 2,
                fontWeight = FontWeight.Bold
            )

            LazyRow(
                verticalAlignment = Alignment.CenterVertically
            ) {
                item {
                    Spacer(modifier = Modifier.size(15.dp))
                }

                items(category.content) {
                    when (category.coverType) {
                        ItemsShapes.square.name -> {
                            SquareItemTransparent(
                                modifier = Modifier.categoryItemsInterval(),
                                title = it.title,
                                creator = it.creator,
                                type = it.type,
                                tags = it.tags,
                                coverReference = it.coverReference,
                                recommendationId = it.id,
                                openScreen = openScreen,
                                onRecommendationClick = onRecommendationClick
                            )
                        }

                        ItemsShapes.horizontal.name -> {
                            HorizontalItemTransparent(
                                modifier = Modifier.categoryItemsInterval(),
                                title = it.title,
                                creator = it.creator,
                                type = it.type,
                                tags = it.tags,
                                coverReference = it.coverReference,
                                recommendationId = it.id,
                                openScreen = openScreen,
                                onRecommendationClick = onRecommendationClick
                            )
                        }

                        ItemsShapes.vertical.name -> {
                            VerticalItemTransparent(
                                modifier = Modifier.categoryItemsInterval(),
                                title = it.title,
                                creator = it.creator,
                                type = it.type,
                                tags = it.tags,
                                coverReference = it.coverReference,
                                recommendationId = it.id,
                                openScreen = openScreen,
                                onRecommendationClick = onRecommendationClick
                            )
                        }

                        else -> {
                            SnackbarManager.showMessage(R.string.error_cover_type)
                        }
                    }
                    currentItemsAmount++
                    isLoading = currentItemsAmount < HomeViewModel.AMOUNT_THRESHOLD
                }

                if (isLoading) {
                    if (currentItemsAmount != 0) {
                        item {
                            SmallLoadingIndicator(
                                modifier = Modifier
                                    .width(150.dp)
                                    .fillParentMaxHeight(),
                                backgroundColor = White
                            )
                        }
                    }
                } else {
                    item {
                        ShowAllButton(
                            modifier = Modifier.padding(start = 35.dp, end = 50.dp, bottom = 30.dp),
                            categoryId = category.id,
                            openScreen = openScreen,
                            onCategoryClick = onCategoryClick
                        )
                    }
                }
            }
        }
    }
}