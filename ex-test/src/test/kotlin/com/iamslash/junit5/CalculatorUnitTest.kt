package com.iamslash.junit5

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Tags
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.function.Executable

class CalculatorUnitTest {
    private val calculator = Calculator()

    @Test
    fun `Adding 1 and 3 should be equal to 4`() {
        assertEquals(4, calculator.add(1, 3))
    }

    @Test
    fun `Dividing by zero should throw the DivideByZeroException`() {
        val exception = assertThrows<DivideByZeroException> {
            calculator.divide(5, 0)
        }
        assertEquals(5, exception.numerator)
    }

    @Test
    fun `The sqaure of a number should be equal to that number multiplied in itself`() {
        assertAll(
            { assertEquals(1, calculator.square(1)) },
            { assertEquals(4, calculator.square(2)) },
            { assertEquals(9, calculator.square(3)) },
        )
    }

    @TestFactory
    fun testSquaresFactory() = listOf(
        DynamicTest.dynamicTest("when I calculate 1^2 then I get 1") {
            assertEquals(1, calculator.square(1))
        },
        DynamicTest.dynamicTest("when I calculate 2^2 then I get 4") {
            assertEquals(4, calculator.square(2))
        },
        DynamicTest.dynamicTest("when I calculate 3^2 then I get 9") {
            assertEquals(9, calculator.square(3))
        },
    )

    private val squaresTestData = listOf(
        1 to 1,
        2 to 4,
        3 to 9,
        4 to 16,
        5 to 25)

    @TestFactory
    fun testSquaresFactory3() = squaresTestData
        .map { (input, expected) ->
            DynamicTest.dynamicTest("when I calculate $input^2 then I get $expected") {
                assertEquals(expected, calculator.square(input))
            }
        }

    @TestFactory
    fun testSquareRootsFactory3() = squaresTestData
        .map { (expected, input) ->
            DynamicTest.dynamicTest("I calculate the square root of $input then I get $expected") {
                assertEquals(expected.toDouble(), calculator.squareRoot(input))
            }
        }

    @Tags(
        Tag("slow"),
        Tag("logarithms")
    )
    @Test
    fun `Log to base 2 of 8 should be equal to 3`() {
        assertEquals(3.0, calculator.log(2, 8))
    }

}
