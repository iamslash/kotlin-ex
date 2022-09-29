package com.iamslash.exeffective.item4

// Item 4: Do not expose inferred types
//
// * We should not hide return type.
fun main() {
    // Type mismatch happens
    open class Animal
    class Zebra: Animal()
    // bad:
    run {
        var animal = Zebra()
//        animal = Animal()  // ERROR: Type mismatch
    }
    // good:
    run {
        var animal: Animal = Zebra()
        animal = Animal()
    }
}

// We should not hide return type.
class Fiat126P
interface Car
fun main_2() {
    run {
        val DEFAULT_CAR = Fiat126P()
//        // bad:
//        interface CarFactory {
//            fun produce() = DEFAULT_CAR
//        }
//        // good:
//        interface CarFactory {
//            fun produce(): Car = DEFAULT_CAR
//        }
    }
}
