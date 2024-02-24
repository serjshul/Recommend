package com.serj.recommend.android.ui.screens.main.profile

import androidx.compose.animation.animateColor
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TabPosition
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.serj.recommend.android.model.collections.User
import com.serj.recommend.android.ui.components.media.CustomGlideImage

private enum class TabPage {
    Posts, Likes, Favourites
}

// TODO: Перенести все строки в коде в values/strings.xml
@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel()
) = ProfileScreenContent(
    profileUser = viewModel.profileUser,
    currentUser = viewModel.currentUser,
)

@Composable
private fun ProfileScreenContent(
    profileUser: User?,
    currentUser: User?,
) {
    Column(
        modifier = Modifier.background(Color.White)
    ) {
        ProfileInfo(
            profileUser = profileUser,
            currentUser = currentUser,
            modifier = Modifier.weight(.3f)
        )
        BottomPart(
            profileUser = profileUser,
            currentUser = currentUser,
            modifier = Modifier.weight(.7f)
        )
    }
}

// TODO: Top part of profile we make like RecommendationScreen
@Composable
private fun ProfileInfo(
    profileUser: User?,
    currentUser: User?,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CustomGlideImage(
            reference = profileUser?.photoReference,
            modifier = Modifier
                .clip(CircleShape)
                .size(64.dp)
        )
        Text(text = "@${profileUser?.nickname}")

        // ProfileDescription
        Row(
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Followers")
                Text(profileUser?.followers?.size.toString())
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Following")
                Text(profileUser?.following?.size.toString())
            }
        }
        if (profileUser != currentUser) {
            Button(onClick = { }) {
                Text("Subscribe")
            }
        }
    }
}

@Composable
private fun BottomPart(
    profileUser: User?,
    currentUser: User?,
    modifier: Modifier = Modifier
) {
    var tabPage by remember { mutableStateOf(TabPage.Posts) }
    var isLoading by remember { mutableStateOf(false) }

    // later it can be set by user itself
    val backgroundColor by animateColorAsState(
        when (tabPage) {
            TabPage.Posts -> Color(0xffef823c)
            TabPage.Likes -> Color(0xfff3b391)
            TabPage.Favourites -> Color(0xfff6d4ba)
        },
        label = "backgroundColor"
    )

    Scaffold(
        topBar = {
            ProfileTabBar(
                profileUser = profileUser,
                currentUser = currentUser,
                backgroundColor = backgroundColor,
                tabPage = tabPage,
                onTabSelected = { tabPage = it }
            )
        },
        containerColor = backgroundColor,
    ) { padding ->
        Box(
            modifier = Modifier.padding(
                top = padding.calculateTopPadding(),
                start = padding.calculateLeftPadding(
                    LayoutDirection.Ltr
                ),
                end = padding.calculateEndPadding(
                    LayoutDirection.Ltr
                )
            )
        ) {
        }
    }

    // if posts picked      -> collect actual posts
    // if favourites picked -> collect actual favs
}


@Composable
private fun ProfileTabBar(
    profileUser: User?,
    currentUser: User?,
    backgroundColor: Color,
    tabPage: TabPage,
    onTabSelected: (tabPage: TabPage) -> Unit
) {
    Column(
        modifier = Modifier
            .windowInsetsPadding(
                WindowInsets.safeDrawing.only(
                    WindowInsetsSides.Horizontal
                )
            )
    ) {
        Spacer(
            Modifier.windowInsetsTopHeight(
                WindowInsets.safeDrawing
            )
        )
        TabRow(
            selectedTabIndex = tabPage.ordinal,
            containerColor = backgroundColor,
            contentColor = MaterialTheme.colorScheme.onPrimary,
            indicator = { tabPositions ->
                HomeTabIndicator(tabPositions, tabPage)
            }
        ) {
            HomeTab(
                title = TabPage.Posts.name,
                onClick = { onTabSelected(TabPage.Posts) }
            )
            HomeTab(
                title = TabPage.Likes.name,
                onClick = { onTabSelected(TabPage.Likes) }
            )
            if (profileUser != currentUser) {
                HomeTab(
                    title = TabPage.Favourites.name,
                    onClick = { onTabSelected(TabPage.Favourites) }
                )
            }
        }
    }
}

@Composable
private fun HomeTabIndicator(
    tabPositions: List<TabPosition>,
    tabPage: TabPage
) {
    val transition = updateTransition(
        tabPage, label = "Tab indicator"
    )
    val indicatorLeft by transition.animateDp(
        transitionSpec = {
            when {
                TabPage.Posts isTransitioningTo TabPage.Likes -> spring(
                    stiffness = Spring.StiffnessVeryLow
                )
                TabPage.Likes isTransitioningTo TabPage.Posts -> spring(
                    stiffness = Spring.StiffnessMedium
                )
                else -> spring()
            }
        },
        label = "Indicator left"
    ) { page ->
        tabPositions[page.ordinal].left
    }
    val indicatorCenter by transition.animateDp(
        transitionSpec = {
            when {
                TabPage.Posts isTransitioningTo TabPage.Likes -> spring(
                    stiffness = Spring.StiffnessMedium
                )
                TabPage.Likes isTransitioningTo TabPage.Posts -> spring(
                    stiffness = Spring.StiffnessVeryLow
                )
                else -> spring()
            }
        },
        label = "Indicator right"
    ) { page ->
        tabPositions[page.ordinal].right
    }
    val indicatorRight by transition.animateDp(
        transitionSpec = {
            when {
                TabPage.Posts isTransitioningTo TabPage.Likes -> spring(
                    stiffness = Spring.StiffnessMedium
                )
                TabPage.Likes isTransitioningTo TabPage.Posts -> spring(
                    stiffness = Spring.StiffnessVeryLow
                )
                else -> spring()
            }
        },
        label = "Indicator right"
    ) { page ->
        tabPositions[page.ordinal].right
    }
    val borderColor by transition.animateColor(
        label = "borderColor"
    ) { page ->
        when (page) {
            TabPage.Posts -> Color(0xffd56103)
            TabPage.Likes -> Color(0xffe26908)
            TabPage.Favourites -> Color(0xffed6f0d)
        }
    }
    Box(
        Modifier
            .fillMaxSize()
            .wrapContentSize(align = Alignment.BottomStart)
            .offset(x = indicatorLeft)
            .width(indicatorRight - indicatorLeft)
            .padding(4.dp)
            .fillMaxSize()
            .border(
                BorderStroke(2.dp, borderColor),
                RoundedCornerShape(4.dp)
            )
    )
}

@Composable
private fun HomeTab(
    title: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) = Row(
    modifier = modifier
        .clickable(onClick = onClick)
        .padding(16.dp),
    horizontalArrangement = Arrangement.Center,
    verticalAlignment = Alignment.CenterVertically
) {
    Text(text = title)
}

@Preview
@Composable
private fun ProfileScreenPreview() =
    ProfileScreenContent(
        profileUser = User(
            nickname = "b0r1ngx"
        ),
        currentUser = User()
    )
