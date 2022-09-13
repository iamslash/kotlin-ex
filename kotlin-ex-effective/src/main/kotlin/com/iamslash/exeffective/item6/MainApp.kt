package com.iamslash.exeffective.item6

import java.lang.IllegalStateException

// Item 6: Prefer standard errors to custom ones
//
// * Use Exceptions in std lib instead of custom Exception.
// * IllegalArgumentException
// * IllegalStateException
// * IndexOutOfBoundsException
// * ConcurrentModificationException
// * UnsupportedOperationException
// * NoSuchElementException

// AsIs:
inline fun <reified T> String.readObject(): T {
    if (incorrectSign) {
        throw JsonParsingException()
    }
    return result
}
// ToBe:
inline fun <reified T> String.readObject(): T {
    if (incorrectSign) {
        throw IllegalStateException
    }
    return result
}
