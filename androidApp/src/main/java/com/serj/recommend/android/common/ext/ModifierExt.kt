package com.serj.recommend.android.common.ext

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

fun Modifier.textInterval(): Modifier {
    return this
        .padding(bottom = 11.dp)
}

fun Modifier.textShape(): Modifier {
    return this
        .fillMaxWidth()
        .wrapContentHeight()
}

fun Modifier.recommendationHeaderShape(): Modifier {
    return this
        .fillMaxWidth()
        .height(300.dp)
}

fun Modifier.recommendationContentShape(): Modifier {
    return this
        .fillMaxWidth()
        .padding(top = 280.dp)
        .background(Color.White, RoundedCornerShape(20.dp))
}

fun Modifier.recommendationItemsInterval(): Modifier {
    return this
        .padding(bottom = 20.dp)
}

fun Modifier.recommendationParagraphShape(): Modifier {
    return this
        .fillMaxWidth()
        .wrapContentHeight()
        .padding(bottom = 20.dp)
}

fun Modifier.recommendationMediaShape(): Modifier {
    return this
        .height(180.dp)
        .fillMaxWidth()
}

fun Modifier.recommendationMediaClip(): Modifier {
    return this
        .clip(RoundedCornerShape(10.dp))
}

fun Modifier.mediaHeaderShape(): Modifier {
    return this
        .fillMaxSize()
}

fun Modifier.topBarShape(): Modifier {
    return this
        .fillMaxWidth()
        .height(64.dp)
}

fun Modifier.screenPaddingsInner(): Modifier {
    return this
        .padding(start = 15.dp, end = 15.dp)
}

fun Modifier.screenPaddingsOuter(): Modifier {
    return this
        .padding(top = 15.dp, bottom = 15.dp)
}





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

fun Modifier.mainScreenItems(): Modifier {
    return this
        .padding(end = 8.dp)
}