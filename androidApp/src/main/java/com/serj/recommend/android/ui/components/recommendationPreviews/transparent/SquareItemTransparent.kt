package com.serj.recommend.android.ui.components.recommendationPreviews.transparent

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.serj.recommend.android.ui.styles.primary

@Composable
fun SquareItemTransparent(
    modifier: Modifier = Modifier,
    recommendationId: String?,
    title: String?,
    creator: String?,
    type: String?,
    tags: List<String>,
    coverReference: StorageReference?,
    openScreen: (String) -> Unit,
    onRecommendationClick: ((String) -> Unit, Recommendation) -> Unit
) {
    if (title != null && creator != null) {
        Column(
            modifier = modifier
                .height(300.dp)
                .width(200.dp)
        ) {
            CustomGlideImage(
                modifier = Modifier
                    .padding(bottom = 2.dp)
                    .size(200.dp)
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
                    .padding(bottom = 2.dp),
                text = "$type   /   ${tags.joinToString(separator = " & ")}",
                color = primary,
                maxLines = 1,
                fontSize = 12.sp,
                lineHeight = 1.2.em,
                fontWeight = FontWeight.Bold,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Start
            )

            Text(
                modifier = Modifier
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
                maxLines = 2,
                fontWeight = FontWeight.Bold,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = creator,
                color = Color.Black,
                fontSize = 14.sp,
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
        modifier = Modifier.background(Color.White),
        recommendationId = "",
        title = "Kyle (i found you)",
        creator = "Fred again..",
        type = "Music",
        tags = listOf("Deep House", "Progressive House", "UK Bass"),
        coverReference = null,
        openScreen = { },
        onRecommendationClick = { _: (String) -> Unit, _: Recommendation -> }
    )
}