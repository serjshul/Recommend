package com.serj.recommend.android.ui.screens.main.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.serj.recommend.android.R
import com.serj.recommend.android.model.items.UserItem
import com.serj.recommend.android.ui.components.media.CustomGlideImage

// TODO: Перенести все строки в коде в values/strings.xml
@Composable
fun ProfileScreen(
    user: UserItem?
) {

    Column {
        ProfileInfo(
            user = user,
            modifier = Modifier.weight(.3f)
        )
        BottomPart(
            modifier = Modifier.weight(.7f)
        )
    }
}

@Composable
fun ProfileInfo(
    user: UserItem?,
    modifier: Modifier = Modifier
) {
    Column {
        // ProfilePhoto
        if (user?.photoReference != null) {
            CustomGlideImage(
                reference = user.photoReference,
                modifier = Modifier
                    .clip(CircleShape)
                    .size(64.dp)
            )
        } else {
            Image(
                painter = painterResource(id = R.drawable.icon_like_bordered_1),
                contentDescription = null,
                modifier = Modifier
                    .clip(CircleShape)
                    .size(64.dp)
            )
        }
        Text(text = user?.nickname ?: "Your nickname")
        // ProfileDescription
        Row {
            // subscribers
            // subscribe to
            //
        }
    }
}

@Composable
fun BottomPart(modifier: Modifier = Modifier) {
    // Exists of two tabs (or more?)
    // Tabs can be switched

    // Posts
    // Favourites

    // if posts picked      -> collect actual posts
    // if favourites picked -> collect actual favs
}
