package com.iamslash

fun main() {
    // sort intArray
    val a = intArrayOf(5, 4, 3, 2, 1)
    a.sort()
    a.sortDescending()

    // sorted intArray
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
    val sortedWithComparator = a.sortedWith(Comparator<Int>{ a, b ->
        when {
            a < b -> -1
            a > b -> 1
            else -> 0
        }
    })
}
