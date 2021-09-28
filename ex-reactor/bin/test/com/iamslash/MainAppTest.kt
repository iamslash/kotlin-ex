package com.iamslash

import kotlinx.coroutines.launch
import kotlinx.coroutines.reactive.awaitFirst
import kotlinx.coroutines.reactive.awaitFirstOrNull
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import reactor.core.publisher.Mono

internal class MainAppTest {

    @BeforeEach
    fun setUp() {
    }

    @AfterEach
    fun tearDown() {
    }

    @DisplayName("hello")
    @Test
    fun hello() {
        val mainApp = MainApp()
        assertEquals("Hello World", mainApp.hello())
    }

    @Test
    fun givenMonoEmpty_thenThrowsException() = runBlockingTest{
        val monoEmpty = Mono.empty<Void>()
        assertEquals(null, monoEmpty.awaitFirstOrNull())
        assertThrows(NoSuchElementException::class.java) {
            runBlockingTest {
                monoEmpty.awaitFirst()
            }
        }
    }
}
