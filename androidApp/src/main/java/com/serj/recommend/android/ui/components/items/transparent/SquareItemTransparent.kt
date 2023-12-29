package com.serj.recommend.android.ui.components.items.transparent

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.serj.recommend.android.common.ext.recommendationCoverShape
import com.serj.recommend.android.model.Recommendation
import com.serj.recommend.android.ui.components.loadingIndicators.SmallLoadingIndicator
import com.serj.recommend.android.ui.styles.LightGray

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
    if (title != null && creator != null) {
        Column(
            modifier = modifier
                .height(270.dp)
                .width(200.dp)
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
                        .size(200.dp)
                        .recommendationCoverShape(),
                    bitmap = cover.asImageBitmap(),
                    contentDescription = title,
                    contentScale = ContentScale.Crop
                )
            } else {
                SmallLoadingIndicator(
                    modifier = Modifier
                        .size(200.dp)
                        .clip(RoundedCornerShape(5.dp)),
                    backgroundColor = LightGray
                )
            }

            Text(
                text = title,
                color = Color.Black,
                fontSize = 14.sp,
                lineHeight = 1.2.em,
                maxLines = 2,
                fontWeight = FontWeight.Bold,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = creator,
                color = Color.Black,
                fontSize = 12.sp,
                lineHeight = 1.2.em,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Preview
@Composable
fun SquareItemTransparentPreview() {
    SquareItemTransparent(
        recommendationId = "",
        title = "Title Title Title Title Title Title Title Title Title Title Title Title",
        creator = "Creator Creator Creator Creator Creator Creator Creator Creator Creator Creator ",
        cover = null,
        openScreen = { },
        onRecommendationClick = { _: (String) -> Unit, _: Recommendation -> }
    )
}