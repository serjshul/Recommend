package com.serj.recommend.android.ui.screens.main.newRecommendation.components

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.serj.recommend.android.ui.styles.primary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewRecommendationTopBar(
    isValid: Boolean
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.White,
            titleContentColor = Color.Black,
        ),
        title = {
            Text(
                "New Recommendation",
                fontSize = 16.sp,
                maxLines = 1,
                fontWeight = FontWeight.Bold,
                overflow = TextOverflow.Ellipsis
            )
        },
        actions = {
            Button(
                onClick = {  },
                colors = ButtonDefaults.buttonColors(
                    containerColor = primary,
                    contentColor = Color.White
                ),
                modifier = Modifier.alpha(if (isValid) 1f else 0.6f)
            ) {
                Text(
                    text = "Recommend",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                )
            }
        },
        scrollBehavior = scrollBehavior,
    )
}

@Preview
@Composable
fun NewRecommendationTopBarPreview() {
    NewRecommendationTopBar(
        isValid = false
    )
}