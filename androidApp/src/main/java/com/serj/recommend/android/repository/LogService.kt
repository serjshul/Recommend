package com.serj.recommend.android.repository


interface LogService {
    fun logNonFatalCrash(throwable: Throwable)
}