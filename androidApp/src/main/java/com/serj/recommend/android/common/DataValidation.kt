package com.serj.recommend.android.common

import com.serj.recommend.android.model.collections.Recommendation
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

fun isLikeValid(like: Like) =
    like.userId != null && like.recommendationId != null && like.date != null && like.source != null

fun isCommentValid(comment: Comment) = comment.userId != null && comment.recommendationId != null &&
        comment.text != null && comment.date != null && comment.source != null

fun isRepostValid(repost: Repost) =
    repost.userId != null && repost.recommendationId != null && repost.date != null && repost.source != null
