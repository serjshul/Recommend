package com.serj.recommend.android.ui.screens.home

import androidx.compose.runtime.mutableStateOf
import com.serj.recommend.android.ARTICLE_SCREEN
import com.serj.recommend.android.EDIT_TASK_SCREEN
import com.serj.recommend.android.SETTINGS_SCREEN
import com.serj.recommend.android.model.Article
import com.serj.recommend.android.model.service.ConfigurationService
import com.serj.recommend.android.model.service.LogService
import com.serj.recommend.android.model.service.StorageService
import com.serj.recommend.android.ui.screens.RecommendViewModel
import com.serj.recommend.android.ui.screens.recommendation.RecommendationActionOption
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    logService: LogService,
    private val storageService: StorageService,
    private val configurationService: ConfigurationService
) : RecommendViewModel(logService) {
    val options = mutableStateOf<List<String>>(listOf())

    //val articles = storageService.articles
    val categories = storageService.categories

    fun loadArticleOptions() {
        val hasEditOption = configurationService.isShowTaskEditButtonConfig
        options.value = RecommendationActionOption.getOptions(hasEditOption)
    }

    fun onArticleCheckChange(article: Article) {
        //launchCatching { storageService.updateArticle(article.copy(completed = !article.completed)) }
    }

    fun onAddClick(openScreen: (String) -> Unit) = openScreen(EDIT_TASK_SCREEN)

    fun onSettingsClick(openScreen: (String) -> Unit) = openScreen(SETTINGS_SCREEN)

    fun onArticleActionClick(openScreen: (String) -> Unit, article: Article, action: String) {
        when (RecommendationActionOption.getByTitle(action)) {
            RecommendationActionOption.EditArticle -> openScreen(ARTICLE_SCREEN)
            RecommendationActionOption.ToggleFlag -> onFlagArticleClick(article)
            RecommendationActionOption.DeleteArticle -> onDeleteArticleClick(article)
        }
    }

    private fun onFlagArticleClick(article: Article) {
        //launchCatching { storageService.updateArticle(article.copy(flag = !article.flag)) }
    }

    private fun onDeleteArticleClick(article: Article) {
        launchCatching { storageService.deleteArticle(article.id) }
    }
}