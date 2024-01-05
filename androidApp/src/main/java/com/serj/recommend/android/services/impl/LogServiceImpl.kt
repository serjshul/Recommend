package com.serj.recommend.android.services.impl

import com.google.firebase.Firebase
import com.google.firebase.crashlytics.crashlytics
import com.serj.recommend.android.services.LogService
import javax.inject.Inject


class LogServiceImpl @Inject constructor() : LogService {
    override fun logNonFatalCrash(throwable: Throwable) =
        Firebase.crashlytics.recordException(throwable)
}