package com.ylevanon.alephbet

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform