package com.iamslash.exlogging.example

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import kotlin.reflect.full.companionObject

fun <T : Any> T.logger(): Logger = LoggerFactory.getLogger(javaClass)
class LoggerExtension {
    fun helloWorld() {
        logger().info("Hello LoggerExtension")
    }
}

// Extension Method on a Marker Interface
interface Logging
//fun <T : Logging> T.logger(): Logger = LoggerFactory.getLogger(javaClass)
class LoggerExtensionMarker : Logging {
    fun helloWorld() {
        logger().info("Hello LoggerExtensionMarkerInterface")
    }
}

//// Reified Type Parameter
//// avoiding a reflection call at runtime
//inline fun <reified T : Logging> T.logger(): Logger =
//    LoggerFactory.getLogger(T::class.java)

// Combining With Companion Objects
inline fun <T : Any> getClassForLogging(javaClass: Class<T>): Class<*> {
    return javaClass.enclosingClass?.takeIf {
        it.kotlin.companionObject?.java == javaClass
    } ?: javaClass
}
inline fun <reified T : Logging> T.logger(): Logger
    = LoggerFactory.getLogger(getClassForLogging(T::class.java))

fun main() {
    // A First Attempt
    val loggerExtension: LoggerExtension = LoggerExtension()
    loggerExtension.helloWorld()

    // Pollution of the Any Type
    // It breaks encapsulation
    "foo".logger().info("Hello Pollution of the Any Type")

    // Extension Method on a Marker Interface
    val loggerExtensionMarker: LoggerExtensionMarker = LoggerExtensionMarker()
    loggerExtensionMarker.helloWorld()
}
