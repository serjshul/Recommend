package com.serj.recommend.android.recommendationItems

import android.content.Context
import android.content.Intent
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.serj.recommend.android.ArticleActivity


@Composable
fun BookItem(book: BookItemData, context: Context) {
    Column(
        modifier = Modifier
            .clickable {
                context.startActivity(Intent(context, ArticleActivity::class.java))
            }
    ) {
        Image(
            modifier = Modifier
                .height(240.dp)
                .width(150.dp)
                .clip(RoundedCornerShape(10.dp)),
            painter = painterResource(id = book.cover),
            contentDescription = book.title,
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.size(5.dp))

        Text(
            text = book.title,
            color = Color.Black,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = book.author,
            color = Color.Black,
            fontSize = 12.sp
        )
    }
}

data class BookItemData(
    var title: String,
    var author: String,
    var cover: Int
)