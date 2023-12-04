package com.serj.recommend.android.ui.screens.recommendation

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.serj.recommend.android.R

@Composable
fun Footer(
    modifier: Modifier = Modifier,
    onCommentsClick: () -> Unit
) {
    val author = "@serjshul"
    val date = "04/12/2023 21:36"
    val views = "52"
    val likes = "15"
    val comments = "3"
    val reposts = "2"
    //val comments =
    val color = "#E03038"

    Info(
        modifier = modifier,
        author = author,
        date = date
    )

    Divider(
        modifier = modifier.padding(start = 15.dp, end = 15.dp),
        thickness = 1.dp,
        color = Color.Gray
    )

    InteractionPanel(
        modifier = modifier,
        views = views,
        likes = likes,
        comments = comments,
        reposts = reposts
    )

    Column (
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 15.dp, end = 15.dp, bottom = 15.dp)
            .clickable { onCommentsClick() },
    ) {
        for (i in 0..2) {
            ShortCommentItem(user = "@lalala", comment = "wow! so cool")
        }
    }
}

@Composable
fun Info(
    modifier: Modifier = Modifier,
    author: String,
    date: String
) {
    Text(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 15.dp, end = 15.dp, bottom = 15.dp),
        text = buildAnnotatedString {
            append("by ")
            withStyle(style = SpanStyle(color = Color.Black)) {
                append(author)
            }
            append(" on $date")
        },
        color = Color.Gray,
        fontSize = 14.sp
    )
}

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
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 20.dp, bottom = 15.dp)
    ) {
        Row(
            modifier = modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                modifier = modifier
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
            modifier = modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                modifier = modifier.padding(end = 4.dp),
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
            modifier = modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                modifier = modifier.padding(end = 2.dp),
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
            modifier = modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                modifier = modifier.padding(end = 2.dp),
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

@Composable
fun ShortCommentItem(
    modifier: Modifier = Modifier,
    user: String,
    comment: String
) {
    Text(
        modifier = modifier
            .padding(bottom = 3.dp),
        text = buildAnnotatedString {
            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                append(user)
            }
            append(" $comment")
        },
        fontSize = 14.sp,
        maxLines = 2,
        lineHeight = 1.2.em,
        overflow = TextOverflow.Ellipsis
    )
}