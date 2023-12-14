package com.serj.recommend.android.ui.screens.home.components.categoryItems

import android.graphics.Bitmap
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.serj.recommend.android.model.Category
import com.serj.recommend.android.model.CategoryItem
import com.serj.recommend.android.model.Recommendation
import com.serj.recommend.android.ui.screens.home.components.categoryContentItems.HorizontalCategoryItem
import com.serj.recommend.android.ui.screens.home.components.categoryContentItems.SquareCategoryItem
import com.serj.recommend.android.ui.screens.home.components.categoryContentItems.VerticalCategoryItem

@Composable
fun OrdinaryCategory(
    modifier: Modifier = Modifier,
    items: List<CategoryItem?>?,
    covers: List<Bitmap?>?,
    category: Category,
    openScreen: (String) -> Unit,
    onRecommendationClick: ((String) -> Unit, Recommendation) -> Unit
) {
    Column(
        modifier = modifier.padding(bottom = 40.dp)
    ) {
        Text(
            modifier = modifier.padding(start = 15.dp, end = 15.dp, bottom = 10.dp),
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

            items?.let {
                items(items.size) { i ->
                    when (category.coverType) {
                        "square" -> {
                            SquareCategoryItem(
                                title = items[i]?.title,
                                creator = items[i]?.creator,
                                cover = covers?.get(i),
                                recommendationId = items[i]?.recommendationId,
                                openScreen = openScreen,
                                onRecommendationClick = onRecommendationClick
                            )
                        }
                        "horizontal" -> {
                            HorizontalCategoryItem(
                                title = items[i]?.title,
                                creator = items[i]?.creator,
                                cover = covers?.get(i),
                                recommendationId = items[i]?.recommendationId,
                                openScreen = openScreen,
                                onRecommendationClick = onRecommendationClick
                            )
                        }
                        "vertical" -> {
                            VerticalCategoryItem(
                                title = items[i]?.title,
                                creator = items[i]?.creator,
                                cover = covers?.get(i),
                                recommendationId = items[i]?.recommendationId,
                                openScreen = openScreen,
                                onRecommendationClick = onRecommendationClick
                            )
                        }
                        else -> {
                            // TODO: what else?
                        }
                    }
                }

                item {
                    Column(
                        modifier = modifier.padding(start = 35.dp, end = 50.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        OutlinedIconButton(
                            colors = IconButtonDefaults.outlinedIconButtonColors(
                                contentColor = Color.Black,
                            ),
                            border = BorderStroke(1.dp, Color.Gray),
                            onClick = {
                                // TODO: category screen
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.ArrowForward,
                                contentDescription = "forward"
                            )
                        }

                        Text(
                            text = "Show all",
                            color = Color.Black,
                            fontSize = 14.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}