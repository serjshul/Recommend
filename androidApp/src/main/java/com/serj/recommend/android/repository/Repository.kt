package com.serj.recommend.android.repository

import com.serj.recommend.android.model.collections.Recommendation

interface Repository {
    fun getPosts(): List<Recommendation>
    fun getSaved(): List<Recommendation>
}