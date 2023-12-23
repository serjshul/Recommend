package com.serj.recommend.android.ui.screens.main.home.components.categoryItems

import android.graphics.Bitmap
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.serj.recommend.android.R
import com.serj.recommend.android.common.ext.mainScreenItems
import com.serj.recommend.android.model.Category
import com.serj.recommend.android.model.CategoryItem
import com.serj.recommend.android.model.Recommendation
import com.serj.recommend.android.ui.COVER_HORIZONTAL
import com.serj.recommend.android.ui.COVER_SQUARE
import com.serj.recommend.android.ui.COVER_VERTICAL
import com.serj.recommend.android.ui.components.items.transparent.HorizontalItemTransparent
import com.serj.recommend.android.ui.components.items.transparent.SquareItemTransparent
import com.serj.recommend.android.ui.components.items.transparent.VerticalItemTransparent
import com.serj.recommend.android.ui.components.snackbar.SnackbarManager
import com.serj.recommend.android.ui.screens.main.home.components.ShowAllButton

@Composable
fun OrdinaryCategory(
    modifier: Modifier = Modifier,
    items: List<CategoryItem?>?,
    covers: List<Bitmap?>?,
    category: Category,
    openScreen: (String) -> Unit,
    onRecommendationClick: ((String) -> Unit, Recommendation) -> Unit,
    onCategoryClick: ((String) -> Unit, String) -> Unit
) {
    if (!items.isNullOrEmpty()) {
        Column(
            modifier = modifier.padding(bottom = 20.dp)
        ) {
            Text(
                modifier = Modifier
                    .padding(start = 15.dp, end = 15.dp, bottom = 10.dp)
                    .clickable { onCategoryClick(openScreen, category.id) },
                text = category.title,
                color = Color.Black,
                fontSize = 22.sp,
                maxLines = 2,
                fontWeight = FontWeight.Bold,
            )

            LazyRow(
                verticalAlignment = Alignment.CenterVertically
            ) {
                item {
                    Spacer(modifier = Modifier.size(15.dp))
                }

                items(items.size) {i ->
                    when (category.coverType) {
                        COVER_SQUARE -> {
                            SquareItemTransparent(
                                modifier = Modifier.mainScreenItems(),
                                title = items[i]?.title,
                                creator = items[i]?.creator,
                                cover = covers?.getOrNull(i),
                                recommendationId = items[i]?.recommendationId,
                                openScreen = openScreen,
                                onRecommendationClick = onRecommendationClick
                            )
                        }

                        COVER_HORIZONTAL -> {
                            HorizontalItemTransparent(
                                modifier = Modifier.mainScreenItems(),
                                title = items[i]?.title,
                                creator = items[i]?.creator,
                                cover = covers?.getOrNull(i),
                                recommendationId = items[i]?.recommendationId,
                                openScreen = openScreen,
                                onRecommendationClick = onRecommendationClick
                            )
                        }

                        COVER_VERTICAL -> {
                            VerticalItemTransparent(
                                modifier = Modifier.mainScreenItems(),
                                title = items[i]?.title,
                                creator = items[i]?.creator,
                                cover = covers?.getOrNull(i),
                                recommendationId = items[i]?.recommendationId,
                                openScreen = openScreen,
                                onRecommendationClick = onRecommendationClick
                            )
                        }

                        else -> {
                            SnackbarManager.showMessage(R.string.error_cover_type)
                        }
                    }
                }

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