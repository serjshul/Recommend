package com.serj.recommend.android.ui.screens.home.categories.recommendations

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest

//import com.serj.recommend.android.ArticleActivity


@Composable
fun VerticalRectangleRecItem(
    modifier: Modifier = Modifier,
    content: HashMap<String, String>
) {
    Column(
        modifier = Modifier
            .clickable {
//                context.startActivity(Intent(context, ArticleActivity::class.java))
            }
    ) {
        AsyncImage(
            modifier = Modifier
                .height(240.dp)
                .width(150.dp)
                .clip(RoundedCornerShape(10.dp))
                .padding(bottom = 5.dp),
            model = ImageRequest.Builder(LocalContext.current)
                .data("gs://recommend-27827.appspot.com/recommendations/covers/cover_Peggy_gou_Nanana.jpg")
                .crossfade(true)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )

        /*
        Image(
            modifier = Modifier
                .height(240.dp)
                .width(150.dp)
                .clip(RoundedCornerShape(10.dp))
                .padding(bottom = 5.dp),
            painter = painterResource(id = R.drawable.cover_book_haruki_murakami_norwegian_forest),
            contentDescription = "book.title",
            contentScale = ContentScale.Crop
        )

         */

        Text(
            text = content["title"]!!,
            color = Color.Black,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = content["creator"]!!,
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