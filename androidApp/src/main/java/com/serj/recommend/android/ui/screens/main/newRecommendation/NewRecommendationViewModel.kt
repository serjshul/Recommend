package com.serj.recommend.android.ui.screens.main.newRecommendation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.serj.recommend.android.model.User
import com.serj.recommend.android.services.AccountService
import com.serj.recommend.android.services.LogService
import com.serj.recommend.android.services.StorageService
import com.serj.recommend.android.ui.screens.RecommendViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NewRecommendationViewModel @Inject constructor(
    logService: LogService,
    private val storageService: StorageService,
    private val accountService: AccountService
) : RecommendViewModel(logService) {

    var isNewRecommendationValid by mutableStateOf(false)
        private set
    var currentUser by mutableStateOf<User?>(null)
        private set

    var type by mutableStateOf("")
        private set
    var title by mutableStateOf("")
        private set
    var creator by mutableStateOf("")
        private set
    var tags by mutableStateOf("")
        private set
    var year by mutableStateOf("")
        private set
    var description by mutableStateOf("")
        private set
    var quote by mutableStateOf("")
        private set
    var isQuoteEnabled by mutableStateOf(false)
        private set
    val paragraphs = mutableStateListOf(
        mutableStateMapOf(
            "title" to "",
            "text" to ""
        )
    )
    val isParagraphsEnabled = mutableStateListOf(false)
    val currentParagraphIndex = mutableIntStateOf(0)

    init {
        launchCatching {
            accountService.currentUser.collect { user ->
                currentUser = user
            }
        }
    }

    fun onTitleValueChange(input: String) {
        title = input
        checkNewRecommendationValid()
    }

    fun onTypeValueChange(input: String) {
        type = input
        checkNewRecommendationValid()
    }

    fun onCreatorValueChange(input: String) {
        creator = input
        checkNewRecommendationValid()
    }

    fun onTagsValueChange(input: String) {
        tags = input
        checkNewRecommendationValid()
    }

    fun onYearValueChange(input: String) {
        year = input
        checkNewRecommendationValid()
    }

    fun onDescriptionValueChange(input: String) {
        description = input
        checkNewRecommendationValid()
    }

    fun enableParagraph(index: Int) {
        isParagraphsEnabled[index] = true
        paragraphs.add(
            mutableStateMapOf(
                "title" to "",
                "text" to ""
            )
        )
        isParagraphsEnabled.add(false)
        checkNewRecommendationValid()
    }

    fun disableParagraph(index: Int) {
        isParagraphsEnabled.removeAt(index)
        paragraphs.removeAt(index)
        checkNewRecommendationValid()
    }

    fun changeCurrentParagraphIndex(index: Int) {
        currentParagraphIndex.intValue = index
    }

    fun onParagraphTitleValueChange(input: String) {
        paragraphs[currentParagraphIndex.intValue]["title"] = input
        checkNewRecommendationValid()
    }

    fun onParagraphTextValueChange(input: String) {
        paragraphs[currentParagraphIndex.intValue]["text"] = input
        checkNewRecommendationValid()
    }

    fun onQuoteValueChange(input: String) {
        quote = input
    }

    fun enableQuote() {
        isQuoteEnabled = true
    }

    fun disableQuote() {
        quote = ""
        isQuoteEnabled = false
    }

    private fun checkNewRecommendationValid() {
        var isParagraphsValid = true

        for (i in paragraphs.indices) {
            if (isParagraphsEnabled[i] && (paragraphs[i]["title"] == "" || paragraphs[i]["text"] == "")) {
                isParagraphsValid = false
            }
        }

        isNewRecommendationValid = type != "" && title != "" && creator != "" && tags != "" &&
                year != "" && description != "" && isParagraphsValid
    }
}