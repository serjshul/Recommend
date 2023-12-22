package com.serj.recommend.android.common.ext

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

fun Modifier.fieldModifier(): Modifier {
    return this
        .fillMaxWidth()
        .padding(15.dp, 5.dp)
}

fun Modifier.basicButton(): Modifier {
    return this
        .fillMaxWidth()
        .padding(15.dp, 10.dp)
}

fun Modifier.textButton() = this
    .fillMaxWidth()
    .padding(
        15.dp, 10.dp, 15.dp, 20.dp
    )


fun Modifier.recommendationCoverShape(): Modifier {
    return this
        .padding(bottom = 5.dp)
        .clip(RoundedCornerShape(5.dp))
}

fun Modifier.mainScreenItems(): Modifier {
    return this
        .padding(end = 8.dp)
}