package com.serj.recommend.android.ui.screens.home.categories.recommendations

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.serj.recommend.android.model.Recommendation

//import com.serj.recommend.android.ArticleActivity


@Composable
fun SquareRecItem(
    music: MusicItemData,
    openScreen: (String) -> Unit,
    onRecommendationClick: ((String) -> Unit, Recommendation) -> Unit
) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .clickable {
                onRecommendationClick(
                    openScreen,
                    Recommendation(id = "4s5fTvgODBQPRKYOHkkc")
                )
            }
    ) {
        Image(
            modifier = Modifier
                .height(160.dp)
                .width(160.dp)
                .clip(RoundedCornerShape(10.dp)),
            painter = painterResource(id = music.cover),
            contentDescription = music.title,
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.size(5.dp))

        Text(
            text = music.title,
            color = Color.Black,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = music.musician,
            color = Color.Black,
            fontSize = 12.sp
        )
    }
}

data class MusicItemData(
    var title: String,
    var musician: String,
    var cover: Int
)