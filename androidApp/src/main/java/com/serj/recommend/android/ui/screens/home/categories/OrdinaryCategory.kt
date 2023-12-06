package com.serj.recommend.android.ui.screens.home.categories

import android.graphics.Bitmap
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.serj.recommend.android.R
import com.serj.recommend.android.model.Category
import com.serj.recommend.android.model.Recommendation
import com.serj.recommend.android.ui.screens.home.categories.recommendations.SquareRecItem


@Composable
fun OrdinaryCategory(
    modifier: Modifier = Modifier,
    covers: List<Bitmap?>?,
    category: Category,
    openScreen: (String) -> Unit,
    onRecommendationClick: ((String) -> Unit, Recommendation) -> Unit
) {
    Column {
        Text(
            modifier = Modifier
                .padding(start = 15.dp, top = 15.dp, end = 15.dp),
            text = category.title,
            color = colorResource(id = R.color.apple_red),
            fontSize = 26.sp,
            maxLines = 2,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.size(5.dp))

        LazyRow {
            items(category.content.size) {i ->
                Spacer(modifier = Modifier.size(15.dp))

                if (category.content[i]["coverType"] == "Square") {
                    SquareRecItem(
                        recommendationId = category.content[i]["recommendationId"] ?: "",
                        title = category.content[i]["title"] ?: "",
                        creator = category.content[i]["creator"] ?: "",
                        type = category.content[i]["type"] ?: "",
                        cover = covers?.get(i),
                        openScreen = openScreen,
                        onRecommendationClick = onRecommendationClick
                    )
                }

                //mediaItem(data[i])
                //placesItem(data[i])

                /*
                VerticalRectangleRecItem(
                    content = category.content[i]
                )
                */
            }
            item {
                Spacer(modifier = Modifier.size(15.dp))
            }
        }
    }
}