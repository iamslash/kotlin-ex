package com.iamslash.excaffine.app

import com.github.benmanes.caffeine.cache.Caffeine
import com.github.benmanes.caffeine.cache.LoadingCache
import com.iamslash.excaffine.common.DataObject

object StatisticsApp {

    @JvmStatic
    fun main(args: Array<String>) {
        val cache: LoadingCache<String, DataObject> = Caffeine.newBuilder()
            .maximumSize(100)
            .recordStats()
            .build { k -> DataObject["Data for $k"]}
        cache.get("A")
        cache.get("A")

        println("cache's hit count is ${cache.stats().hitCount()}")
        println("cache's miss count is ${cache.stats().missCount()}")
    }
}
