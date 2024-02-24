package com.serj.recommend.android.services

import com.serj.recommend.android.model.subcollections.Like
import com.serj.recommend.android.model.subcollections.Repost
import com.serj.recommend.android.services.model.Response
import java.util.Date

typealias LikeResponse = Response<String>
typealias RemoveLikeResponse = Response<Boolean>
typealias CommentResponse = Response<Boolean>
typealias RemoveCommentResponse = Response<Boolean>
typealias RepostResponse = Response<String>
typealias RemoveRepostResponse = Response<Boolean>

interface InteractionService {
    suspend fun like(
        like: Like
    ): LikeResponse

    suspend fun removeLike(
        userId: String,
        recommendationId: String,
        likeId: String
    ): RemoveLikeResponse

    suspend fun comment(
        userId: String,
        recommendationId: String,
        repliedCommentId: String?,
        repliedUserId: String?,
        text: String,
        isReplied: Boolean,
        date: Date,
        source: String
    ): CommentResponse

    suspend fun removeComment(
        recommendationId: String,
        userId: String,
        commentId: String,
        commentOwnerId: String
    ): RemoveCommentResponse

    suspend fun repost(
        repost: Repost
    ): RepostResponse

    suspend fun removeRepost(
        userId: String,
        recommendationId: String,
        repostId: String
    ): RemoveRepostResponse
}