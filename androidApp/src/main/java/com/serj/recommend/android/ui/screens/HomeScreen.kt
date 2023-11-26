package com.serj.recommend.android.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import com.serj.recommend.android.getBannersData
import com.serj.recommend.android.getBooksData
import com.serj.recommend.android.getMediaData
import com.serj.recommend.android.getMusicData
import com.serj.recommend.android.getPlacesData
import com.serj.recommend.android.ui.BannerIndicator
import com.serj.recommend.android.ui.BannerItem
import com.serj.recommend.android.ui.categories.CasualCategoryItem
import com.serj.recommend.android.ui.categories.CrossingCategoryItem
import com.serj.recommend.android.ui.categories.GalleryCategoryItem
import com.serj.recommend.datalayer.navigation.Navigation


@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController) {
    val banners = getBannersData()
    val musicData = getMusicData()
    val mediaData = getMediaData()
    val placesData = getPlacesData()
    val booksData = getBooksData()

    Scaffold() { paddingValues ->
        Box {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(color = Color.White)
            ) {
                item {
                    Box() {
                        val pagerState = rememberPagerState(pageCount = { banners.size })
                        HorizontalPager(
                            state = pagerState,
                            verticalAlignment = Alignment.Top
                        ) { page ->
                            BannerItem(banners[page])
                        }
                        BannerIndicator(pagerState = pagerState)
                    }
                }

                item {
                    CrossingCategoryItem("Serj's New Music", musicData[0])  {
                        navController.navigate(Navigation.ArticleScreen.name)
                    }
                }

                item {
                    CasualCategoryItem(
                        "Cool books", booksData[0]
                    )
                }

                item {
                    GalleryCategoryItem(title = "Netflix and Chill", data = mediaData[0])
                }
            }
        }
    }
}
