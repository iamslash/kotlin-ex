package effective

class Item1 {
    fun doit() {
        val l = mutableListOf(5, 4, 3, 2, 1)
        val asc = l.sort()
        println(asc)
    }
}
