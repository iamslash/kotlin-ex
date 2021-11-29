package com.iamslash

import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import reactor.util.retry.Retry
import java.time.Duration

internal class RetryAppTest {

    private val retryApp: RetryApp = mockk(relaxed = true)

    @Test
    fun `retry should retry from the first forever when error has occurred`() {
        // given
        every { retryApp.getPost(1) } returns(Mono.just("a"))
        every { retryApp.getPost(2) } throws RuntimeException() andThen (Mono.just("b"))
        every { retryApp.getPost(3) } returns(Mono.just("c"))
        // when
        val flux: Flux<String> = Flux.just(1, 2, 3)
            .flatMap { postId -> retryApp.getPost(postId) }
            .retry()
        // then
        StepVerifier.create(flux)
            .expectNext("a", "a", "b", "c")
            .verifyComplete()
    }

    @Test
    fun `retry should retry from the first n times when error has occurred`() {
        // given
        every { retryApp.getPost(1) } returns(Mono.just("a"))
        every { retryApp.getPost(2) } throws RuntimeException() andThenThrows RuntimeException() andThen (Mono.just("b"))
        every { retryApp.getPost(3) } returns(Mono.just("c"))
        // when
        val flux: Flux<String> = Flux.just(1, 2, 3)
            .flatMap { postId -> retryApp.getPost(postId) }
            .log()
            .retry(2)
        // then
        StepVerifier.create(flux)
            .expectNext("a", "a", "a","b", "c")
            .verifyComplete()
    }

    @Test
    fun `retry should retry from the first Retry max times when error has occurred`() {
        // given
        every { retryApp.getPost(1) } returns(Mono.just("a"))
        every { retryApp.getPost(2) } throws RuntimeException() andThenThrows RuntimeException() andThen (Mono.just("b"))
        every { retryApp.getPost(3) } returns(Mono.just("c"))
        // when
        val flux: Flux<String> = Flux.just(1, 2, 3)
            .flatMap { postId -> retryApp.getPost(postId) }
            .retryWhen(Retry.max(2))
        // then
        StepVerifier.create(flux)
            .expectNext("a", "a", "a","b", "c")
            .verifyComplete()
    }

    @Test
    fun `retry should retry from the first fixed delay when error has occurred`() {
        // given
        every { retryApp.getPost(1) } returns(Mono.just("a"))
        every { retryApp.getPost(2) } throws RuntimeException() andThenThrows RuntimeException() andThen (Mono.just("b"))
        every { retryApp.getPost(3) } returns(Mono.just("c"))
        // when
        val flux: Flux<String> = Flux.just(1, 2, 3)
            .flatMap { postId -> retryApp.getPost(postId) }
            .retryWhen(Retry.fixedDelay(2, Duration.ofMillis(200)))
        // then
        StepVerifier.create(flux)
            .expectNext("a", "a", "a","b", "c")
            .verifyComplete()
    }

    @Test
    fun `retry should retry from the first backoff when error has occurred`() {
        // given
        every { retryApp.getPost(1) } returns(Mono.just("a"))
        every { retryApp.getPost(2) } throws RuntimeException() andThenThrows RuntimeException() andThen (Mono.just("b"))
        every { retryApp.getPost(3) } returns(Mono.just("c"))
        // when
        val flux: Flux<String> = Flux.just(1, 2, 3)
            .flatMap { postId -> retryApp.getPost(postId) }
            .retryWhen(Retry.backoff(2, Duration.ofMillis(200)))
        // then
        StepVerifier.create(flux)
            .expectNext("a", "a", "a","b", "c")
            .verifyComplete()
    }

}
