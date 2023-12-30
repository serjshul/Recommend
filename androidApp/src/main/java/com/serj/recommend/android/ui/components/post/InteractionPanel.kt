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
import com.serj.recommend.android.ui.styles.TexasHeatwave
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
                .padding(end = 8.dp)
                .size(39.dp),
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
                .padding(top = 3.dp, end = 8.dp)
                .size(34.dp),
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
                .padding(top = 3.dp)
                .size(34.dp),
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
            .background(TexasHeatwave)
            .padding(15.dp)
    )
}