package com.iamslash.excaffine.app

import com.github.benmanes.caffeine.cache.AsyncLoadingCache
import com.github.benmanes.caffeine.cache.Cache
import com.github.benmanes.caffeine.cache.Caffeine
import com.github.benmanes.caffeine.cache.LoadingCache
import com.iamslash.excaffine.common.DataObject
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeUnit.MINUTES


object PopulatingApp {

    @JvmStatic
    fun main(args: Array<String>) {
//        doManualPopulating()
//        doSynchronousLoading()
        doAsynchronousLoading()
    }

    private fun doManualPopulating() {
        val cache: Cache<String, DataObject> = Caffeine.newBuilder()
            .expireAfterWrite(1, MINUTES)
            .maximumSize(100)
            .build<String, DataObject>()

        val itemKey: String = "a";
        val itemVal: DataObject = cache.get(itemKey) { k ->
            DataObject["Data for $k"]
        }!!

        println("$itemKey : ${itemVal.toString()}")
    }

    private fun doSynchronousLoading() {
        val cache: LoadingCache<String, DataObject> = Caffeine.newBuilder()
            .maximumSize(100)
            .expireAfterWrite(1, TimeUnit.MINUTES)
            .build { k -> DataObject["Data for $k"] }

        for (k in listOf("A", "B", "C")) {
            val v = cache.get(k)
            println("$k : ${v!!.data}")
        }

        val itemValMap: Map<String, DataObject> = cache.getAll(listOf("A", "B"))
        println(itemValMap.size)
    }

    private fun doAsynchronousLoading() {
        val cache: AsyncLoadingCache<String, DataObject> = Caffeine.newBuilder()
            .maximumSize(100)
            .expireAfterWrite(1, TimeUnit.MINUTES)
            .buildAsync { k -> DataObject["Data for $k"] }

        val itemKey = "A"
        cache.get(itemKey).thenAccept { itemVal ->
            println("$itemKey : ${itemVal!!.data}")
        }

        cache.getAll(listOf("A", "B", "C"))
            .thenAccept { itemMap -> println("${itemMap.size}") }
    }

}
