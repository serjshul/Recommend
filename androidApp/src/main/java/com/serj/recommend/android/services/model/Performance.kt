package com.serj.recommend.android.services.model

import com.google.firebase.perf.metrics.Trace
import com.google.firebase.perf.trace

/**
 * Trace a block with Firebase performance.
 *
 * Supports both suspend and regular methods.
 */
inline fun <T> trace(
    name: String,
    block: Trace.() -> T
): T = Trace.create(name).trace(block)