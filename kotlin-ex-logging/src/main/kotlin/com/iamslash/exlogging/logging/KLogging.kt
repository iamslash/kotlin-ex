package com.iamslash.exlogging.logging

import com.iamslash.exlogging.logging.internal.KLoggerFactory
import org.slf4j.Logger

/**
 * Usage:
 * ```
 * class Foo {
 *     companion object: KLogging()
 *
 *     fun doit() {
 *         log.debug { "Hello World" }
 *     }
 * }
 * ```
 */
open class KLogging {

    val log: Logger by lazy { KLoggerFactory.logger(this.javaClass) }

}
