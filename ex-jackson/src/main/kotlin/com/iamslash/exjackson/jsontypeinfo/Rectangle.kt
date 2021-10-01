package com.iamslash.exjackson.jsontypeinfo

data class Rectangle(var w: Int = 0, var h: Int = 0): Shape() {

    override fun toString(): String {
        return "Rectangle{" +
               "w=" + w +
               ", h=" + h +
               '}'
    }

}
