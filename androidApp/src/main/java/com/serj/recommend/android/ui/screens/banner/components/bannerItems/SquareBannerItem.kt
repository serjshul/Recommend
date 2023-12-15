package com.serj.recommend.android.ui.screens.banner.components.bannerItems

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.serj.recommend.android.model.Recommendation
import com.serj.recommend.android.ui.components.loadingIndicators.SmallLoadingIndicator
import com.serj.recommend.android.ui.screens.recommendation.components.toColor

@Composable
fun SquareBannerItem(
    modifier: Modifier = Modifier,
    recommendationId: String?,
    title: String?,
    creator: String?,
    description: String?,
    color: String?,
    cover: Bitmap?,
    openScreen: (String) -> Unit,
    onRecommendationClick: ((String) -> Unit, Recommendation) -> Unit
) {
    ElevatedCard(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable {
                if (recommendationId != null) {
                    onRecommendationClick(
                        openScreen,
                        Recommendation(id = recommendationId)
                    )
                }
            },
        elevation = CardDefaults.cardElevation(
            defaultElevation = 5.dp
        ),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        if (title != null) {
            Row(
                modifier = Modifier.padding(10.dp)
            ) {
                if (cover != null) {
                    Image(
                        modifier = Modifier
                            .size(115.dp)
                            .padding(end = 10.dp)
                            .clip(RoundedCornerShape(5.dp)),
                        bitmap = cover.asImageBitmap(),
                        contentDescription = title,
                        contentScale = ContentScale.Crop
                    )
                } else {
                    SmallLoadingIndicator(
                        modifier = Modifier
                            .size(115.dp)
                            .padding(end = 10.dp)
                            .clip(RoundedCornerShape(5.dp))
                            .background(Color.LightGray)
                    )
                }

                Column(
                    verticalArrangement = Arrangement.Top
                ) {
                    Text(
                        text = title,
                        color = color?.toColor() ?: Color.Black,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )

                    if (creator != null && description != null) {
                        Text(
                            modifier = Modifier.padding(bottom = 4.dp),
                            text = creator,
                            color = Color.Gray,
                            fontSize = 13.sp,
                        )

                        Text(
                            text = description,
                            color = Color.Black,
                            fontSize = 13.sp,
                            maxLines = 4,
                            lineHeight = 1.4.em,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
        } else {
            SmallLoadingIndicator(
                modifier = Modifier
                    .height(135.dp)
                    .fillMaxWidth()
            )
        }
    }
}