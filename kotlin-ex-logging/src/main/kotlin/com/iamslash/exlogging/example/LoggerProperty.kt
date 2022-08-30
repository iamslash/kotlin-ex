package com.iamslash.exlogging.example

import org.slf4j.LoggerFactory

class LoggerProperty {
    private val logger = LoggerFactory.getLogger(javaClass)

    fun helloWorld() {
        logger.info("Hello LoggerProperty!!!")
    }
}

fun main() {
    val loggerProperty: LoggerProperty = LoggerProperty()
    loggerProperty.helloWorld()
}
