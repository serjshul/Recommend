package com.serj.recommend.android.ui.screens.main.newRecommendation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.serj.recommend.android.ui.styles.background
import com.serj.recommend.android.ui.styles.secondary

@Composable
fun NewRecommendationQuote(
    modifier: Modifier = Modifier,
    quote: String,
    color: Color = background,
    enabled: Boolean,
    onQuoteValueChange: (String) -> Unit,
    enableQuote: () -> Unit,
    disableQuote: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(8.dp, 5.5.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(2.dp, 2.dp)
                .fillMaxWidth()
                .background(color, RoundedCornerShape(20.dp))
                .alpha(
                    if (!enabled) 0.8f else 1f
                )
                .blur(
                    radiusX = if (!enabled) 3.5.dp else 0.dp,
                    radiusY = if (!enabled) 3.5.dp else 0.dp
                )
        ) {
            Text(
                modifier = Modifier
                    .padding(top = 12.dp, bottom = 2.dp)
                    .align(Alignment.CenterHorizontally),
                text = "Add favourite quote",
                color = Color.White,
                fontSize = 12.sp
            )

            NewRecommendationInput(
                text = if (enabled) quote
                    else "Yeah, bitch, I said what I said\nI'd rather be famous instead\nI let " +
                        "all that get to my head\nI don’t care, I paint the town red",
                placeholder = "Write a quote...",
                enabled = enabled,
                fontSize = 17.sp,
                maxLines = 4,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Start,
                modifier = Modifier.padding(start = 5.dp, end = 5.dp, bottom = 5.dp),
                onValueChange = onQuoteValueChange,
            )
        }

        if (!enabled) {
            ElevatedButton(
                onClick = { enableQuote() },
                modifier = Modifier.align(Alignment.Center),
                colors = ButtonDefaults.elevatedButtonColors(
                    containerColor = secondary,
                    contentColor = Color.White
                )
            ) {
                Row {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = "Add a quote",
                        tint = Color.White,
                        modifier = Modifier.size(18.dp)
                    )

                    Text(
                        text = "Add a quote",
                        modifier = Modifier.padding(start = 5.dp)
                    )
                }
            }
        }

        if (enabled) {
            IconButton(
                onClick = { disableQuote() },
                modifier = Modifier.align(Alignment.TopEnd)
            ) {
                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = "Disable quote",
                    tint = Color.White,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun NewRecommendationQuotePreview() {
    NewRecommendationQuote(
        quote = "",
        enabled = true,
        onQuoteValueChange = { },
        enableQuote = { },
        disableQuote = { }
    )
}