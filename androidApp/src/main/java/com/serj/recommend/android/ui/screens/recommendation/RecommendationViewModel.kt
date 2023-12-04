package com.serj.recommend.android.ui.screens.recommendation

import androidx.compose.runtime.mutableStateOf
import com.serj.recommend.android.ARTICLE_ID
import com.serj.recommend.android.EDIT_TASK_SCREEN
import com.serj.recommend.android.SETTINGS_SCREEN
import com.serj.recommend.android.model.Article
import com.serj.recommend.android.model.service.ConfigurationService
import com.serj.recommend.android.model.service.LogService
import com.serj.recommend.android.model.service.StorageService
import com.serj.recommend.android.ui.screens.RecommendViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class RecommendationViewModel @Inject constructor(
    logService: LogService,
    private val storageService: StorageService,
    private val configurationService: ConfigurationService
) : RecommendViewModel(logService) {
    val options = mutableStateOf<List<String>>(listOf())

    val articles = storageService.articles

    fun loadArticleOptions() {
        val hasEditOption = configurationService.isShowTaskEditButtonConfig
        options.value = RecommendationActionOption.getOptions(hasEditOption)
    }

    /*
    fun onTaskCheckChange(article: Article) {
        launchCatching { storageService.updateArticle(article.copy(completed = !article.completed)) }
    }
     */

    fun onAddClick(openScreen: (String) -> Unit) = openScreen(EDIT_TASK_SCREEN)

    fun onSettingsClick(openScreen: (String) -> Unit) = openScreen(SETTINGS_SCREEN)

    fun onArticleActionClick(openScreen: (String) -> Unit, article: Article, action: String) {
        when (RecommendationActionOption.getByTitle(action)) {
            RecommendationActionOption.EditArticle -> openScreen("$EDIT_TASK_SCREEN?$ARTICLE_ID={${article.id}}")
            //RecommendationActionOption.ToggleFlag -> onFlagArticleClick(article)
            RecommendationActionOption.DeleteArticle -> onDeleteArticleClick(article)
            else -> {}
        }
    }

    /*
    private fun onFlagArticleClick(article: Article) {
        launchCatching { storageService.updateArticle(article.copy(flag = !task.flag)) }
    }

     */

    private fun onDeleteArticleClick(article: Article) {
        launchCatching { storageService.deleteArticle(article.id) }
    }
}