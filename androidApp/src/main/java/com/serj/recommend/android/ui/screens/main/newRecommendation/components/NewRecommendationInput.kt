package com.serj.recommend.android.ui.screens.main.newRecommendation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

@Composable
fun NewRecommendationInput(
    modifier: Modifier = Modifier,
    text: String,
    placeholder: String,
    enabled: Boolean,
    textColor: Color = Color.White,
    placeholderTextColor: Color = Color.White,
    fontSize: TextUnit = 14.sp,
    fontWeight: FontWeight? = null,
    lineHeight: TextUnit = TextUnit.Unspecified,
    textAlign: TextAlign = TextAlign.Center,
    maxLines: Int = Int.MAX_VALUE,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    onValueChange: (String) -> Unit
) {
    TextField(
        value = text,
        onValueChange = { onValueChange(it) },
        enabled = enabled,
        placeholder = {
            Text(
                text = placeholder,
                style = TextStyle(
                    fontSize = fontSize,
                    textAlign = textAlign
                ),
                modifier = Modifier.fillMaxWidth()
            )
        },
        textStyle = TextStyle(
            fontSize = fontSize,
            textAlign = textAlign,
            fontWeight = fontWeight,
            lineHeight = lineHeight
        ),
        maxLines = maxLines,
        singleLine = maxLines == 1,
        keyboardOptions = keyboardOptions,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            errorContainerColor = Color.Transparent,
            focusedTextColor = textColor,
            unfocusedTextColor = textColor,
            disabledTextColor = textColor,
            errorTextColor = textColor,
            focusedPlaceholderColor = placeholderTextColor,
            unfocusedPlaceholderColor = placeholderTextColor,
            disabledPlaceholderColor = placeholderTextColor,
            errorCursorColor = placeholderTextColor,
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
        modifier = modifier.fillMaxWidth()
    )
}

@Preview
@Composable
fun OneLineInputPreview() {
    NewRecommendationInput(
        text = "The White Lotus",
        enabled = true,
        placeholder = "Title",
        maxLines = 1,
        onValueChange = { }
    )
}