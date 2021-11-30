package com.iamslash

import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

object Fluxapp {

    @JvmStatic
    fun main(args: Array<String>) {
        doFlux()
    }

    private fun doFlux() {
        val flux = Flux.just("Hello Flux", "Bye Flux", "Welcome Flux")
        flux.map { s -> s.toUpperCase() }
            .flatMap { s -> Flux.just(s) }
            .subscribe { s -> println(s) }
    }
}
