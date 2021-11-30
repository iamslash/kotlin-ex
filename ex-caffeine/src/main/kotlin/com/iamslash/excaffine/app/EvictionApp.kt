package com.iamslash.excaffine.app

import com.github.benmanes.caffeine.cache.Caffeine
import com.github.benmanes.caffeine.cache.Expiry
import com.github.benmanes.caffeine.cache.LoadingCache
import com.iamslash.excaffine.common.DataObject
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeUnit.SECONDS


object EvictionApp {

    @JvmStatic
    fun main(args: Array<String>) {
//        doSizeBasedEviction()
//        doWeightBasedEviction()
//        doTimeBasedEvictionExpireAfterAccess()
//        doTimeBasedEvictionExpireAfterWrite()
//        doTimeBasedEvictionCustomPolicy()
        doReferencedBasedEviction()
    }

    fun doSizeBasedEviction() {
        val cache: LoadingCache<String, DataObject> = Caffeine.newBuilder()
            .maximumSize(1)
            .build { k -> DataObject["Data for $k"] }
        cache.get("A")
        println("cache's estimated size is ${cache.estimatedSize()}")
        cache.get("B")
        println("cache's estimated size is ${cache.estimatedSize()}")
        cache.get("C")
        println("cache's estimated size is ${cache.estimatedSize()}")
        // the cache eviction is executed asynchronously.
        cache.cleanUp()
        println("cache's estimated size is ${cache.estimatedSize()}")
    }

    fun doWeightBasedEviction() {
        val cache: LoadingCache<String, DataObject> = Caffeine.newBuilder()
            .maximumWeight(10)
            .weigher { _: String, _: DataObject -> 5 }
            .build { k -> DataObject["Data for $k"] }
        cache.get("A")
        cache.get("B")
        println("cache's estimated size is ${cache.estimatedSize()}")
        cache.get("C")
        cache.cleanUp()
        println("cache's estimated size is ${cache.estimatedSize()}")
    }

    fun doTimeBasedEvictionExpireAfterAccess() {
        val cache: LoadingCache<String, DataObject> = Caffeine.newBuilder()
            .expireAfterAccess(1, TimeUnit.SECONDS)
            .build { k -> DataObject["Data for $k"] }
        cache.get("A")
        println("cache's estimated size is ${cache.estimatedSize()}")
        Thread.sleep(2000)
        // the cache eviction is executed asynchronously.
        cache.cleanUp()
        println("cache's estimated size is ${cache.estimatedSize()}")
    }

    fun doTimeBasedEvictionExpireAfterWrite() {
        val cache: LoadingCache<String, DataObject> = Caffeine.newBuilder()
            .expireAfterWrite(1, TimeUnit.SECONDS)
            .weakKeys()
            .weakValues()
            .build { k -> DataObject["Data for $k"] }
        cache.get("A")
        println("cache's estimated size is ${cache.estimatedSize()}")

        Thread.sleep(100)
        cache.cleanUp()
        println("cache's estimated size is ${cache.estimatedSize()}")

        Thread.sleep(2000)
        // the cache eviction is executed asynchronously.
        cache.cleanUp()
        println("cache's estimated size is ${cache.estimatedSize()}")
    }

    fun doTimeBasedEvictionCustomPolicy() {
        val cache = Caffeine.newBuilder().expireAfter(object: Expiry<String, DataObject> {
            override fun expireAfterCreate(
                key: String, value: DataObject, currentTime: Long,
            ): Long {
                return (value.data.length * 1000).toLong()
            }

            override fun expireAfterUpdate(
                key: String, value: DataObject, currentTime: Long, currentDuration: Long,
            ): Long {
                return currentDuration
            }

            override fun expireAfterRead(
                key: String, value: DataObject, currentTime: Long, currentDuration: Long,
            ): Long {
                return currentDuration
            }
        }).build { k: String -> DataObject["Data for $k"] }
    }

    fun doReferencedBasedEviction() {
        val cacheWeakRef: LoadingCache<String, DataObject> = Caffeine.newBuilder()
            .expireAfterWrite(1, TimeUnit.SECONDS)
            .weakKeys()
            .weakValues()
            .build { k -> DataObject["Data for $k"] }
        val cacheSoftRef: LoadingCache<String, DataObject> = Caffeine.newBuilder()
            .expireAfterWrite(1, TimeUnit.SECONDS)
            .softValues()
            .build { k -> DataObject["Data for $k"] }
    }
}
