package com.serj.recommend.android.ui.components.comments

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.serj.recommend.android.R
import com.serj.recommend.android.model.Comment

@Composable
fun CommentDropdownMenu(
    modifier: Modifier = Modifier,
    comment: Comment,
    expanded: Boolean,
    onDeleteCommentClick: (Comment) -> Unit,
    onCommentDismissRequest: (Comment) -> Unit,
) {
    DropdownMenu(
        modifier = modifier
            .width(120.dp)
            .background(Color.White),
        expanded = expanded,
        onDismissRequest = { onCommentDismissRequest(comment) }
    ) {
        DropdownMenuItem(
            text = { Text("Pin") },
            onClick = {  },
            leadingIcon = {
                Icon(
                    ImageVector.vectorResource(id = R.drawable.dropdown_menu_pin),
                    contentDescription = "Pin button"
                )
            },
            colors = MenuDefaults.itemColors(
                textColor = Color.Black,
                leadingIconColor = Color.Black
            )
        )
        DropdownMenuItem(
            text = { Text("Delete") },
            onClick = { onDeleteCommentClick(comment) },
            leadingIcon = {
                Icon(
                    ImageVector.vectorResource(id = R.drawable.dropdown_menu_delete),
                    contentDescription = "Delete button"
                )
            },
            colors = MenuDefaults.itemColors(
                textColor = Color.Red,
                leadingIconColor = Color.Red
            )
        )
    }
}