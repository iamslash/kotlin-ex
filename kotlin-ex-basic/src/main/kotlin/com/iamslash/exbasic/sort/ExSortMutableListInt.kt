package com.iamslash.exbasic.sort

fun main() {
    // sort MutableList<Int>
    val l = mutableListOf<Int>(5, 4, 3, 2, 1)
    l.sort()
    l.sortDescending()
    l.sortBy { kotlin.math.abs(it) }
    l.sortByDescending { kotlin.math.abs(it) }
    l.sortWith { a, b ->
        when {
            a < b -> -1
            a > b -> 1
            else -> 0
        }
    }
    l.sortWith(Comparator<Int>{ a, b ->
        when {
            a < b -> -1
            a > b -> 1
            else -> 0
        }
    })

    // sorted MutableList<Int>
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
    val sortedWithComparator = l.sortedWith(Comparator<Int>{ a, b ->
        when {
            a < b -> -1
            a > b -> 1
            else -> 0
        }
    })
}
