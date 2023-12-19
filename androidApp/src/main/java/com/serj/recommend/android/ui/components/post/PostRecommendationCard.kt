package com.serj.recommend.android.ui.components.post

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.serj.recommend.android.model.Recommendation
import com.serj.recommend.android.ui.components.loadingIndicators.SmallLoadingIndicator
import com.serj.recommend.android.ui.styles.LightGray
import com.serj.recommend.android.ui.styles.White

@Composable
fun PostRecommendationCard(
    modifier: Modifier = Modifier,
    background: Bitmap?,
    title: String?,
    creator: String?,
    recommendationId: String?,
    openScreen: (String) -> Unit,
    onRecommendationClick: ((String) -> Unit, Recommendation) -> Unit
) {
    if (title != null && creator != null) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(top = 5.dp)
                .clip(RoundedCornerShape(5.dp))
                .clickable {
                    if (recommendationId != null) {
                        onRecommendationClick(
                            openScreen,
                            Recommendation(id = recommendationId)
                        )
                    }
                }
        ) {
            if (background != null) {
                Image(
                    modifier = Modifier
                        .fillMaxSize()
                        .blur(radius = 3.dp),
                    bitmap = background.asImageBitmap(),
                    contentDescription = "background",
                    contentScale = ContentScale.Crop
                )
                Column(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = title,
                        color = White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = creator,
                        color = White,
                        fontSize = 16.sp
                    )
                }
            } else {
                SmallLoadingIndicator(
                    modifier = Modifier.fillMaxSize(),
                    backgroundColor = LightGray
                )
            }
        }
    }
}