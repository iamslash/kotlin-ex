package com.iamslash.exlogging.example

import com.iamslash.exlogging.logging.KLogging

class LoggerKloging {

    companion object : KLogging()

    fun helloWorld() {
        log.info("Hello LoggerKloging")
    }
}

fun main() {
    LoggerKloging().helloWorld()
}
