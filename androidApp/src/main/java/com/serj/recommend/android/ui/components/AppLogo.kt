package com.serj.recommend.android.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.serj.recommend.android.R

@Composable
fun AppLogo(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.wrapContentSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.app_logo),
            contentDescription = "Recommend"
        )
    }
}