package com.iamslash.exlogging

import org.slf4j.LoggerFactory

class LoggerReflection {
    companion object {
        @Suppress("JAVA_CLASS_ON_COMPANNION")
        @JvmStatic
        private val logger = LoggerFactory.getLogger(javaClass.enclosingClass)
    }

    fun helloWorld() {
        logger.info("Hello LoggerReflection")
    }
}

fun main() {
    val loggerReflection: LoggerReflection = LoggerReflection()
    loggerReflection.helloWorld()
}
