package com.serj.recommend.android

enum class RecommendRoutes {
    SplashScreen, SignUpScreen, SignInScreen, ResetPasswordScreen,
    MainScreen, HomeScreen, FeedScreen, RecScreen, SearchScreen, ProfileScreen,
    RecommendationScreen, BannerScreen
}

const val RECOMMENDATION_ID = "recommendationId"
const val RECOMMENDATION_ID_ARG = "?$RECOMMENDATION_ID={$RECOMMENDATION_ID}"
const val BANNER_ID = "bannerId"
const val BANNER_ID_ARG = "?$BANNER_ID={$BANNER_ID}"