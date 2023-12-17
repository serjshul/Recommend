package com.serj.recommend.android.ui.components.authentication

import androidx.annotation.StringRes
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp

@Composable
fun AuthenticationButton(@StringRes text: Int, modifier: Modifier, action: () -> Unit) {
    FilledTonalButton(
        onClick = action,
        modifier = modifier,
        colors = ButtonDefaults.filledTonalButtonColors(
            containerColor = Color.Black,
            contentColor = Color.White
        )
    ) {
        Text(text = stringResource(text), fontSize = 16.sp)
    }
}