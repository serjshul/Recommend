package com.serj.recommend.android.ui.screens.authentication.createProfile.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CreateProfileInput(
    modifier: Modifier = Modifier,
    text: String,
    label: String,
    isError: Boolean,
    maxLines: Int = Int.MAX_VALUE,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = text,
        onValueChange = { onValueChange(it) },
        label = {
            Text(
                text = label,
                style = TextStyle(fontSize = 12.sp),
            )
        },
        textStyle = TextStyle(
            fontSize = 14.sp,
            textAlign = TextAlign.Start
        ),
        maxLines = maxLines,
        singleLine = maxLines == 1,
        isError = isError,
        modifier = modifier
            .padding(15.dp, 2.5.dp)
            .fillMaxWidth()
    )
}

@Preview
@Composable
fun CreateProfileInputPreview() {
    CreateProfileInput(
        text = "",
        label = "Title",
        isError = false,
        maxLines = 1,
        onValueChange = { },
        modifier = Modifier.background(Color.White)
    )
}