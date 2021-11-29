package com.iamslash

import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.test.StepVerifier

internal class ErrorAppTest {

    private val errorApp: ErrorApp = mockk(relaxed = true)

    @Test
    fun `onErrorReturn should return fallBack value when error has occurred`() {
        // given
        every { errorApp.getPost(1) }.returnsMany(Mono.just("1"), Mono.just("2"))
        every { errorApp.getPost(2) } throws RuntimeException()
        // when
        val flux: Flux<String> = Flux.just(1, 2, 3)
            .flatMap { postId -> errorApp.getPost(postId) }
            .onErrorReturn("22")
        // then
        StepVerifier.create(flux)
            .expectNext("1")
            .expectNext("22")
            .verifyComplete()
    }

    @Test
    fun `onErrorResume should resume new publisher when error has occurred`() {
        // given
        every { errorApp.getPost(1) }.returns(Mono.just("a"))
        every { errorApp.getPost(2) } throws RuntimeException("onErrorResume Exception")
        every { errorApp.getPost(3) } returns Mono.just("c")
        // when
        val flux: Flux<String> = Flux.just(1, 2, 3, 4)
            .flatMap { postId -> errorApp.getPost(postId) }
            .onErrorResume { e ->
                println(e.localizedMessage)
                return@onErrorResume Flux.just("bb", "cc")
            }
        // then
        StepVerifier.create(flux)
            .expectNext("a", "bb", "cc")
            .verifyComplete()
    }

    @Test
    fun `onErrorResume should resume new publisher when error of specific type has occurred`() {
        // given
        every { errorApp.getPost(1) }.returns(Mono.just("a"))
        every { errorApp.getPost(2) } throws RuntimeException("onErrorResume Exception")
        every { errorApp.getPost(3) } returns Mono.just("c")
        // when
        val flux: Flux<String> = Flux.just(1, 2, 3, 4)
            .flatMap { postId -> errorApp.getPost(postId) }
            .onErrorResume(java.lang.RuntimeException::class.java) { e ->
                println(e.localizedMessage)
                return@onErrorResume Flux.just("bb", "cc")
            }
        // then
        StepVerifier.create(flux)
            .expectNext("a", "bb", "cc")
            .verifyComplete()
    }

    @Test
    fun `onErrorResume should resume new publisher when predicated error has occurred`() {
        // given
        every { errorApp.getPost(1) }.returns(Mono.just("a"))
        every { errorApp.getPost(2) } throws RuntimeException("onErrorResume Exception")
        every { errorApp.getPost(3) } returns Mono.just("c")
        // when
        val flux: Flux<String> = Flux.just(1, 2, 3, 4)
            .flatMap { postId -> errorApp.getPost(postId) }
            .onErrorResume({ e -> e.localizedMessage.contains("Exception") }) { e ->
                println(e.localizedMessage)
                return@onErrorResume Flux.just("bb", "cc")
            }
        // then
        StepVerifier.create(flux)
            .expectNext("a", "bb", "cc")
            .verifyComplete()
    }


    @Test
    fun `onErrorContinue should continue when error has occurred`() {
        // given
        every { errorApp.getPost(1) }.returns(Mono.just("a"))
        every { errorApp.getPost(2) } throws RuntimeException("onErrorContinue Exception")
        every { errorApp.getPost(3) } returns Mono.just("c")
        // when
        val flux: Flux<String> = Flux.just(1, 2, 3)
            .flatMap { postId -> errorApp.getPost(postId) }
            .onErrorContinue { e, o ->
                println("Error has occurred at $o with ${e.localizedMessage}")
            }
        // then
        StepVerifier.create(flux)
            .expectNext("a", "c")
            .verifyComplete()
    }

    @Test
    fun `onErrorContinue should continue when error of specific type has occurred`() {
        // given
        every { errorApp.getPost(1) }.returns(Mono.just("a"))
        every { errorApp.getPost(2) } throws RuntimeException("onErrorContinue Exception")
        every { errorApp.getPost(3) } returns Mono.just("c")
        // when
        val flux: Flux<String> = Flux.just(1, 2, 3)
            .flatMap { postId -> errorApp.getPost(postId) }
            .onErrorContinue(RuntimeException::class.java) { e, o ->
                println("Error has occurred at $o with ${e.localizedMessage}")
            }
        // then
        StepVerifier.create(flux)
            .expectNext("a", "c")
            .verifyComplete()
    }

    @Test
    fun `onErrorContinue should continue when predicated error has occurred`() {
        // given
        every { errorApp.getPost(1) }.returns(Mono.just("a"))
        every { errorApp.getPost(2) } throws RuntimeException("onErrorContinue Exception")
        every { errorApp.getPost(3) } returns Mono.just("c")
        // when
        val flux: Flux<String> = Flux.just(1, 2, 3)
            .flatMap { postId -> errorApp.getPost(postId) }
            .onErrorContinue({ e: Throwable -> e.localizedMessage.contains("Exception") }) { e, o ->
                println("Error has occurred at $o with ${e.localizedMessage}")
            }

        // then
        StepVerifier.create(flux)
            .expectNext("a", "c")
            .verifyComplete()
    }
}
