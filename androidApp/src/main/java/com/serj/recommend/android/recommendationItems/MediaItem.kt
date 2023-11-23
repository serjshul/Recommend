package com.serj.recommend.android.recommendationItems

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.serj.recommend.android.R


@Composable
fun MediaItem(media: MediaItemData) {
    Column {
        Image(
            modifier = Modifier
                .height(160.dp)
                .width(300.dp)
                .clip(RoundedCornerShape(10.dp)),
            painter = painterResource(id = media.cover),
            contentDescription = media.title,
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.size(5.dp))

        Text(
            text = media.title,
            color = Color.Black,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )

        Row {
            Text(
                text = media.type,
                color = Color.Black,
                fontSize = 12.sp
            )

            Spacer(modifier = Modifier.size(10.dp))

            Text(
                text = media.production,
                color = colorResource(id = R.color.muesli),
                fontSize = 12.sp
            )
        }
    }
}

data class MediaItemData(
    var title: String,
    var type: String,
    var production: String,
    var cover: Int
)