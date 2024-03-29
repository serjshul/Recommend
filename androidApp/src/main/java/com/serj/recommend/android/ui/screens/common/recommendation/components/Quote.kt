package com.serj.recommend.android.ui.screens.common.recommendation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.serj.recommend.android.ui.styles.background

@Composable
fun Quote(
    modifier: Modifier = Modifier,
    quote: String,
    color: Color?
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(start = 15.dp, end = 15.dp, top = 12.5.dp, bottom = 5.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color ?: background, RoundedCornerShape(20.dp))
        ) {
            Text(
                modifier = Modifier
                    .padding(top = 12.dp, bottom = 7.dp)
                    .align(Alignment.CenterHorizontally),
                text = "Favourite quote",
                color = Color.White,
                fontSize = 14.sp
            )

            Text(
                modifier = Modifier.padding(start = 15.dp, end = 15.dp, bottom = 15.dp),
                text = quote.replace("\\n", "\n"),
                fontWeight = FontWeight.Bold,
                color = Color.White,
                fontSize = 16.sp
            )
        }
    }
}

@Preview
@Composable
fun QuotePreview() {
    Quote(
        quote = "Yeah, bitch, I said what I said\\nI'd rather be famous instead\\nI let " +
                "all that get to my head\\nI don’t care, I paint the town red",
        color = null
    )
}