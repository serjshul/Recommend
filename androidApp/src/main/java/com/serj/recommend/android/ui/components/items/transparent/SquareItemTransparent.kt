package com.serj.recommend.android.ui.components.items.transparent

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.serj.recommend.android.R
import com.serj.recommend.android.common.ext.recommendationCoverShape
import com.serj.recommend.android.model.Recommendation

@Composable
fun SquareItemTransparent(
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
                modifier = Modifier
                    .size(160.dp)
                    .recommendationCoverShape(),
                bitmap = cover.asImageBitmap(),
                contentDescription = title,
                contentScale = ContentScale.Crop
            )
        } else {
            Image(
                modifier = Modifier
                    .size(160.dp)
                    .recommendationCoverShape(),
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
            fontWeight = FontWeight.Bold
        )

        Text(
            text = creator ?: "loading",
            color = Color.Black,
            fontSize = 12.sp,
            lineHeight = 1.2.em
        )
    }
}