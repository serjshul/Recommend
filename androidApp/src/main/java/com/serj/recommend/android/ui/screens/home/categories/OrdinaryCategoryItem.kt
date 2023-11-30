package com.serj.recommend.android.ui.screens.home.categories

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.serj.recommend.android.R
import com.serj.recommend.android.ui.screens.home.categories.recommendations.BookItemData
import com.serj.recommend.android.ui.screens.home.categories.recommendations.VerticalRectangleRecItem


@Composable
fun OrdinaryCategoryItem(
    title: String,
    data: List<BookItemData>
) {
    Column {
        Text(
            modifier = Modifier
                .padding(start = 15.dp, top = 15.dp, end = 15.dp),
            text = title,
            color = colorResource(id = R.color.apple_red),
            fontSize = 26.sp,
            maxLines = 2,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.size(5.dp))

        LazyRow {
            items(data.size) {i ->
                Spacer(modifier = Modifier.size(15.dp))
                //musicItem(data[i])
                //mediaItem(data[i])
                //placesItem(data[i])
                VerticalRectangleRecItem(data[i])
            }
            item {
                Spacer(modifier = Modifier.size(15.dp))
            }
        }
    }
}