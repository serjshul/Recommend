package com.serj.recommend.android.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun TestFeatures(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .padding(10.dp)
            .background(color = Color.Cyan)
    ) {
        Text(text = "Hello")
        Text(text = "World")
        Button(onClick = { }) {
            Text(text = "Click on me")
        }
    }
}

@Preview
@Composable
fun PreviewTestFeatures() = TestFeatures()