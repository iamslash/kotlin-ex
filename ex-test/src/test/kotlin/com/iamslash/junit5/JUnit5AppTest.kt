package com.iamslash.junit5

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

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

    @Test
    fun `isEmpty should return true for empty lists`() {
        val list = listOf<String>()
        assertTrue(list::isEmpty)
    }

    @Test
    @Disabled
    fun `3 is equal to 4`() {
        assertEquals(3, 4) {
            "Three does not equal four"
        }
    }
}
