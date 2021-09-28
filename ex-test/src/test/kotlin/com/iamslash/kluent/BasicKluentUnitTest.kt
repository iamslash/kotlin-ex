package com.iamslash.kluent

import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.api.Test

class BasicKluentUnitTest {

    @Test
    fun `isEmpty should return true for empty lists`() {
        val list = listOf<String>()
        list::isEmpty shouldBeEqualTo true
    }

}
