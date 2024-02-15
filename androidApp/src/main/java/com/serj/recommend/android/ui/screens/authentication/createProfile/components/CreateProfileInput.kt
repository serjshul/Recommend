package com.serj.recommend.android.ui.screens.authentication.createProfile.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.serj.recommend.android.ui.styles.outline

@Composable
fun CreateProfileInput(
    modifier: Modifier = Modifier,
    text: String,
    label: String,
    placeholder: String,
    textColor: Color,
    labelColor: Color,
    placeHolderColor: Color,
    borderColor: Color,
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
                style = TextStyle(fontSize = 12.sp)
            )
        },
        placeholder = {
            Text(
                text = placeholder,
                style = TextStyle(fontSize = 14.sp)
            )
        },
        textStyle = TextStyle(
            fontSize = 14.sp,
            textAlign = TextAlign.Start
        ),
        maxLines = maxLines,
        singleLine = maxLines == 1,
        isError = isError,
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            errorContainerColor = Color.Transparent,
            focusedTextColor = textColor,
            unfocusedTextColor = textColor,
            focusedPlaceholderColor = placeHolderColor,
            unfocusedPlaceholderColor = placeHolderColor,
            focusedLabelColor = labelColor,
            unfocusedLabelColor = labelColor,
            focusedBorderColor = borderColor,
            unfocusedBorderColor = borderColor
        ),
        modifier = modifier
            .padding(15.dp, 15.dp)
            .fillMaxWidth()
    )
}

@Preview
@Composable
fun OneLineInputPreview() {
    CreateProfileInput(
        text = "",
        label = "Title",
        placeholder = "Title",
        textColor = Color.Black,
        labelColor = outline,
        placeHolderColor = Color.Gray,
        borderColor = outline,
        isError = false,
        maxLines = 1,
        onValueChange = { },
        modifier = Modifier.background(Color.White)
    )
}