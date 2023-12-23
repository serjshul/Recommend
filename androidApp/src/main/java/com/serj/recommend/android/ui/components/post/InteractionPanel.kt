package com.serj.recommend.android.ui.components.post

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.serj.recommend.android.R
import com.serj.recommend.android.ui.styles.White

@Composable
fun InteractionPanel(
    modifier: Modifier = Modifier,
    views: String,
    likes: String,
    comments: String,
    reposts: String
) {
    var isLiked by rememberSaveable { mutableStateOf(false) }

    Row(
        modifier = modifier.fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Image(
                modifier = Modifier
                    .padding(end = 2.dp)
                    .clickable { isLiked = !isLiked },
                painter = if (isLiked) painterResource(id = R.drawable.icon_liked)
                else painterResource(id = R.drawable.icon_unliked),
                contentDescription = if (isLiked) "liked"
                else "unliked",
                contentScale = ContentScale.Crop
            )

            Text(
                text = likes,
                color = Color.Gray,
                fontSize = 14.sp
            )
        }

        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                modifier = Modifier.padding(end = 4.dp),
                painter = painterResource(id = R.drawable.icon_comment),
                contentDescription = "comment",
                contentScale = ContentScale.Crop
            )

            Text(
                text = comments,
                color = Color.Gray,
                fontSize = 14.sp
            )
        }

        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                modifier = Modifier.padding(end = 2.dp),
                painter = painterResource(id = R.drawable.icon_repost),
                contentDescription = "repost",
                contentScale = ContentScale.Crop
            )

            Text(
                text = reposts,
                color = Color.Gray,
                fontSize = 14.sp
            )
        }

        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                modifier = Modifier.padding(end = 2.dp),
                painter = painterResource(id = R.drawable.icon_views),
                contentDescription = "views",
                contentScale = ContentScale.Crop
            )

            Text(
                text = views,
                color = Color.Gray,
                fontSize = 14.sp
            )
        }
    }
}

@Preview
@Composable
fun InteractionPanelPreview() {
    InteractionPanel(
        modifier = Modifier
            .background(White)
            .padding(15.dp),
        views = "531",
        likes = "185",
        comments = "24",
        reposts = "13"
    )
}