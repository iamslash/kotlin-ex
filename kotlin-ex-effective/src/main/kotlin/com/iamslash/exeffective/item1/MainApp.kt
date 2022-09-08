package com.iamslash.exeffective.item1

import kotlin.concurrent.thread
import kotlin.properties.Delegates

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
    run {
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
    }

    // state management is hard, because:
    // * It is harder to understand and debug a program with many mutating points.
    // * Mutability makes it harder to reason about the code.
    // * It requires proper synchronization in multithreaded programs.
    // * Mutable elements are harder to test.
    // * When state mutates, often some other classes need to be notified about this change.

    run {
        // managing shared state is hard
        var num = 0
        for (i in 1..1000) {
            thread {
                Thread.sleep(10)
                num += 1
            }
        }
        Thread.sleep(5000)
        print(num)  // WRONG: Very unlikely to be 1000
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
        //    print(num)  // WRONG: Every time a different number
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
    }

    //////////////////////////////////////////////////
    // Limiting mutability in Kotlin

    //////////////////////////////////////////////////
    // ..Read-only properties val
    run {
        // read-only property
        val a = 10
        // a = 20  // ERROR

        // read-only property can have mutable collection
        val list = mutableListOf(1, 2, 3)
        list.add(4)
        println(list)  // [1, 2, 3, 4]

        // A read-only property can provide the mutability
        // with get()
        var name: String = "David"
        var surname: String = "Sun"
        val fullName: String
            get() = "$name $surname"
        println(fullName)  // David Sun
        name = "John"
        println(fullName)  // John Sun

        // Custom getter can provide the mutability
        // with get()
        fun calculate(): Int {
            println("Calculating...")
            return 42å
        }
        val fizz = calculate()
        val buzz
            get() = calculate()
        println(fizz)  // 3
        println(fizz)  // 3
        println(buzz)  // Calculating... 42
        println(buzz)  // Calculating... 42

        // Can override val with var
        interface Element {
            val active: Boolean
        }
        class ActualElement: Element {
            override var active : Boolean = false
        }

        // fullName was not smart casted.
        // fullName2 was smart casted.
        val name: String? = "David"
        val surname: String = "Sun"
        val fullName: String?
            get() = name?.let { "$it $surname" }
        val fullName2: String? = name?.let { "$it $surname" }
        if (fullName != null) {
            println(fullName.length)  // ERROR
        }
        if (fullName2 != null) {
            println(fullName2.length)  // David Sun
        }
    }
    //////////////////////////////////////////////////
    // ..Separation between mutable and read-only collections

    //////////////////////////////////////////////////
    // ..copy in data classes

    //////////////////////////////////////////////////
    // Different kinds of mutation points
    run {
        // Mutable collection
        val list1: MutableList<Int> = mutableListOf()
        // Mutable property
        var list2: List<Int> = listOf()
        // Can mutate in different ways
        list1.add(1)
        list2 = list2 + 1
        // Can translate in different ways
        list1 += 1  // list1.plusAssign(1)
        list2 += 1  // list2 = list2.plus(1)

        // Mutable property has concurrency problems
        var list = listOf<Int>()
        for (i in 1..1000) {
            thread {
                list = list + i
            }
        }
        Thread.sleep(1000)
        // Very unlikely to be 1000
        print(list.size)
        // Wrong: Every time a different number, for instance 911.

        // Mutable collection is slightly faster than
        // mutable property. But Mutable property provides
        // observers.
        var names by Delegates.observable(listOf<String>()) { _, old, new ->
            println("Names changed from $old to $new")
        }
        names += "foo"
        // Output: Names changed from [] to [foo]
        names += "bar"
        // Output: Names changed from [foo] to [foo, bar]
        // Mutable property of mutable collections also
        // provides observers with setter
        class Announcement {
            var announcements = listOf<Announcement>()
                private set
        }

        // Using mutable property of mutable collection is
        // the worst thing. There are 2 mutating points
        // Do not use this.
        var list3 = mutableListOf<Int>()
    }

    //////////////////////////////////////////////////
    // Do not leak mutation points
    run {
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
}
