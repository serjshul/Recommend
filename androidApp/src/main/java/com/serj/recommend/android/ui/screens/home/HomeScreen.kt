package com.serj.recommend.android.ui.screens.home

import android.content.ContentValues
import android.util.Log
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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.serj.recommend.android.R
import com.serj.recommend.android.model.Article
import com.serj.recommend.android.model.Category
import com.serj.recommend.android.ui.screens.home.categories.CrossingCategoryItem
import com.serj.recommend.android.ui.screens.home.categories.GalleryCategoryItem
import com.serj.recommend.android.ui.screens.home.categories.OrdinaryCategoryItem
import com.serj.recommend.android.ui.screens.home.categories.recommendations.BookItemData
import com.serj.recommend.android.ui.screens.home.categories.recommendations.MediaItemData
import com.serj.recommend.android.ui.screens.home.categories.recommendations.MusicItemData


@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    openScreen: (String) -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    //val articles = viewModel.articles.collectAsStateWithLifecycle(emptyList())
    val categories = viewModel.categories.collectAsStateWithLifecycle(emptyList())
    val options by viewModel.options

    Log.v(ContentValues.TAG, categories.value.toString())

    HomeScreenContent(
        articles = categories.value,
        options = options,
        onArticleClick = viewModel::onArticleActionClick,
        openScreen = openScreen
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreenContent(
    modifier: Modifier = Modifier,
    articles: List<Category>,
    options: List<String>,
    onArticleClick: ((String) -> Unit, Article, String) -> Unit,
    openScreen: (String) -> Unit
) {
    val banners = getBannersData()
    val musicData = getMusicData()
    val mediaData = getMediaData()
    val booksData = getBooksData()

    Scaffold() { paddingValues ->
        LazyColumn(
            modifier = modifier
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
                CrossingCategoryItem(
                    title = "Serj's New Music",
                    data = musicData[0],
                    openScreen = openScreen
                )
            }

            item {
                OrdinaryCategoryItem(
                    "Cool books", booksData[0]
                )
            }

            item {
                GalleryCategoryItem(title = "Netflix and Chill", data = mediaData[0])
            }
        }
    }
}


fun getBannersData(): ArrayList<BannerItemData> {
    return arrayListOf(
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
}

fun getMusicData(): ArrayList<List<MusicItemData>> {
    val musicData = arrayListOf<List<MusicItemData>>()

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
    musicData.add(newMusic)

    return musicData
}

fun getMediaData(): ArrayList<List<MediaItemData>> {
    val mediaData = arrayListOf<List<MediaItemData>>()

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
    mediaData.add(mediaNetflix)

    return mediaData
}

fun getBooksData(): ArrayList<List<BookItemData>> {
    val booksData = arrayListOf<List<BookItemData>>()

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
    booksData.add(booksCategory)

    return booksData
}
