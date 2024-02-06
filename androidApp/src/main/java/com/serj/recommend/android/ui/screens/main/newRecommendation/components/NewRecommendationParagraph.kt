package com.serj.recommend.android.ui.screens.main.newRecommendation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.serj.recommend.android.ui.styles.secondary

@Composable
fun NewRecommendationParagraph(
    modifier: Modifier = Modifier,
    index: Int,
    enabled: Boolean,
    title: String,
    //imageReference: StorageReference?,
    //videoReference: StorageReference?,
    text: String,
    color: Color = Color.Black,
    enableParagraph: (Int) -> Unit,
    disableParagraph: (Int) -> Unit,
    changeCurrentParagraph: (Int) -> Unit,
    onTitleValueChange: (String) -> Unit,
    onTextValueChange: (String) -> Unit
) {
    Box(
        modifier = modifier
            .onFocusChanged {
                if (it.isFocused) changeCurrentParagraph(index)
            }
            .background(Color.White)
    ){
        Box(
            modifier = Modifier
                .alpha(
                    if (!enabled) 0.8f else 1f
                )
                .blur(
                    radiusX = if (!enabled) 3.5.dp else 0.dp,
                    radiusY = if (!enabled) 3.5.dp else 0.dp
                )
        ) {
            NewRecommendationInput(
                text =
                if (enabled) title
                else "Cosmology has its high priests",
                placeholder = "Title",
                textColor = color,
                placeholderTextColor = Color.Gray,
                enabled = enabled,
                fontSize = 22.sp,
                maxLines = 2,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .padding(top = if (enabled) 20.dp else 0.dp)
                    .fillMaxWidth()
                    .align(Alignment.TopCenter),
                onValueChange = onTitleValueChange,
            )

            NewRecommendationInput(
                text =
                if (enabled) text
                else "We believe in it all–and oh, how we love it. Big cosmology " +
                        "has become our secular religion, a church even atheists can " +
                        "join. It addresses many of the same questions religion " +
                        "does: Why are we here? How did it all begin? What comes " +
                        "next? And even if you can barely understand the answers " +
                        "when you get them, well, you’ve heard of a thing called " +
                        "faith, right?",
                placeholder = "Write a text...",
                enabled = enabled,
                textColor = Color.Black,
                placeholderTextColor = Color.Gray,
                fontSize = 14.sp,
                lineHeight = 1.35.em,
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .padding(top = if (enabled) 70.dp else 50.dp)
                    .fillMaxWidth()
                    .align(Alignment.TopCenter),
                onValueChange = onTextValueChange,
            )
        }

        if (!enabled) {
            ElevatedButton(
                onClick = { enableParagraph(index) },
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

        if (enabled) {
            IconButton(
                onClick = { disableParagraph(index) },
                modifier = Modifier.align(Alignment.TopEnd)
            ) {
                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = "Disable paragraph",
                    tint = Color.Black,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun NewRecommendationParagraphPreview() {
    NewRecommendationParagraph(
        modifier = Modifier.background(Color.White),
        index = 0,
        enabled = false,
        title = "",
        //imageReference = null,
        //videoReference = null,
        text = "",
        enableParagraph = { },
        disableParagraph = { },
        changeCurrentParagraph = { },
        onTitleValueChange = { },
        onTextValueChange = { }
    )
}