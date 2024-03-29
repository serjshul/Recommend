package com.serj.recommend.android.ui.screens.authentication.createProfile.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.serj.recommend.android.R
import java.util.Calendar
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateProfileDatePicker(
    modifier: Modifier = Modifier,
    dateOfBirth: String,
    label: String,
    @StringRes supportingText: Int,
    isError: Boolean,
    showDatePicker: Boolean,
    onValueChange: (Date) -> Unit,
    onIconClick: () -> Unit,
    onDismissRequest: () -> Unit
) {
    val calendar = Calendar.getInstance()
    calendar.time = Date()

    val datePickerState = rememberDatePickerState(initialSelectedDateMillis = calendar.timeInMillis)
    var selectedDate by remember { mutableLongStateOf(calendar.timeInMillis) }

    Box(
        modifier = modifier
    ) {
        OutlinedTextField(
            value = dateOfBirth,
            onValueChange = { },
            label = {
                Text(
                    text = label,
                    style = TextStyle(fontSize = 12.sp),
                )
            },
            supportingText = {
                if (isError) {
                    Text(
                        text = stringResource(id = supportingText)
                    )
                }
            },
            textStyle = TextStyle(
                fontSize = 14.sp,
                textAlign = TextAlign.Start
            ),
            trailingIcon = {
                IconButton(
                    onClick = { onIconClick() }
                ) {
                    Icon(
                        imageVector = Icons.Filled.DateRange,
                        contentDescription = "Choose the option"
                    )
                }
            },
            readOnly = true,
            maxLines = 1,
            singleLine = true,
            isError = isError,
            modifier = modifier
                .padding(start = 15.dp, top = 2.5.dp, end = 7.5.dp, bottom = 2.5.dp)
                .fillMaxWidth()
        )

        if (showDatePicker) {
            DatePickerDialog(
                onDismissRequest = { onDismissRequest() },
                confirmButton = {
                    TextButton(
                        onClick = {
                            selectedDate = datePickerState.selectedDateMillis!!
                            onValueChange(Date(selectedDate))
                            onDismissRequest()
                        }
                    ) {
                        Text(text = "Confirm")
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = { onDismissRequest() }
                    ) {
                        Text(text = "Cancel")
                    }
                }
            ) {
                DatePicker(
                    state = datePickerState
                )
            }
        }
    }
}

@Preview
@Composable
fun CreateProfileDatePickerPreview() {
    CreateProfileDatePicker(
        dateOfBirth = "",
        label = "Date of birth",
        supportingText = R.string.create_account_date_of_birth_error,
        isError = false,
        showDatePicker = false,
        onValueChange = { },
        onIconClick = { },
        onDismissRequest = { }
    )
}