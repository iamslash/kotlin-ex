package com.iamslash.exeffective.item1

import kotlin.concurrent.thread

// Item 1: Limit mutability
//
// * Prefer val over var.
// * Prefer an immutable property over a mutable one.
// * Prefer objects and classes that are immutable over mutable.
// * If you need them to change, consider making them immutable data classes, and using copy.
// * When you hold state, prefer read-only over mutable collections.
// * Design your mutation points wisely and do not produce unnecessary ones.
// * Do not expose mutable objects.
fun main() {
    // Mutable variables
    var a = 10
    var b: MutableList<Int> = mutableListOf()
    // Mutable classes
    class BankAccount {
        var balance = 0.0
            private set
        fun deposit(depositAmount: Double) {
            balance += depositAmount
        }
        @Throws(InsufficientFunds::class)
        fun withdraw(withdrawAmount: Double) {
            if (balance < withdrawAmount) {
                throw InsufficientFunds()
            }
            balance -= withdrawAmount
        }
        inner class InsufficientFunds: Exception()
    }
    val account = BankAccount()
    println(account.balance)  // 0.0
    account.deposit(100.0)
    println(account.balance)  // 100.0
    account.withdraw(50.0)
    println(account.balance)  // 50.0

    // state management is hard, because:
    // * It is harder to understand and debug a program with many mutating points.
    // * Mutability makes it harder to reason about the code.
    // * It requires proper synchronization in multithreaded programs.
    // * Mutable elements are harder to test.
    // * When state mutates, often some other classes need to be notified about this change.

    // manage shared state is hard
    var num = 0
    for (i in 1..1000) {
        thread {
            Thread.sleep(10)
            num += 1
        }
    }
    Thread.sleep(5000)
    print(num)  // Very unlikely to be 1000
    // Every time a different number
//    // Coroutine version
//    var num = 0
//    coroutineScope {
//        for (i in 1..1000) {
//            launch {
//                delay(10)
//                num += 1
//            }
//        }
//    }
//    print(num)  // Every time a different number
    // Thread safe version
    val lock = Any()
    var numFoo = 0
    for (i in 1..1000) {
        thread {
            Thread.sleep(10)
            synchronized(lock) {
                numFoo += 1
            }
        }
    }
    Thread.sleep(1000)
    print(numFoo)

    //////////////////////////////////////////////////
    // Limiting mutability in Kotlin

    //////////////////////////////////////////////////
    // ..Read-only properties val

    //////////////////////////////////////////////////
    // ..Separation between mutable and read-only collections

    //////////////////////////////////////////////////
    // ..copy in data classes

    //////////////////////////////////////////////////
    // Different kinds of mutation points

    //////////////////////////////////////////////////
    // Do not leak mutation points
    data class User(val name: String)
    class UserRepository {
        private val storedUsers: MutableMap<Int, String> = mutableMapOf()
        fun loadAll(): MutableMap<Int, String> = storedUsers
    }
    val userRepository = UserRepository()
    val storedUsers = userRepository.loadAll()
    storedUsers[4] = "David"  // Pretty dangerous
    print(userRepository.loadAll())  // {4=David"}
}
