package com.serj.recommend.android.ui.screens.main.home.components.contentItems

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.serj.recommend.android.R
import com.serj.recommend.android.model.Recommendation

@Composable
fun HorizontalCategoryItem(
    modifier: Modifier = Modifier,
    recommendationId: String?,
    title: String?,
    creator: String?,
    cover: Bitmap?,
    openScreen: (String) -> Unit,
    onRecommendationClick: ((String) -> Unit, Recommendation) -> Unit
) {
    Column(
        modifier = modifier
            .height(230.dp)
            .width(265.dp)
            .padding(end = 8.dp)
            .clickable {
                if (recommendationId != null) {
                    onRecommendationClick(
                        openScreen,
                        Recommendation(id = recommendationId)
                    )
                }
            }
    ) {
        if (cover != null) {
            Image(
                modifier = modifier
                    .height(160.dp)
                    .width(265.dp)
                    .padding(bottom = 5.dp)
                    .clip(RoundedCornerShape(5.dp)),
                bitmap = cover.asImageBitmap(),
                contentDescription = title,
                contentScale = ContentScale.Crop
            )
        } else {
            Image(
                modifier = modifier
                    .height(160.dp)
                    .width(265.dp)
                    .padding(bottom = 5.dp)
                    .clip(RoundedCornerShape(5.dp)),
                painter = painterResource(id = R.drawable.gradient),
                contentDescription = title,
                contentScale = ContentScale.Crop
            )
        }

        Text(
            text = title ?: "loading",
            color = Color.Black,
            fontSize = 14.sp,
            lineHeight = 1.2.em,
            maxLines = 2,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = creator ?: "loading",
            color = Color.Black,
            fontSize = 12.sp,
            maxLines = 2,
            lineHeight = 1.2.em
        )
    }
}