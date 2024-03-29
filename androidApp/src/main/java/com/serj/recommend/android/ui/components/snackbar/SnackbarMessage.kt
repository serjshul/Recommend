package com.serj.recommend.android.ui.components.snackbar

import android.content.res.Resources
import androidx.annotation.StringRes
import com.serj.recommend.android.R.string as AppText

sealed class SnackbarMessage {
    class StringSnackbar(val message: String) : SnackbarMessage()
    class ResourceSnackbar(@StringRes val message: Int) : SnackbarMessage()

    companion object {
        fun SnackbarMessage.toMessage(resources: Resources): String {
            return when (this) {
                is StringSnackbar -> this.message
                is ResourceSnackbar -> resources.getString(this.message)
            }
        }

        fun Throwable.toSnackbarMessage(): SnackbarMessage {
            val message = this.message.orEmpty()
            return if (message.isNotBlank()) StringSnackbar(message)
            else ResourceSnackbar(AppText.navigation_title_home_screen)
        }
    }
}

//class SnackbarMessage2() {
//
//    constructor(message: String) : this() {
//
//    }
//
//    constructor(@StringRes message: Int) : this() {
//
//    }
//
//
//}