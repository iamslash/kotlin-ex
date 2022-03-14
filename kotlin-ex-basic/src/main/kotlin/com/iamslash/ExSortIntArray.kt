package com.iamslash

fun main() {
    // Sort intArray
    val a = intArrayOf(5, 4, 3, 2, 1)
    val sortList = a.sort()
    val sortDescending = a.sortDescending()

    val sorted = a.sorted()
    val sortedBy = a.sortedBy { kotlin.math.abs(it) }
    val sortedDescending = a.sortedDescending()
    val sortedByDescending = a.sortedByDescending { kotlin.math.abs(it) }
    val sortedWith = a.sortedWith { a, b ->
        when {
            a < b -> -1
            a > b -> 1
            else -> 0
        }
    }
}
