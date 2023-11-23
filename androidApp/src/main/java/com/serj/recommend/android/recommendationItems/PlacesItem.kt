package com.serj.recommend.android.recommendationItems

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
fun PlacesItem(place: PlacesItemData) {
    Column (
        modifier = Modifier
            .padding(top = 5.dp, bottom = 15.dp)
    ) {
        Image(
            modifier = Modifier
                .height(160.dp)
                .width(270.dp)
                .clip(RoundedCornerShape(10.dp)),
            painter = painterResource(id = place.cover),
            contentDescription = place.title,
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.size(5.dp))

        Text(
            text = place.title,
            color = Color.Black,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )

        Row {
            Text(
                text = place.type,
                color = Color.Black,
                fontSize = 12.sp
            )

            Spacer(modifier = Modifier.size(10.dp))

            Text(
                text = place.location,
                color = colorResource(id = R.color.tigereye),
                fontSize = 12.sp
            )
        }
    }
}

data class PlacesItemData(
    var title: String,
    var type: String,
    var location: String,
    var cover: Int
)