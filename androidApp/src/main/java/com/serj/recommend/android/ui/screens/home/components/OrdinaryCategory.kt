package com.serj.recommend.android.ui.screens.home.components

import android.graphics.Bitmap
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.serj.recommend.android.model.Category
import com.serj.recommend.android.model.Recommendation
import com.serj.recommend.android.ui.screens.home.categories.recommendations.HorizontalItem
import com.serj.recommend.android.ui.screens.home.categories.recommendations.SquareItem
import com.serj.recommend.android.ui.screens.home.categories.recommendations.VerticalItem
import com.serj.recommend.android.ui.styles.AppleRed
import com.serj.recommend.android.ui.styles.Muesli

@Composable
fun OrdinaryCategory(
    modifier: Modifier = Modifier,
    covers: List<Bitmap?>?,
    category: Category,
    openScreen: (String) -> Unit,
    onRecommendationClick: ((String) -> Unit, Recommendation) -> Unit
) {
    val currentIndex = category.content.size - 1

    Column(
        modifier = modifier.padding(bottom = 20.dp)
    ) {
        Row(
            modifier = modifier.padding(start = 15.dp, end = 15.dp, bottom = 5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = modifier.weight(10f),
                text = category.title,
                color = AppleRed,
                fontSize = 26.sp,
                maxLines = 2,
                fontWeight = FontWeight.Bold,
            )

            Text(
                modifier = modifier.weight(2f),
                text = "view",
                color = Muesli,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.End
            )
        }

        LazyRow {
            items(category.content.size) {i ->
                Spacer(modifier = Modifier.size(15.dp))

                when (category.content[currentIndex - i]["coverType"]) {
                    "Square" -> {
                        SquareItem(
                            title = category.content[currentIndex - i]["title"] ?: "",
                            creator = category.content[currentIndex - i]["creator"] ?: "",
                            cover = covers?.get(currentIndex - i),
                            recommendationId = category.content[currentIndex - i]["recommendationId"] ?: "",
                            openScreen = openScreen,
                            onRecommendationClick = onRecommendationClick
                        )
                    }
                    "Horizontal" -> {
                        HorizontalItem(
                            title = category.content[currentIndex - i]["title"] ?: "",
                            creator = category.content[currentIndex - i]["creator"] ?: "",
                            cover = covers?.get(currentIndex - i),
                            recommendationId = category.content[currentIndex - i]["recommendationId"] ?: "",
                            openScreen = openScreen,
                            onRecommendationClick = onRecommendationClick
                        )
                    }
                    "Vertical" -> {
                        VerticalItem(
                            title = category.content[currentIndex - i]["title"] ?: "",
                            creator = category.content[currentIndex - i]["creator"] ?: "",
                            cover = covers?.get(currentIndex - i),
                            recommendationId = category.content[currentIndex - i]["recommendationId"] ?: "",
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
                Spacer(modifier = Modifier.size(15.dp))
            }
        }
    }
}