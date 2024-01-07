package com.serj.recommend.android.common

class RecommendationNotFoundException(
    message: String = "Recommendation not found!"
) : Exception(message)

class UserNotFoundException(
    message: String = "User not found!"
) : Exception(message)

class BannerNotFoundException(
    message: String = "Banner not found!"
) : Exception(message)

class CategoryNotFoundException(
    message: String = "Category not found!"
) : Exception(message)