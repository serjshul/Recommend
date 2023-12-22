package com.serj.recommend.android

import android.app.Activity
import androidx.annotation.StringRes

fun Activity.getString(@StringRes id: Int) {
    this.applicationContext.getString(id)
}