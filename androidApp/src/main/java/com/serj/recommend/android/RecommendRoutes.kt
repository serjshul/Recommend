package com.serj.recommend.android

enum class RecommendRoutes {
    SplashScreen, SignUpScreen, SignInScreen, ResetPasswordScreen,
    MainScreen, HomeScreen, FeedScreen, CreateRecommendScreen, SearchScreen, ProfileScreen,
    RecommendationScreen, BannerScreen, CategoryScreen
}

const val RECOMMENDATION_ID = "recommendationId"
const val RECOMMENDATION_ID_ARG = "?$RECOMMENDATION_ID={$RECOMMENDATION_ID}"
const val BANNER_ID = "bannerId"
const val BANNER_ID_ARG = "?$BANNER_ID={$BANNER_ID}"
const val CATEGORY_ID = "categoryId"
const val CATEGORY_ID_ARG = "?$CATEGORY_ID={$CATEGORY_ID}"