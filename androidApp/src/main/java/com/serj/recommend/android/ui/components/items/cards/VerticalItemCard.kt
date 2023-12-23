package com.serj.recommend.android.ui.components.items.cards

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.serj.recommend.android.common.ext.toColor
import com.serj.recommend.android.model.Recommendation
import com.serj.recommend.android.ui.components.loadingIndicators.SmallLoadingIndicator
import com.serj.recommend.android.ui.styles.Black
import com.serj.recommend.android.ui.styles.Gray
import com.serj.recommend.android.ui.styles.LightGray
import com.serj.recommend.android.ui.styles.White

@Composable
fun VerticalItemCard(
    modifier: Modifier = Modifier,
    recommendationId: String?,
    title: String?,
    creator: String?,
    description: String?,
    color: String? = "#000000",
    cover: Bitmap?,
    openScreen: (String) -> Unit,
    onRecommendationClick: ((String) -> Unit, Recommendation) -> Unit
) {
    if (title != null && creator != null && description != null) {
        ElevatedCard(
            modifier = modifier
                .height(268.dp)
                .fillMaxWidth()
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
            colors = CardDefaults.cardColors(containerColor = White)
        ) {
            Row(
                modifier = Modifier.padding(10.dp)
            ) {
                if (cover != null) {
                    Image(
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(165.dp)
                            .padding(end = 10.dp)
                            .clip(RoundedCornerShape(5.dp)),
                        bitmap = cover.asImageBitmap(),
                        contentDescription = title,
                        contentScale = ContentScale.Crop
                    )
                } else {
                    SmallLoadingIndicator(
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(165.dp)
                            .padding(end = 10.dp)
                            .clip(RoundedCornerShape(5.dp)),
                        backgroundColor = LightGray
                    )
                }

                Column(
                    verticalArrangement = Arrangement.Top
                ) {
                    Text(
                        text = title,
                        color = color?.toColor() ?: Black,
                        fontSize = 15.sp,
                        maxLines = 1,
                        fontWeight = FontWeight.Bold,
                        overflow = TextOverflow.Ellipsis
                    )

                    Text(
                        modifier = Modifier.padding(bottom = 4.dp),
                        text = creator,
                        color = Gray,
                        maxLines = 1,
                        fontSize = 13.sp,
                        overflow = TextOverflow.Ellipsis
                    )

                    Text(
                        text = description,
                        color = Black,
                        fontSize = 13.sp,
                        maxLines = 11,
                        lineHeight = 1.4.em,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun VerticalItemCardPreview() {
    VerticalItemCard(
        recommendationId = "",
        title = "Title",
        creator = "Creator",
        description = "description description description description description " +
                "description description description description description description " +
                "description description description description description description " +
                "description description description description description description " +
                "description description description description description description " +
                "description description description description description ",
        color = null,
        cover = null,
        openScreen = { },
        onRecommendationClick = { _: (String) -> Unit, _: Recommendation -> }
    )
}