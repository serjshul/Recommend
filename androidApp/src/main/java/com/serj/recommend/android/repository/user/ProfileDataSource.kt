package com.serj.recommend.android.repository.user

import com.serj.recommend.android.model.Recommendation

interface ProfileDataSource {
    fun getPosts(): List<Recommendation>
}