package com.iamslash.exlogging.example

import com.iamslash.exlogging.logging.KLogging

class LoggerKlogging {

    companion object : KLogging()

    fun helloWorld() {
        log.info("Hello LoggerKloging")
    }
}

fun main() {
    LoggerKlogging().helloWorld()
}
