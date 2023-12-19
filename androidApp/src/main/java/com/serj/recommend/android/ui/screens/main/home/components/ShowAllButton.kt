package com.serj.recommend.android.ui.screens.main.home.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ShowAllButton(
    modifier: Modifier = Modifier,
    categoryId: String,
    openScreen: (String) -> Unit,
    onCategoryClick: ((String) -> Unit, String) -> Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        OutlinedIconButton(
            colors = IconButtonDefaults.outlinedIconButtonColors(
                contentColor = Color.Black,
            ),
            border = BorderStroke(1.dp, Color.Gray),
            onClick = {
                onCategoryClick(openScreen, categoryId)
            }
        ) {
            Icon(
                imageVector = Icons.Outlined.ArrowForward,
                contentDescription = "forward"
            )
        }

        Text(
            text = "Show all",
            color = Color.Black,
            fontSize = 14.sp,
            textAlign = TextAlign.Center
        )
    }
}