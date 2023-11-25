package com.serj.recommend.android

import com.serj.recommend.android.ui.BannerItemData
import com.serj.recommend.android.ui.recommendationItems.BookItemData
import com.serj.recommend.android.ui.recommendationItems.MediaItemData
import com.serj.recommend.android.ui.recommendationItems.MusicItemData
import com.serj.recommend.android.ui.recommendationItems.PlacesItemData


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

fun getPlacesData(): ArrayList<List<PlacesItemData>> {
    val placesData = arrayListOf<List<PlacesItemData>>()

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
    placesData.add(placesCategory)

    return placesData
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