package com.serj.recommend.android.ui.screens.main.home.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.google.firebase.storage.StorageReference
import com.serj.recommend.android.model.Banner
import com.serj.recommend.android.ui.components.media.CustomGlideImage

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun Banner(
    modifier: Modifier = Modifier,
    banner: Banner? = null,
    coverReference: StorageReference?,
    openScreen: (String) -> Unit,
    onBannerClick: ((String) -> Unit, String) -> Unit
) {
    var sizeImage by remember { mutableStateOf(IntSize.Zero) }
    // TODO: save like / unlike by user
    var isSaved by rememberSaveable { mutableStateOf(false) }

    if (banner != null) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .height(520.dp)
                .padding(bottom = 30.dp)
        ) {
            CustomGlideImage(
                modifier = Modifier
                    .fillMaxSize()
                    .onGloballyPositioned { sizeImage = it.size },
                url = coverReference
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Color.White),
                            startY = sizeImage.height.toFloat() / 7,
                            endY = sizeImage.height.toFloat()
                        )
                    )
            )

            Column(
                modifier = Modifier
                    .matchParentSize()
                    .padding(bottom = 10.dp),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier.padding(start = 10.dp, end = 10.dp, bottom = 5.dp),
                    text = banner.title ?: "loading",
                    color = Color.Black,
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )

                Text(
                    modifier = Modifier.padding(start = 10.dp, end = 10.dp, bottom = 10.dp),
                    text = banner.promo ?: "loading",
                    color = Color.Black,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center
                )

                Row(
                    modifier = Modifier.padding(bottom = 9.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    FilledTonalButton(
                        modifier = Modifier.padding(end = 5.dp),
                        colors = ButtonDefaults.filledTonalButtonColors(
                            containerColor = Color.Black
                        ),
                        onClick = {
                            if (banner.id != null) {
                                onBannerClick(
                                    openScreen,
                                    banner.id
                                )
                            }
                        }
                    ) {
                        Text(
                            text = "Read",
                            color = Color.White,
                            fontSize = 14.sp,
                            textAlign = TextAlign.Center
                        )
                    }

                    OutlinedIconButton(
                        modifier = Modifier.padding(end = 2.dp),
                        colors = IconButtonDefaults.outlinedIconButtonColors(
                            contentColor = Color.Black
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
                            contentColor = Color.Black
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
}