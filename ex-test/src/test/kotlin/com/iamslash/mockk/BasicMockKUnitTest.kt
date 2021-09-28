package com.iamslash.mockk

import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.slot
import io.mockk.spyk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class BasicMockKUnitTest {

    @Test
    fun givenServiceMock_whenCallingMockedMethod_thenCorrectlyVerified() {
        // given
        val service = mockk<TestableService>()
        every { service.getDataFromDb("Expected Param") } returns "Expected Output"
        // when
        val actual = service.getDataFromDb("Expected Param")
        // then
        // Verifies that calls were made in the past. Part of DSL
        verify { service.getDataFromDb("Expected Param") }
        assertEquals("Expected Output", actual)
    }

    @Test
    fun givenServiceSpy_whenMockingOnlyOneMethod_thenOtherMethodsShouldBehaveAsOriginalObject() {
        // given
        val service = spyk<TestableService>()
        every { service.getDataFromDb(any()) } returns "Mocked Output"

        // when checking mocked method
        val firstActual = service.getDataFromDb("Any Param")
        // then
        assertEquals("Mocked Output", firstActual)

        // when checking not mocked method
        val secondActual = service.doSomethingElse("Any Param")
        // then
        assertEquals("I don't want to!", secondActual)
    }

    @Test
    fun givenRelaxedMock_whenCallingNotMockedMethod_thenReturnDefaultValue() {
        // given
        val service = mockk<TestableService>(relaxed = true)
        // when
        val result = service.getDataFromDb("Any Param")
        // then
        assertEquals("", result)
    }

    @Test
    fun givenObject_whenMockingIt_thenMockedMethodShouldReturnProperValue() {
        // given
        val service = TestableService()
        mockkObject(service)
        // when calling not mocked method
        val firstResult = service.getDataFromDb("Any Param")
        // then return real response
        assertEquals("Value from DB", firstResult)

        // when calling mocked method
        every { service.getDataFromDb(any()) } returns "Mocked Output"
        val secondResult = service.getDataFromDb("Any Param")
        // then return mocked response
        assertEquals("Mocked Output", secondResult)
    }

    @Test
    fun givenMock_whenCapturingParamValue_thenProperValueShouldBeCaptured() {
        // given
        val service = mockk<TestableService>()
        val slot = slot<String>()
        every { service.getDataFromDb(capture(slot)) } returns "Expected Output"
        // when
        service.getDataFromDb("Expected Param")
        // then
        assertEquals("Expected Param", slot.captured)
    }

    @Test
    fun givenMock_whenCapturingParamsValues_thenProperValuesShouldBeCaptured() {
        // given
        val service = mockk<TestableService>()
        val list = mutableListOf<String>()
        every { service.getDataFromDb(capture(list)) } returns "Expected Output"
        // when
        service.getDataFromDb("Expected Param 1")
        service.getDataFromDb("Expected Param 2")
        // then
        assertEquals(2, list.size)
        assertEquals("Expected Param 1", list[0])
        assertEquals("Expected Param 2", list[1])
    }

}
