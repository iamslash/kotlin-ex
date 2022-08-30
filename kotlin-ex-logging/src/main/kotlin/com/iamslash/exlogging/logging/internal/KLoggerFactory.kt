package com.iamslash.exlogging.logging.internal

import com.iamslash.exlogging.logging.KLogging
import org.slf4j.Logger

internal object KLoggerFactory {

    fun logger(name: String): Logger = org.slf4j.LoggerFactory.getLogger(name)

    fun logger(block: () -> Unit): Logger = logger(KLoggerNameResolver.name(block))

    fun logger(clazz: Class<*>): Logger = logger(KLoggerNameResolver.name(clazz))

    fun logger(klogging: KLogging): Logger = logger(KLoggerNameResolver.name(klogging.javaClass))

}
