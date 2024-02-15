package com.serj.recommend.android.ui.screens.authentication.createProfile.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
fun CreateProfileList(
    modifier: Modifier = Modifier,
    chosenOption: String,
    options: List<String>,
    label: String,
    isError: Boolean,
    expanded: Boolean,
    maxLines: Int = Int.MAX_VALUE,
    onValueChange: (String) -> Unit,
    onOptionsClick: () -> Unit,
    onDismissRequest: () -> Unit
) {
    Box(
        modifier = modifier.fillMaxWidth()
    ) {
        OutlinedTextField(
            value = chosenOption,
            onValueChange = { },
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
            trailingIcon = {
                IconButton(
                    onClick = { onOptionsClick() }
                ) {
                    Icon(
                        imageVector = Icons.Filled.KeyboardArrowRight,
                        contentDescription = "Choose the option"
                    )
                }
            },
            readOnly = true,
            maxLines = maxLines,
            singleLine = maxLines == 1,
            isError = isError,
            modifier = modifier
                .padding(15.dp, 2.5.dp)
                .fillMaxWidth()
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { onDismissRequest() },
            modifier = Modifier
                .background(Color.White)
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(text = option) },
                    onClick = {
                        onValueChange(option)
                        onDismissRequest()
                    }
                )
            }
        }
    }
}

@Preview
@Composable
fun CreateProfileListPreview() {
    CreateProfileList(
        chosenOption = "",
        options = listOf("item 1", "item 2", "item 3"),
        label = "Title",
        isError = false,
        maxLines = 1,
        expanded = false,
        onOptionsClick = { },
        onDismissRequest = { },
        onValueChange = { },
        modifier = Modifier.background(Color.White)
    )
}