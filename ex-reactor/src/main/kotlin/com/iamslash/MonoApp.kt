package com.iamslash

import reactor.core.publisher.Mono
import java.util.concurrent.CountDownLatch

object MonoApp {

    @JvmStatic
    fun main(args: Array<String>) {
        doMono()
    }

    private fun doMono() {
        val mono = Mono.just("Hello Mono")
        mono.map { s -> s.toUpperCase() }
            .flatMap { s -> Mono.just(s) }
            .subscribe { s -> println(s) }
    }
}
