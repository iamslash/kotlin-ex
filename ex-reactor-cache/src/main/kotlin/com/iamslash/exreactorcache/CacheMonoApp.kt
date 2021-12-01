package com.iamslash.exreactorcache

import com.github.benmanes.caffeine.cache.Cache
import com.github.benmanes.caffeine.cache.Caffeine
import reactor.cache.CacheMono
import reactor.core.publisher.Mono
import reactor.core.publisher.Signal
import java.time.Duration
import java.time.Instant
import java.time.ZonedDateTime
import java.util.concurrent.TimeUnit


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
//        doFunctionCache("keyA").block()
//        doMapCache("keyA").block()
//        doMapClassCache("keyA").block()
        doCaffeineCache("keyA").block()
    }

    fun doFunctionCache(itemKey: String): Mono<String> {
        val stringCacheMap: MutableMap<String, String> = mutableMapOf()
        val itemVal: Mono<String> = CacheMono.lookup(
            { k ->
                Mono.justOrEmpty(stringCacheMap[k])
                .map {
                    Signal.next(it!!)
                }
            },
            itemKey)
//            .onCacheMissResume { handleCacheMiss() }
            .onCacheMissResume(handleCacheMiss(itemKey))
            .andWriteWith { key, sig ->
                Mono.fromRunnable {
                    stringCacheMap
                    stringCacheMap[key] = sig.get()
                }
            }
        return itemVal.doOnNext { v -> println("key: $itemKey, val is $v") }
    }

    fun doMapCache(itemKey: String): Mono<String> {
        val stringSignalCacheMap: MutableMap<String, Signal<out String>> = mutableMapOf()
        val itemVal: Mono<String> = CacheMono.lookup(stringSignalCacheMap, itemKey)
//            .onCacheMissResume { handleCacheMiss() }
            .onCacheMissResume(handleCacheMiss(itemKey))
        return itemVal.doOnNext { v -> println("key: $itemKey, val is $v") }
    }

    fun doMapClassCache(itemKey: String): Mono<String> {
        val objectSignalCacheMap: MutableMap<String, Signal<*>> = mutableMapOf()
        val itemVal: Mono<String> = CacheMono.lookup(objectSignalCacheMap, itemKey, String::class.java)
//            .onCacheMissResume { handleCacheMiss() }
            .onCacheMissResume(handleCacheMiss(itemKey))
        return itemVal.doOnNext { v -> println("key: $itemKey, val is $v") }
    }

    fun doCaffeineCache(itemKey: String): Mono<String> {
        val caffeineCache: Cache<String, String> = Caffeine.newBuilder()
            .expireAfterWrite(1L, TimeUnit.SECONDS)
            .recordStats()
            .build()
        val itemVal: Mono<String> = CacheMono.lookup(
            { k ->
                Mono.justOrEmpty(caffeineCache.getIfPresent(k))
                    .map {
                        Signal.next(it!!)
                    }
            },
            itemKey)
//            .onCacheMissResume { handleCacheMiss() }
            .onCacheMissResume(handleCacheMiss(itemKey))
            .andWriteWith { key, sig ->
                Mono.fromRunnable {
                    caffeineCache.put(key, sig.get())
                }
            }
        return itemVal.doOnNext { v -> println("key: $itemKey, val is $v") }
    }

}
