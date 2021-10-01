package com.iamslash.exjackson.jsontypeinfo

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper

object MainApp {

    @JvmStatic
    fun main(args: Array<String>) {
        val mapper = jacksonObjectMapper()
        val v = View(listOf(Rectangle(3, 6), Circle(5)))

        println("-- serializing --")
        val s = mapper.writeValueAsString(v)
        println(s)

        println("-- deserializing --")
        val view = mapper.readValue(s, View::class.java)
        println(view)
        // {"shapes":[{"className":"com.iamslash.exjackson.jsontypeinfo.Rectangle","w":3,"h":6},{"className":"com.iamslash.exjackson.jsontypeinfo.Circle","radius":5}]}
        // {"shapes":[{"@class":"com.iamslash.exjackson.jsontypeinfo.Rectangle","w":3,"h":6},{"@class":"com.iamslash.exjackson.jsontypeinfo.Circle","radius":5}]}
    }
}
