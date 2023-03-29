package com.kenshi.kmmtranslator

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform