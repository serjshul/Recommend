package com.serj.recommend.android.ui.components.comments.items

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.google.firebase.storage.StorageReference
import com.serj.recommend.android.common.getCreatedTime
import com.serj.recommend.android.model.collections.Comment
import com.serj.recommend.android.ui.components.media.GlideUserImage
import com.serj.recommend.android.ui.styles.primary
import java.util.Date

@Composable
fun CommentRecommendationItem(
    modifier: Modifier = Modifier,
    comment: Comment,
    nickname: String,
    photoReference: StorageReference?,
    text: String,
    date: Date,
    onCommentClick: (Comment) -> Unit,
) {
    val createdTime = getCreatedTime(date)

    Row(
        modifier = modifier
            .padding(10.dp, 5.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.Top
    ) {
        Box(
            modifier = Modifier
                .size(50.dp)
        ) {
            GlideUserImage(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape),
                reference = photoReference
            )
        }

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                modifier = Modifier
                    .padding(bottom = 3.dp)
                    .clickable { onCommentClick(comment) },
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append(nickname)
                    }
                    append(" $text")
                },
                color = Color.White,
                fontSize = 14.sp,
                lineHeight = 1.2.em
            )

            Row {
                Text(
                    modifier = Modifier.padding(end = 10.dp),
                    text = createdTime,
                    color = Color.LightGray,
                    fontSize = 12.sp,
                    lineHeight = 1.2.em
                )
            }
        }
    }
}

@Preview
@Composable
fun CommentRecommendationItemPreview() {
    CommentRecommendationItem(
        modifier = Modifier.background(primary),
        comment = Comment(),
        nickname = "succession",
        photoReference = null,
        text = "A note to adults in the audience: “13 Reasons Why” is not Netflix’s next “Stranger Things”.",
        date = Date(Date().time - 22 * 60 * 60 * 1000L),
        onCommentClick = { _: Comment -> }
    )
}