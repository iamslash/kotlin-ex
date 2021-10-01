package com.iamslash.exjackson.jsontypeinfo

data class Circle(var radius: Int = 0): Shape() {

    override fun toString(): String {
        return "Circle{" +
               "radius=" + radius +
               '}'
    }

}
