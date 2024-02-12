package com.serj.recommend.android.ui.screens.main.newRecommendation.components

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.google.firebase.storage.StorageReference
import com.serj.recommend.android.ui.screens.common.recommendation.components.UserInfo
import com.serj.recommend.android.ui.styles.secondary

@Composable
fun NewRecommendationHeader(
    modifier: Modifier = Modifier,
    title: String,
    type: String,
    creator: String,
    tags: String,
    year: String,
    backgroundImageUri: Uri?,
    photoReference: StorageReference?,
    nickname: String?,
    onTitleValueChange: (String) -> Unit,
    onTypeValueChange: (String) -> Unit,
    onCreatorValueChange: (String) -> Unit,
    onTagsValueChange: (String) -> Unit,
    onYearValueChange: (String) -> Unit,
    onAddBackgroundImage: (Uri) -> Unit,
    onRemoveBackgroundImage: () -> Unit
) {
    val launcher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent()
        ) { uri: Uri? ->
            if (uri != null) {
                onAddBackgroundImage(uri)
            }
        }

    Column(modifier = modifier) {
        IconButton(
            onClick = { onRemoveBackgroundImage() },
            enabled = backgroundImageUri != null,
            modifier = Modifier
                .alpha(if (backgroundImageUri != null) 1f else 0f)
                .align(Alignment.End)
        ) {
            Icon(
                imageVector = Icons.Filled.Close,
                contentDescription = "Disable quote",
                tint = Color.White,
                modifier = Modifier.size(20.dp)
            )
        }

        if (nickname != null) {
            UserInfo(
                photoReference = photoReference,
                nickname = nickname
            )
        }

        ElevatedButton(
            onClick = { launcher.launch("image/*") },
            colors = ButtonDefaults.elevatedButtonColors(
                containerColor = secondary,
                contentColor = Color.White
            ),
            enabled = backgroundImageUri == null,
            modifier = Modifier
                .padding(top = 50.dp)
                .alpha(if (backgroundImageUri == null) 1f else 0f)
                .align(Alignment.CenterHorizontally)
        ) {
            Row {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Add a background",
                    tint = Color.White,
                    modifier = Modifier.size(18.dp)
                )

                Text(
                    text = "Add a background",
                    modifier = Modifier.padding(start = 5.dp)
                )
            }
        }

        NewRecommendationInput(
            text = title,
            placeholder = "Title",
            enabled = true,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            maxLines = 4,
            modifier = Modifier
                .padding(
                    start = 15.dp,
                    end = 15.dp,
                    top = if (nickname != null) 30.dp else 86.dp
                )
                .align(Alignment.CenterHorizontally),
            onValueChange = onTitleValueChange
        )

        NewRecommendationInput(
            text = type,
            placeholder = "Type",
            enabled = true,
            fontSize = 14.sp,
            lineHeight = 1.2.em,
            maxLines = 4,
            modifier = Modifier,
            onValueChange = onTypeValueChange,
        )

        NewRecommendationHeaderInfo(
            modifier = Modifier
                .padding(15.dp, 20.dp)
                .fillMaxWidth(),
            creator = creator,
            tags = tags,
            year = year,
            onCreatorValueChange = onCreatorValueChange,
            onTagsValueChange = onTagsValueChange,
            onYearValueChange = onYearValueChange
        )
    }
}

@Composable
fun NewRecommendationHeaderInfo(
    modifier: Modifier = Modifier,
    creator: String,
    tags: String,
    year: String,
    onCreatorValueChange: (String) -> Unit,
    onTagsValueChange: (String) -> Unit,
    onYearValueChange: (String) -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        NewRecommendationInput(
            text = creator,
            placeholder = "Creator",
            enabled = true,
            fontSize = 14.sp,
            lineHeight = 1.2.em,
            maxLines = 4,
            modifier = Modifier
                .weight(1f),
            onValueChange = onCreatorValueChange,
        )

        Text(
            modifier = Modifier
                .padding(start = 10.dp, end = 10.dp),
            text = "/",
            color = Color.White,
            fontSize = 23.sp
        )

        NewRecommendationInput(
            text = tags,
            placeholder = "Tags",
            enabled = true,
            fontSize = 14.sp,
            lineHeight = 1.2.em,
            maxLines = 4,
            modifier = Modifier
                .weight(1f),
            onValueChange = onTagsValueChange,
        )

        Text(
            modifier = Modifier
                .padding(start = 10.dp, end = 10.dp),
            text = "/",
            color = Color.White,
            fontSize = 23.sp
        )

        NewRecommendationInput(
            text = year,
            placeholder = "Year",
            enabled = true,
            fontSize = 14.sp,
            maxLines = 1,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier
                .weight(1f),
            onValueChange = onYearValueChange,
        )
    }
}

@Preview
@Composable
fun NewRecommendationHeaderPreview() {
    NewRecommendationHeader(
        title = "Title",
        type = "Type",
        creator = "Creator",
        tags = "Tag, Tag, Tag, Tag, Tag",
        year = "2023",
        photoReference = null,
        nickname = "nickname",
        backgroundImageUri = null,
        onTitleValueChange = { },
        onTypeValueChange = { },
        onCreatorValueChange = { },
        onTagsValueChange = { },
        onYearValueChange = { },
        onAddBackgroundImage = { },
        onRemoveBackgroundImage = { }
    )
}