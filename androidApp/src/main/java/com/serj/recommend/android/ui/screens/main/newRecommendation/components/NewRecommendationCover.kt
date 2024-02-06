package com.serj.recommend.android.ui.screens.main.newRecommendation.components

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.CrossFade
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.serj.recommend.android.ui.styles.secondary

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun NewRecommendationCover(
    modifier: Modifier = Modifier,
    coverType: String,
    coverImageUri: Uri?,
    onCoverTypeChange: (String) -> Unit,
    onAddCoverImage: (Uri) -> Unit,
    onRemoveCoverImage: () -> Unit
) {
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            onAddCoverImage(uri)
        }
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        NewRecommendationInput(
            text = coverType,
            placeholder = "Cover type",
            textColor = Color.Black,
            placeholderTextColor = Color.Gray,
            enabled = true,
            fontSize = 14.sp,
            maxLines = 1,
            textAlign = TextAlign.Start,
            modifier = Modifier.width(150.dp),
            onValueChange = onCoverTypeChange,
        )

        Box(
            modifier = Modifier.size(200.dp)
        ) {
            GlideImage(
                model = coverImageUri,
                contentDescription = "Background",
                transition = CrossFade,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            if (coverImageUri == null) {
                ElevatedButton(
                    onClick = { launcher.launch("image/*") },
                    modifier = Modifier.align(Alignment.Center),
                    colors = ButtonDefaults.elevatedButtonColors(
                        containerColor = secondary,
                        contentColor = Color.White
                    )
                ) {
                    Row {
                        Icon(
                            imageVector = Icons.Filled.Add,
                            contentDescription = "Add a cover",
                            tint = Color.White,
                            modifier = Modifier.size(18.dp)
                        )

                        Text(
                            text = "Add a cover",
                            modifier = Modifier.padding(start = 5.dp)
                        )
                    }
                }
            }

            if (coverImageUri != null) {
                IconButton(
                    onClick = { onRemoveCoverImage() },
                    modifier = Modifier.align(Alignment.TopEnd)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = "Disable cover",
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun NewRecommendationCoverPreview() {
    NewRecommendationCover(
        coverType = "",
        coverImageUri = null,
        onCoverTypeChange = { },
        onAddCoverImage = { },
        onRemoveCoverImage = { }
    )
}