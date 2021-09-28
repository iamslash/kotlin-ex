package com.iamslash

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@ConfigurationPropertiesScan
@SpringBootApplication
class ExTestSpringApplication

fun main(args: Array<String>) {
    runApplication<ExTestSpringApplication>(*args)
}
