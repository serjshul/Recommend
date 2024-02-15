package com.serj.recommend.android.repository

import com.serj.recommend.android.model.collections.Recommendation

// read about data-layer here:
// https://developer.android.com/topic/architecture/data-layer
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