package com.prologistik.test

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform