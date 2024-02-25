package com.serj.recommend.android.services


interface LogService {
    fun logNonFatalCrash(throwable: Throwable)
}