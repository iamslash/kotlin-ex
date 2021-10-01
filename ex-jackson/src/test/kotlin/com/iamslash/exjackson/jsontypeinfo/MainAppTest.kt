package com.iamslash.exjackson.jsontypeinfo

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class MainAppTest {

    val mapper = jacksonObjectMapper()

    @Test
    fun basic() {
        val movie = Movie("Endgame", "Marvel", 9.2f)
        val serialized = mapper.writeValueAsString(movie)

        val json = """{"name":"Endgame","studio":"Marvel","rating":9.2}"""
        assertEquals(serialized, json)
    }
}
