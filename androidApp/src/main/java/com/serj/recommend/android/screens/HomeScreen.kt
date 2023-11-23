package com.serj.recommend.android.screens

import android.content.Context
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import com.serj.recommend.android.BannerIndicator
import com.serj.recommend.android.BannerItem
import com.serj.recommend.android.R
import com.serj.recommend.android.BannerItemData
import com.serj.recommend.android.recommendationItems.BookItemData
import com.serj.recommend.android.recommendationItems.MediaItemData
import com.serj.recommend.android.recommendationItems.MusicItemData
import com.serj.recommend.android.recommendationItems.PlacesItemData
import com.serj.recommend.android.categories.CasualCategoryItem
import com.serj.recommend.android.categories.CrossingCategoryItem
import com.serj.recommend.android.categories.GalleryCategoryItem


@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(context: Context) {
    val banners = arrayListOf(
        BannerItemData(
            "Super Weakness",
            "There is Sirotkin's new EP about love, loneliness and tigers in dark glasses. It is just what you need on a lonely warm evenings",
            R.drawable.banner_sirotkin
        ),
        BannerItemData(
            "Goodbye, poor bitches!",
            "Why is Doja Cat fighting with fans? What lies behind her new album \"Scarlett\"? And why do people on social media often say that she’s gone crazy?",
            R.drawable.banner_doja_cat
        ),
        BannerItemData(
            "Never Have I Ever",
            "After a traumatic year, an Indian-American teen just wants to spruce up her social status — but everyone won't make it easy on her",
            R.drawable.banner_never_have_i_ever
        ),
        BannerItemData(
            "Aster",
            "A cafe for every day with fresh pastries, a carefully selected wine list and coffee roasted for us by Gravitas.",
            R.drawable.banner_aster
        )
    )
    val newMusic = listOf(
        MusicItemData(
            "Saoko",
            "Rosalia",
            R.drawable.cover_music_rosalia_saoko
        ),
        MusicItemData(
            "Matilda",
            "Harry Styles",
            R.drawable.cover_music_harry_styles_matilda
        ),
        MusicItemData(
            "Пост-пост",
            "Монеточка",
            R.drawable.cover_music_monetochka_post_post
        ),
        MusicItemData(
            "King of Everything",
            "Dominic Fike",
            R.drawable.cover_music_dominic_fike_king_of_everything
        )
    )
    val mediaNetflix = listOf(
        MediaItemData(
            "Never Have I Ever",
            "Series",
            "Netflix",
            R.drawable.cover_media_never_have_i_ever
        ),
        MediaItemData(
            "Never Have I Ever",
            "Series",
            "Netflix",
            R.drawable.cover_media_never_have_i_ever
        ),
        MediaItemData(
            "Never Have I Ever",
            "Series",
            "Netflix",
            R.drawable.cover_media_never_have_i_ever
        )
    )
    val placesCategory = listOf(
        PlacesItemData(
            "Civil на Волынском",
            "Кофейня",
            "Санкт-Петербург",
            R.drawable.cover_places_civil
        ),
        PlacesItemData(
            "Aster",
            "Кафе",
            "Санкт-Петербург",
            R.drawable.cover_places_aster
        )
    )

    val booksCategory = listOf(
        BookItemData(
            "Norwegian Forest",
            "Haruki Murakami",
            R.drawable.cover_book_haruki_murakami_norwegian_forest
        ),
        BookItemData(
            "The Night in Lisbon",
            "Erich Maria Remarque",
            R.drawable.cover_book_erich_maria_remarque_the_night_in_lisbon
        ),
        BookItemData(
            "Book Thief",
            "Markus Zusak",
            R.drawable.cover_book_markus_zusak_book_thief
        )
    )

    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())

    Scaffold (
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
    ) { paddingValues ->
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
                    CasualCategoryItem("Cool books", booksCategory, context)
                }

                item {
                    CrossingCategoryItem("Serj's New Music", newMusic, context)
                }

                item {
                    GalleryCategoryItem(title = "Netflix and Chill", data = mediaNetflix, context)
                }
            }

            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                ),
                title = {
                    Text(
                        "Home",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { /* do something */ }) {
                        Icon(
                            imageVector = Icons.Filled.Menu,
                            contentDescription = "Localized description"
                        )
                    }
                },
                scrollBehavior = scrollBehavior
            )
        }
    }
}