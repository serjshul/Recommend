package com.serj.recommend.android.ui.screens.main.newRecommendation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.serj.recommend.android.ui.screens.common.recommendation.components.Paragraph
import com.serj.recommend.android.ui.styles.secondary

@Composable
fun NewRecommendationAddParagraph(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(14.dp, 13.dp)
    ) {
        Paragraph(
            title = "Cosmology has its high priests",
            text = "We believe in it all–and oh, how we love it. Big cosmology " +
                    "has become our secular religion, a church even atheists can " +
                    "join. It addresses many of the same questions religion " +
                    "does: Why are we here? How did it all begin? What comes " +
                    "next? And even if you can barely understand the answers " +
                    "when you get them, well, you’ve heard of a thing called " +
                    "faith, right?",
            imageReference = null,
            videoReference = null,
            modifier = Modifier
                .padding(2.dp, 2.dp)
                .alpha(0.8f)
                .blur(radiusX = 3.5.dp, radiusY = 3.5.dp)
        )

        ElevatedButton(
            onClick = {  },
            modifier = Modifier.align(Alignment.Center),
            colors = ButtonDefaults.elevatedButtonColors(
                containerColor = secondary,
                contentColor = Color.White
            )
        ) {
            Row {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Add a paragraph",
                    tint = Color.White,
                    modifier = Modifier.size(18.dp)
                )

                Text(
                    text = "Add a paragraph",
                    modifier = Modifier.padding(start = 5.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun NewRecommendationAddParagraphPreview() {
    NewRecommendationAddParagraph()
}