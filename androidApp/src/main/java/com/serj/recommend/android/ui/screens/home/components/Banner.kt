package com.serj.recommend.android.ui.screens.home.components

import android.graphics.Bitmap
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.serj.recommend.android.R

@Composable
fun Banner(
    modifier: Modifier = Modifier,
    title: String,
    description: String,
    background: Bitmap?
) {
    var sizeImage by remember { mutableStateOf(IntSize.Zero) }
    // TODO: save like / unlike by user
    var isSaved by rememberSaveable { mutableStateOf(false) }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(520.dp)
            .padding(bottom = 30.dp)
    ) {
        if (background != null) {
            Image(
                modifier = Modifier
                    .matchParentSize()
                    .onGloballyPositioned { sizeImage = it.size },
                bitmap = background.asImageBitmap(),
                contentDescription = title,
                contentScale = ContentScale.Crop
            )
            Box(
                modifier = modifier
                    .matchParentSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Color.Black),
                            startY = sizeImage.height.toFloat() / 6,
                            endY = sizeImage.height.toFloat()
                        )
                    )
            )
        } else {
            Image(
                modifier = Modifier.matchParentSize(),
                painter = painterResource(id = R.drawable.gradient),
                contentDescription = "background_gradient",
                contentScale = ContentScale.Crop
            )
        }

        Column(
            modifier = modifier
                .matchParentSize()
                .padding(bottom = 10.dp),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = modifier.padding(start = 5.dp, end = 5.dp, bottom = 5.dp),
                text = title,
                color = Color.White,
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            Text(
                modifier = modifier.padding(start = 5.dp, end = 5.dp, bottom = 10.dp),
                text = description,
                color = Color.White,
                fontSize = 14.sp,
                textAlign = TextAlign.Center
            )

            Row(
                modifier = modifier.padding(bottom = 9.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                FilledTonalButton(
                    modifier = modifier.padding(end = 5.dp),
                    colors = ButtonDefaults.filledTonalButtonColors(
                        containerColor = Color.White
                    ),
                    onClick = {
                        // TODO: recommend on click
                    }
                ) {
                    Text(
                        text = "Read",
                        color = Color.Black,
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center
                    )
                }

                OutlinedIconButton(
                    modifier = modifier.padding(end = 2.dp),
                    colors = IconButtonDefaults.outlinedIconButtonColors(
                        contentColor = Color.White
                    ),
                    border = BorderStroke(1.dp, Color.Gray),
                    onClick = {
                        isSaved = !isSaved
                    }
                ) {
                    Icon(
                        imageVector = if (isSaved) Icons.Outlined.Favorite
                        else Icons.Outlined.FavoriteBorder,
                        contentDescription = "Save"
                    )
                }

                OutlinedIconButton(
                    colors = IconButtonDefaults.outlinedIconButtonColors(
                        contentColor = Color.White
                    ),
                    border = BorderStroke(1.dp, Color.Gray),
                    onClick = {
                        // TODO: recommend on click
                    }
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Clear,
                        contentDescription = "Remove"
                    )
                }
            }
        }
    }
}