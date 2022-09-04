package com.iamslash.exbasic.sort

fun main() {
    val grid: Array<IntArray> = arrayOf(intArrayOf(3, 1), intArrayOf(2, 5))
    val h = grid.size
    val w = grid[0].size
    grid.forEach {
        println(it.contentToString())
    }
    val coordList: MutableList<IntArray> = mutableListOf()
    for (y in 0 until h) {
        for (x in 0 until w) {
            coordList.add(intArrayOf(y, x))
        }
    }
    println("--------------------------------------------------")
    coordList.forEach({ println(it.contentToString()) })
    println("--------------------------------------------------")
    coordList.sortWith(Comparator<IntArray>{ a, b ->
        val c = grid[a[0]][a[1]]
        val d = grid[b[0]][b[1]]
        c - d
    })
    coordList.forEach({ println("grid[${it[0]}][${it[1]}]: ${grid[it[0]][it[1]]}") })
    println("--------------------------------------------------")
}
