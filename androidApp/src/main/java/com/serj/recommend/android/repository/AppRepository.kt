package com.serj.recommend.android.repository

import com.serj.recommend.android.model.Recommendation

class AppRepository(
    private val local: LocalRepository,
    private val remote: RemoteRepository
): Repository {
    override fun getPosts(): List<Recommendation> {
        local.getPosts()
        TODO("Not yet implemented")
    }

    override fun getSaved(): List<Recommendation> {
        local.getSaved()
        TODO("Not yet implemented")
    }

}