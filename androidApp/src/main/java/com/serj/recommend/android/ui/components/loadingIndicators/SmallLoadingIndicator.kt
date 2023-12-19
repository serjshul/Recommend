package com.serj.recommend.android.ui.components.loadingIndicators

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.serj.recommend.android.ui.styles.TigerEye

@Composable
fun SmallLoadingIndicator(
    modifier: Modifier = Modifier,
    backgroundColor: Color
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .width(34.dp)
                .align(Alignment.Center),
            trackColor = TigerEye,
            color = backgroundColor
        )
    }
}