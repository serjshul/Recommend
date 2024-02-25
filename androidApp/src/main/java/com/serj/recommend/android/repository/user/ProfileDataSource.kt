package com.serj.recommend.android.repository.user

import com.serj.recommend.android.model.collections.Recommendation

interface ProfileDataSource {
    fun getPosts(): List<Recommendation>
}