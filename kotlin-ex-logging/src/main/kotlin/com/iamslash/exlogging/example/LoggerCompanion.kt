package com.iamslash.exlogging.example

import org.slf4j.LoggerFactory

class LoggerCompanion {


    companion object {
//        private val logger = LoggerFactory.getLogger(LoggerCompanion::class.java)
        // This is not what we want.
        private val logger = LoggerFactory.getLogger(javaClass)
    }

    fun helloWorld() {
        logger.info("Hello LoggerCompanion")
    }
}

fun main() {
    val loggerCompanion = LoggerCompanion()
    loggerCompanion.helloWorld()
}
