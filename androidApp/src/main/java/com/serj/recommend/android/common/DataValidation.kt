package com.serj.recommend.android.common

import com.serj.recommend.android.model.collections.Recommendation
import com.serj.recommend.android.model.items.Post
import com.serj.recommend.android.model.items.Preview
import com.serj.recommend.android.model.items.UserItem
import com.serj.recommend.android.model.subcollections.Comment
import com.serj.recommend.android.model.subcollections.Like
import com.serj.recommend.android.model.subcollections.Repost

fun isRecommendationValid(recommendation: Recommendation?): Boolean =
    if (recommendation == null) {
        false
    } else
        recommendation.id != null && recommendation.uid != null && recommendation.title != null &&
                recommendation.type != null && recommendation.creator != null &&
                recommendation.tags.isNotEmpty() && recommendation.year != null &&
                recommendation.description != null && recommendation.coversUrl.isNotEmpty()

fun isPostValid(post: Post?): Boolean =
    if (post == null) {
        false
    } else
        post.id != null && post.uid != null && post.title != null && post.type != null &&
                post.creator != null && post.tags.isNotEmpty() && post.date != null &&
                post.description != null && post.coversUrl.isNotEmpty()

fun isPreviewValid(preview: Preview?): Boolean =
    if (preview == null) {
        false
    } else
        preview.id != null && preview.uid != null && preview.title != null && preview.type != null &&
                preview.creator != null && preview.tags.isNotEmpty() && preview.coversUrl.isNotEmpty()

fun isUserItemValid(userItem: UserItem?): Boolean =
    if (userItem == null) {
        false
    } else
        userItem.uid != null && userItem.nickname != null && userItem.photoUrl != null

fun isLikeValid(like: Like) =
    like.userId != null && like.recommendationId != null && like.date != null && like.source != null

fun isCommentValid(comment: Comment) = comment.userId != null && comment.recommendationId != null &&
        comment.text != null && comment.date != null && comment.source != null

fun isRepostValid(repost: Repost) =
    repost.userId != null && repost.recommendationId != null && repost.date != null && repost.source != null
