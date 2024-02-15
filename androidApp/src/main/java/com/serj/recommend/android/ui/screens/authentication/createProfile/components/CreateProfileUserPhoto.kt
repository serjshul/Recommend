package com.serj.recommend.android.ui.screens.authentication.createProfile.components

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
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

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CreateProfileUserPhoto(
    modifier: Modifier = Modifier,
    userPhotoUri: Uri?,
    onAddUserPhoto: (Uri) -> Unit
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
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (userPhotoUri != null) {
            GlideImage(
                modifier = Modifier
                    .padding(top = 40.dp, bottom = 20.dp)
                    .size(130.dp)
                    .clip(CircleShape),
                model = userPhotoUri,
                contentDescription = "User photo",
                contentScale = ContentScale.Crop
            )
        } else {
            Image(
                modifier = Modifier
                    .padding(top = 40.dp, bottom = 20.dp)
                    .size(130.dp)
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
                .padding(bottom = 35.dp)
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
        onAddUserPhoto = { }
    )
}