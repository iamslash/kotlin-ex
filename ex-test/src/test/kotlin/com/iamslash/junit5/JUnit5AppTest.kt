package com.iamslash.junit5

import com.iamslash.junit5.JUnit5App
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class JUnit5AppTest {

    @BeforeEach
    fun setUp() {
    }

    @AfterEach
    fun tearDown() {
    }

    @Test
    fun `JUnit5App hello() should return "Hello World"`() {
        assertEquals("Hello World", JUnit5App().hello())
    }
}
