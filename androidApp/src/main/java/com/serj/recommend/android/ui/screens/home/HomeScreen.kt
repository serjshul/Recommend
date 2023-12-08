package com.serj.recommend.android.ui.screens.home

import android.graphics.Bitmap
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.serj.recommend.android.R
import com.serj.recommend.android.model.Category
import com.serj.recommend.android.model.Recommendation
import com.serj.recommend.android.ui.screens.home.categories.OrdinaryCategory
import com.serj.recommend.android.ui.screens.home.categories.recommendations.BookItemData
import com.serj.recommend.android.ui.screens.home.categories.recommendations.MediaItemData

@Composable
fun HomeScreen(
    openScreen: (String) -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val categories = viewModel.categories.collectAsStateWithLifecycle(emptyList())
    val categoriesImages = viewModel.categoriesImages

    val options by viewModel.options

    HomeScreenContent(
        categories = categories.value,
        options = options,
        openScreen = openScreen,
        categoriesImages = categoriesImages,
        onRecommendationClick = viewModel::onRecommendationClick
    )
}

@Composable
fun HomeScreenContent(
    modifier: Modifier = Modifier,
    categories: List<Category>,
    categoriesImages: Map<String?, List<Bitmap?>?>,
    options: List<String>,
    openScreen: (String) -> Unit,
    onRecommendationClick: ((String) -> Unit, Recommendation) -> Unit
) {
    val banners = getBannersData()

    Scaffold() { paddingValues ->
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(color = Color.White)
        ) {
            item {
                BannerItem(
                    banners = banners
                )
            }

            for (category in categories) {
                item {
                    OrdinaryCategory(
                        category = category,
                        covers = categoriesImages[category.title],
                        openScreen = openScreen,
                        onRecommendationClick = onRecommendationClick
                    )
                }
            }


            /*
            item {
                CrossingCategoryItem(
                    title = "Serj's New Music",
                    data = musicData[0],
                    openScreen = openScreen,
                    onRecommendationClick = onRecommendationClick
                )
            }



            item {
                GalleryCategoryItem(title = "Netflix and Chill", data = mediaData[0])
            }

             */
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
