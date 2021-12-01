package com.iamslash.exreactorcache

import reactor.cache.CacheFlux
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.publisher.SignalType
import kotlin.streams.toList


object CacheFluxApp {

    fun handleCacheMiss(): Flux<Int> {
        println("Cache miss !!!")
        return Flux.just(1, 2, 3, 4, 5)
    }

    fun handleCacheMiss(key: String): Flux<Int> {
        println("Cache miss !!!")
        return Flux.just(1, 2, 3, 4, 5)
    }

    @JvmStatic
    fun main(args: Array<String>) {
        doFunctionCache("keyA").blockLast()
//        doMapCache("keyA").blockLast()
//        doCaffeineCache("keyA").blockLast()
    }

    fun doFunctionCache(itemKey: String): Flux<Int> {
        val intCacheMap: MutableMap<String, MutableList<Int>> = mutableMapOf()
        val itemVal: Flux<Int> = CacheFlux.lookup(
            { k ->
                val rst: MutableList<Int>? = intCacheMap[k]
                return@lookup Mono.justOrEmpty(rst)
                    .flatMap { l ->
                        Flux.fromIterable(l)
                            .materialize()
                            .collectList()
                    }
            },
            itemKey)
            .onCacheMissResume(handleCacheMiss(itemKey))
            .andWriteWith { key, sig ->
                Mono.fromRunnable {
                    intCacheMap[key] = sig.stream()
                        .filter { it.type == SignalType.ON_NEXT }
                        .map { it.get() }
                        .toList() as MutableList<Int>
                }
            }
        return itemVal.doOnNext { v -> println("key: $itemKey, val is $v") }
    }

//    fun doMapCache(itemKey: String): Flux<Int> {
//    }

//    fun doCaffeineCache(itemKey: String): Flux<Int> {
//    }
}
