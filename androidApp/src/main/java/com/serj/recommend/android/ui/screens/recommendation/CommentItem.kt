package com.serj.recommend.android.ui.screens.recommendation

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.serj.recommend.android.R

@Composable
fun CommentItem(
    modifier: Modifier = Modifier
) {
    val photo = R.drawable.profile_photo
    val user = "@lalala"
    val text = "Wow-wow-wow! Wow-wow-wow! Wow-wow-wow! Wow-wow-wow! Wow-wow-wow! Wow-wow-wow!"
    val date = "04/12/2023 22:04"

    var isLiked by rememberSaveable { mutableStateOf(false) }
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp)
    ) {
        Image(
            modifier = Modifier
                .padding(end = 7.dp)
                .size(40.dp)
                .clip(CircleShape),
            painter = painterResource(id = photo),
            contentDescription = "photo",
            contentScale = ContentScale.Crop,
            alignment = Alignment.TopCenter
        )

        Column(
            modifier = modifier.width(screenWidth - 100.dp)
        ) {
            Text(
                modifier = modifier.padding(bottom = 3.dp),
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append(user)
                    }
                    append(" $text")
                },
                fontSize = 14.sp,
                lineHeight = 1.2.em
            )

            Text(
                text = date,
                color = Color.Gray,
                fontSize = 11.sp,
                lineHeight = 1.2.em
            )
        }

        Image(
            modifier = modifier
                .padding(top = 20.dp)
                .size(20.dp)
                .clickable { isLiked = !isLiked }
                .align(Alignment.Top),
            painter = if (isLiked) painterResource(id = R.drawable.icon_liked)
            else painterResource(id = R.drawable.icon_unliked),
            contentDescription = if (isLiked) "liked"
            else "unliked",
            contentScale = ContentScale.Crop
        )
    }
}