package com.iamslash.mockmvc

import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod.POST
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/mockmvc")
class MockMvcController {

    companion object {
        const val ERROR = "invalid user"
    }

    @RequestMapping(value = ["/validate"], method = [POST], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun validate(@RequestBody request: Request): Response {
        val error = if (request.name.first == "admin") {
            null
        } else {
            ERROR
        }
        return Response(error)
    }
}
