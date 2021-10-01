package com.iamslash.exjackson.jsontypeinfo

data class View(var shapes: List<Shape>) {

    override fun toString(): String {
        return "View{" +
               "shapes=" + shapes +
               '}'
    }

}
