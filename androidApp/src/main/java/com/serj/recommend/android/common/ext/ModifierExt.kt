package com.serj.recommend.android.common.ext

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

fun Modifier.fieldModifier(): Modifier {
    return this.fillMaxWidth().padding(15.dp, 5.dp)
}

fun Modifier.basicButton(): Modifier {
    return this.fillMaxWidth().padding(15.dp, 10.dp)
}

fun Modifier.textButton(): Modifier {
    return this.fillMaxWidth().padding(15.dp, 10.dp, 15.dp, 20.dp)
}



fun Modifier.recommendationCoverShape(): Modifier {
    return this
        .padding(bottom = 5.dp)
        .clip(RoundedCornerShape(5.dp))
}

fun Modifier.squareRecommendationMain(): Modifier {
    return this
        .height(225.dp)
        .width(160.dp)
        .padding(end = 8.dp)
}

fun Modifier.squareRecommendationCategory(): Modifier {
    return this
        .height(225.dp)
        .width(160.dp)
}

fun Modifier.horizontalRecommendationMain(): Modifier {
    return this
        .height(230.dp)
        .width(265.dp)
        .padding(end = 8.dp)
}

fun Modifier.horizontalRecommendationCategory(): Modifier {
    return this
        .height(250.dp)
        .fillMaxWidth()
        .padding(start = 15.dp, end = 15.dp, bottom = 15.dp)
}





fun Modifier.card(): Modifier {
    return this.padding(16.dp, 0.dp, 16.dp, 8.dp)
}

fun Modifier.contextMenu(): Modifier {
    return this.wrapContentWidth()
}

fun Modifier.alertDialog(): Modifier {
    return this.wrapContentWidth().wrapContentHeight()
}

fun Modifier.dropdownSelector(): Modifier {
    return this.fillMaxWidth()
}

fun Modifier.toolbarActions(): Modifier {
    return this.wrapContentSize(Alignment.TopEnd)
}

fun Modifier.spacer(): Modifier {
    return this.fillMaxWidth().padding(12.dp)
}

fun Modifier.smallSpacer(): Modifier {
    return this.fillMaxWidth().height(8.dp)
}