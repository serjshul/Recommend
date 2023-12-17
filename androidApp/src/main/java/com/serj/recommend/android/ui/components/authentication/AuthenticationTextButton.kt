package com.serj.recommend.android.ui.components.authentication

import androidx.annotation.StringRes
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource

@Composable
fun AuthenticationTextButton(
    modifier: Modifier = Modifier,
    @StringRes text: Int,
    action: () -> Unit)
{
    TextButton(
        onClick = action,
        modifier = modifier,
        colors = ButtonDefaults.textButtonColors(
            contentColor = Color.Black
        )
    ) {
        Text(
            text = stringResource(text)
        )
    }
}