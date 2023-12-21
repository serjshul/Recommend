package com.serj.recommend

interface Storage {
//    @OptIn(ExperimentalObjCRefinement::class)
//    @HiddenFromObjC
    val isNeedToShowOnboarding: Boolean
}

expect fun getStorage(): Storage