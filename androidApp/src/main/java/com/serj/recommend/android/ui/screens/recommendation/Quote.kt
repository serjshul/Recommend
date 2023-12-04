package com.serj.recommend.android.ui.screens.recommendation

import androidx.compose.foundation.background
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt

@Composable
fun Quote(
    modifier: Modifier = Modifier
) {
    val quote = "Yo soy muy mía, yo me transformo\n" +
            "Una mariposa, yo me transformo\n" +
            "Makeup de drag queen, yo me transformo\n" +
            "Lluvia de estrеlla', yo me transformo"
    val color = "#E03038"

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 15.dp, end = 15.dp, bottom = 25.dp)
            .background(Color(color.toColorInt()), RoundedCornerShape(15.dp))
    ) {
        Text(
            modifier = modifier
                .padding(top = 10.dp, bottom = 5.dp)
                .align(Alignment.CenterHorizontally),
            text = "Favourite quote",
            color = Color.White,
            fontSize = 12.sp
        )

        Text(
            modifier = modifier.padding(start = 15.dp, end = 15.dp, bottom = 15.dp),
            text = quote,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            fontSize = 17.sp
        )
    }
}