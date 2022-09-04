package com.iamslash.exbasic.sort

fun main() {
    // sort Array<Int>
    val l = arrayListOf<Int>(5, 4, 3, 2, 1)
    l.sort()
    l.sortDescending()
    l.sortBy { kotlin.math.abs(it) }
    l.sortByDescending { kotlin.math.abs(it) }
    l.sortWith { a, b -> a - b}
    l.sortWith(Comparator<Int>{ a, b -> a - b })

    // sorted Array<Int>
    val sorted = l.sorted()
    val sortedBy = l.sortedBy { kotlin.math.abs(it) }
    val sortedDescending = l.sortedDescending()
    val sortedByDescending = l.sortedByDescending { kotlin.math.abs(it) }
    val sortedWith = l.sortedWith { a, b -> a - b }
    val sortedWithComparator = l.sortedWith(Comparator<Int>{ a, b -> a - b })
}
