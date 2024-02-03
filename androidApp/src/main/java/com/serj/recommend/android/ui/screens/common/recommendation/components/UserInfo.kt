package com.serj.recommend.android.ui.screens.common.recommendation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.google.firebase.storage.StorageReference
import com.serj.recommend.android.R

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun UserInfo(
    modifier: Modifier = Modifier,
    photoReference: StorageReference?,
    nickname: String
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        if (photoReference != null) {
            GlideImage(
                modifier = Modifier
                    .padding(end = 10.dp)
                    .size(28.dp)
                    .clip(CircleShape),
                model = photoReference,
                contentDescription = "User's photo"
            )
        } else {
            Image(
                modifier = Modifier
                    .padding(end = 10.dp)
                    .size(28.dp)
                    .clip(CircleShape),
                painter = painterResource(id = R.drawable.no_user_photo),
                contentDescription = "User's photo",
                contentScale = ContentScale.Crop
            )
        }

        Text(
            modifier = Modifier.align(Alignment.CenterVertically),
            text = buildAnnotatedString {
                append("by ")
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                    append("@$nickname")
                }
            },
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center,
            color = Color.White,
            fontSize = 12.sp,
            maxLines = 1
        )
    }
}

@Preview
@Composable
fun UserInfoPreview() {
    UserInfo(
        photoReference = null,
        nickname = "preview"
    )
}