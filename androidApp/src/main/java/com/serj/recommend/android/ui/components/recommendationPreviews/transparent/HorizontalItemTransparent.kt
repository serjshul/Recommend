package com.serj.recommend.android.ui.components.recommendationPreviews.transparent

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.google.firebase.storage.StorageReference
import com.serj.recommend.android.common.ext.recommendationCoverShape
import com.serj.recommend.android.model.Recommendation
import com.serj.recommend.android.ui.components.media.CustomGlideImage

@Composable
fun HorizontalItemTransparent(
    modifier: Modifier = Modifier,
    recommendationId: String?,
    title: String?,
    creator: String?,
    coverReference: StorageReference?,
    isOnPager: Boolean = false,
    openScreen: (String) -> Unit,
    onRecommendationClick: ((String) -> Unit, Recommendation) -> Unit
) {
    if (title != null && creator != null) {
        Column(
            modifier = if (isOnPager) modifier
                .height(240.dp)
                .fillMaxWidth()
                .padding(start = 5.dp, end = 5.dp)
            else modifier
                .height(270.dp)
                .width(330.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CustomGlideImage(
                modifier = Modifier
                    .height(200.dp)
                    .fillMaxWidth()
                    .recommendationCoverShape()
                    .clickable {
                        if (recommendationId != null) {
                            onRecommendationClick(
                                openScreen,
                                Recommendation(id = recommendationId)
                            )
                        }
                    },
                reference = coverReference
            )

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        if (recommendationId != null) {
                            onRecommendationClick(
                                openScreen,
                                Recommendation(id = recommendationId)
                            )
                        }
                    },
                text = title,
                color = Color.Black,
                fontSize = 14.sp,
                lineHeight = 1.2.em,
                maxLines = if (isOnPager) 1 else 2,
                fontWeight = FontWeight.Bold,
                overflow = TextOverflow.Ellipsis,
                textAlign = if (isOnPager) TextAlign.Center else TextAlign.Start
            )

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = creator,
                color = Color.Black,
                fontSize = 12.sp,
                lineHeight = 1.2.em,
                maxLines = if (isOnPager) 1 else 2,
                overflow = TextOverflow.Ellipsis,
                textAlign = if (isOnPager) TextAlign.Center else TextAlign.Start
            )
        }
    }
}

@Preview
@Composable
fun HorizontalItemTransparentPreview() {
    HorizontalItemTransparent(
        recommendationId = "",
        title = "Title Title Title Title Title Title Title Title Title Title Title Title " +
                "Title Title Title Title Title Title",
        creator = "Creator Creator Creator Creator Creator Creator Creator Creator Creator " +
                "Creator Creator Creator Creator",
        coverReference = null,
        openScreen = { },
        isOnPager = false,
        onRecommendationClick = { _: (String) -> Unit, _: Recommendation -> }
    )
}