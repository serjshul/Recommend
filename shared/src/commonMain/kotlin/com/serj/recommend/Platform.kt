package com.serj.recommend

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform