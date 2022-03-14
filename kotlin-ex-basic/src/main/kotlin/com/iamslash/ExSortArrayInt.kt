package com.iamslash

fun main() {
    // Sort Array<Int>
    val l = mutableListOf<Int>(5, 4, 3, 2, 1)
    val sortList = l.sort()
    val sortDescending = l.sortDescending()
    val sortByList = l.sortBy { kotlin.math.abs(it) }
    val sortByDescending = l.sortByDescending { kotlin.math.abs(it) }
    val sortWith = l.sortWith { a, b ->
        when {
            a < b -> -1
            a > b -> 1
            else -> 0
        }
    }
    val sorted = l.sorted()
    val sortedBy = l.sortedBy { kotlin.math.abs(it) }
    val sortedDescending = l.sortedDescending()
    val sortedByDescending = l.sortedByDescending { kotlin.math.abs(it) }
    val sortedWith = l.sortedWith { a, b ->
        when {
            a < b -> -1
            a > b -> 1
            else -> 0
        }
    }
}
