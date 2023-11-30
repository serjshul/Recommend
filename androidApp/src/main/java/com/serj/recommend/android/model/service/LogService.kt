package com.serj.recommend.android.model.service


interface LogService {
    fun logNonFatalCrash(throwable: Throwable)
}