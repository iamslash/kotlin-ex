package com.iamslash

import reactor.core.publisher.Mono

class RetryApp {
    public fun getPost(postId: Int): Mono<String> {
        return Mono.empty()
    }
}
