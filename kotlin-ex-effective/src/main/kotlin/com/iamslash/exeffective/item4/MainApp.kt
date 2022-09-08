package com.iamslash.exeffective.item4

// Item 4: Do not expose inferred types
//
// * we should not hide it.
fun main() {
    // Type mismatch happens
    open class Animal
    class Zebra: Animal()
    // AsIs:
    run {
        var animal = Zebra()
        animal = Animal()  // ERROR: Type mismatch
    }
    // ToBe:
    run {
        var animal: Animal = Zebra()
        animal = Animal()
    }

    // We should not hide return type.
    class Fiat126P
    interface Car
    val DEFAULT_CAR = Fiat126P()
    // AsIs:
    interface CarFactory {
        fun produce() = DEFAULT_CAR
    }
    // ToBe:
    interface CarFactory {
        fun produce(): Car = DEFAULT_CAR
    }
}
