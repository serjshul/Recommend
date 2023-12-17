package com.serj.recommend.android.ui.screens.main.rec

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.serj.recommend.android.R

@Composable
fun RecScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Gray)
            .wrapContentSize(Alignment.Center)
    ) {
        // TODO: Перенести все строки в коде в values/strings.xml
        Text(
            text = stringResource(id = R.string.rec_screen),
            style = MaterialTheme.typography.titleLarge,
            color = Color.White,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            textAlign = TextAlign.Center,
        )
    }
}