package com.serj.recommend.android.ui.components.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.serj.recommend.android.R

@Composable
fun AppLogo(
    modifier: Modifier = Modifier,
    size: Dp = 100.dp
) {
    Box(modifier = modifier) {
        Image(
            modifier = Modifier.size(size),
            painter = painterResource(id = R.drawable.app_logo),
            contentDescription = "Recommend"
        )
    }
}