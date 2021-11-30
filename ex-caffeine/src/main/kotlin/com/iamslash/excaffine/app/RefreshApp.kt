package com.iamslash.excaffine.app

import com.github.benmanes.caffeine.cache.Caffeine
import com.github.benmanes.caffeine.cache.LoadingCache
import com.iamslash.excaffine.common.DataObject
import java.util.concurrent.TimeUnit

object RefreshApp {

    @JvmStatic
    fun main(args: Array<String>) {
        // expireAfter:
        //   When the expired entry is requested, an execution blocks until the new value would have been calculated by the build Function.
        // refreshAfterWrite:
        //   if the entry is eligible for the refreshing, then the cache would return an old value and asynchronously reload the value.
        val cache: LoadingCache<String, DataObject> = Caffeine.newBuilder()
            .refreshAfterWrite(1, TimeUnit.SECONDS)
            .build { k -> DataObject["Data for $k"] }
    }
}
