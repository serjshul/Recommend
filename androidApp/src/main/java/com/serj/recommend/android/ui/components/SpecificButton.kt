package com.serj.recommend.android.ui.components

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun SpecificButton(
    modifier: Modifier = Modifier
) {
    Button(onClick = {
        // TODO: track that user click to post new post / recommendation
    }) {
        Text(text="Click on me")
    }
}