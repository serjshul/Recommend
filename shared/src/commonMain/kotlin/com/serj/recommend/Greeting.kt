package com.serj.recommend

class Greeting {
    private val platform: Platform = getPlatform()

    fun greet(): String {
        return "Hello, ${platform.name}!"
    }
}

fun sum(a: Int, b: Int): Int = a + b