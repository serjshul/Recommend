package com.serj.recommend.android.ui.screens.authentication.createProfile.components

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.serj.recommend.android.R
import com.serj.recommend.android.ui.styles.secondary

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CreateProfileUserPhoto(
    modifier: Modifier = Modifier,
    userPhotoUri: Uri?,
    onAddUserPhoto: (Uri) -> Unit,
    onProfileImageUriDisable: () -> Unit
) {
    val launcher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent()
        ) { uri: Uri? ->
            if (uri != null) {
                onAddUserPhoto(uri)
            }
        }

    Column(
        modifier = modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (userPhotoUri != null) {
            Box {
                GlideImage(
                    modifier = Modifier
                        .padding(bottom = 20.dp)
                        .size(150.dp)
                        .clip(CircleShape),
                    model = userPhotoUri,
                    contentDescription = "User photo",
                    contentScale = ContentScale.Crop
                )

                OutlinedIconButton(
                    onClick = { onProfileImageUriDisable() },
                    colors = IconButtonDefaults.outlinedIconButtonColors(
                        containerColor = secondary,
                        contentColor = Color.White
                    ),
                    border = BorderStroke(0.dp, secondary),
                    modifier = Modifier
                        .padding(end = 10.dp, top = 10.dp)
                        .size(24.dp)
                        .align(Alignment.TopEnd)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = "Disable quote",
                        modifier = Modifier.size(15.dp)
                    )
                }
            }
        } else {
            Image(
                modifier = Modifier
                    .padding(bottom = 20.dp)
                    .size(150.dp)
                    .clip(CircleShape),
                painter = painterResource(id = R.drawable.no_user_photo),
                contentDescription = "No user photo",
                contentScale = ContentScale.Crop
            )
        }

        OutlinedButton(
            onClick = { launcher.launch("image/*") },
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = Color.Transparent,
                contentColor = Color.Black
            ),
            border = BorderStroke(1.dp, Color.Gray),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        ) {
            Row {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Add a picture",
                    tint = Color.Black,
                    modifier = Modifier.size(18.dp)
                )

                Text(
                    text = "Add a picture",
                    modifier = Modifier.padding(start = 5.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun CreateProfilePhotoPreview() {
    CreateProfileUserPhoto(
        modifier = Modifier.background(Color.White),
        userPhotoUri = null,
        onAddUserPhoto = { },
        onProfileImageUriDisable = { }
    )
}