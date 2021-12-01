package com.iamslash.exreactorcache

import org.reactivestreams.Subscription
import reactor.cache.CacheMono
import reactor.core.publisher.Mono
import reactor.core.publisher.Signal
import java.time.Instant
import java.time.ZonedDateTime
import java.util.Objects
import java.util.Objects.requireNonNull

object CacheMonoApp {

    fun handleCacheMiss(): Mono<String> {
        println("Cache miss !!!")
        return Mono.just(ZonedDateTime.now().toString())
    }

    fun handleCacheMiss(key: String): Mono<String?> {
        println("Cache miss !!!")
        return Mono.just(Instant.now().toString())
    }

    @JvmStatic
    fun main(args: Array<String>) {
        doFunctionCache("keyA").block()
//        doMapCache()
//        doMapClassCache()
//        doCaffeineCache()
    }

    fun doFunctionCache(key: String): Mono<String?> {
        val mapStringCache: MutableMap<String, String> = mutableMapOf()
        val itemVal = CacheMono.lookup(
            { _ ->
                Mono.justOrEmpty(mapStringCache[key])
                .map { Signal.next(it) }
            },
            key)
            .onCacheMissResume(handleCacheMiss(key))
            .andWriteWith { key, sig ->
                Mono.fromRunnable {
                    mapStringCache
                    mapStringCache[key] = sig.get()!!
                }
            }
        return itemVal.doOnNext { v -> println("key: $key, val is $v") }
    }

    fun doMapCache() {

    }

    fun doMapClassCache() {

    }

    fun doCaffeineCache() {

    }

}
