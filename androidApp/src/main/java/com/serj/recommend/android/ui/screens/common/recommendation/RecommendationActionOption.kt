package com.serj.recommend.android.ui.screens.common.recommendation

enum class RecommendationActionOption(val title: String) {
    EditArticle("Edit article"),
    ToggleFlag("Toggle flag"),
    DeleteArticle("Delete article");

    companion object {
        fun getByTitle(title: String): RecommendationActionOption {
            values().forEach { action -> if (title == action.title) return action }

            return EditArticle
        }

        fun getOptions(hasEditOption: Boolean): List<String> {
            val options = mutableListOf<String>()
            values().forEach { taskAction ->
                if (hasEditOption || taskAction != EditArticle) {
                    options.add(taskAction.title)
                }
            }
            return options
        }
    }
}