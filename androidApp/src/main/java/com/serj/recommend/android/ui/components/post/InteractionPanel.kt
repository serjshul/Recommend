package com.serj.recommend.android.ui.components.post

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.IconButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.serj.recommend.android.R
import com.serj.recommend.android.ui.styles.Black
import com.serj.recommend.android.ui.styles.White

@Composable
fun InteractionPanel(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 10.dp)
    ) {
        IconButton(
            modifier = Modifier
                .size(35.dp)
                .padding(end = 10.dp),
            onClick = { }
        ) {
            Icon(
                ImageVector.vectorResource(id = R.drawable.icon_like_bordered_1),
                contentDescription = "Like",
                tint = White
            )
        }

        IconButton(
            modifier = Modifier
                .size(30.dp)
                .padding(top = 7.dp, end = 6.dp),
            onClick = { }
        ) {
            Icon(
                ImageVector.vectorResource(id = R.drawable.icon_comment_1),
                contentDescription = "Comment",
                tint = White
            )
        }

        IconButton(
            modifier = Modifier
                .size(30.dp)
                .padding(top = 7.dp),
            onClick = { }
        ) {
            Icon(
                ImageVector.vectorResource(id = R.drawable.icon_repost_1),
                contentDescription = "Repost",
                tint = White
            )
        }
    }
}

@Preview
@Composable
fun InteractionPanelPreview() {
    InteractionPanel(
        modifier = Modifier
            .background(Black)
            .padding(15.dp)
    )
}