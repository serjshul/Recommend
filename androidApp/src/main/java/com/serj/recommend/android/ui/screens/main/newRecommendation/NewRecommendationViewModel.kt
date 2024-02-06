package com.serj.recommend.android.ui.screens.main.newRecommendation

import androidx.compose.runtime.getValue
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

    init {
        launchCatching {
            accountService.currentUser.collect { user ->
                currentUser = user
            }
        }
    }

    fun onTitleValueChange(input: String) {
        title = input
    }

    fun onCreatorValueChange(input: String) {
        creator = input
    }

    fun onTagsValueChange(input: String) {
        tags = input
    }

    fun onYearValueChange(input: String) {
        year = input
    }

    fun onDescriptionValueChange(input: String) {
        description = input
    }



    fun onQuoteValueChange(input: String) {
        quote = input
    }

    fun enableQuote() {
        isQuoteEnabled = true
    }

    fun disableQuote() {
        isQuoteEnabled = false
    }

}