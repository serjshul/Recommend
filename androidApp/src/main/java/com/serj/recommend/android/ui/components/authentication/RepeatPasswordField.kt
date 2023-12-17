package com.serj.recommend.android.ui.components.authentication

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.serj.recommend.android.R

@Composable
fun RepeatPasswordField(
    value: String,
    onNewValue: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    PasswordField(value, R.string.repeat_password, onNewValue, modifier)
}