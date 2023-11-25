package com.serj.recommend.android.ui.categories

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.serj.recommend.android.R
import com.serj.recommend.android.ui.recommendationItems.MusicItem
import com.serj.recommend.android.ui.recommendationItems.MusicItemData


@Composable
fun CrossingCategoryItem(title: String, data: List<MusicItemData>, context: Context) {
    Box(
        modifier = Modifier
            //.height(430.dp)
            .padding(top = 10.dp, bottom = 10.dp)
    ) {
        Image(
            modifier = Modifier
                .height(240.dp)
                .padding(start = 15.dp, top = 25.dp, end = 15.dp)
                .clip(RoundedCornerShape(15.dp)),
            painter = painterResource(id = R.drawable.category_crossing),
            contentDescription = "category",
            contentScale = ContentScale.Crop
        )

        Text(
            modifier = Modifier
                .padding(start = 30.dp, end = 15.dp),
            text = title,
            color = colorResource(id = R.color.apple_red),
            fontSize = 30.sp,
            maxLines = 2,
            fontWeight = FontWeight.Bold
        )

        LazyRow(
            modifier = Modifier
                .padding(top = 160.dp)
        ) {
            items(data.size) {i ->
                if (i == 0) {
                    Spacer(modifier = Modifier.size(30.dp))
                } else {
                    Spacer(modifier = Modifier.size(15.dp))
                }
                MusicItem(data[i], context)
                //mediaItem(data[i])
                //placesItem(data[i])
            }
            item {
                Spacer(modifier = Modifier.size(15.dp))
            }
        }
    }
}