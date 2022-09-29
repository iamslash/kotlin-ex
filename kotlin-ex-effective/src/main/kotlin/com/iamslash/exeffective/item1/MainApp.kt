package com.iamslash.exeffective.item1

import java.util.SortedSet
import java.util.TreeSet
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
interface Element {
    val active: Boolean
}
fun main() {
    run {
        // There 2 kinds of something mutable.
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
        run {
            class Person {
                var name: String = "David"
                var surname: String = "Sun"
                val fullName: String
                    get() = "$name $surname"
            }
            val person = Person()
            println(person.fullName)  // David Sun
            person.name = "John"
            println(person.fullName)  // John Sun
        }

        // Custom getter can provide the mutability
        // with get()
        run {
            class Foo {
                val fizz = calculate()
                val buzz
                    get() = calculate()
                fun calculate(): Int {
                    println("Calculating...")
                    return 42
                }
            }
            val foo = Foo()
            println(foo.fizz)  // 3
            println(foo.fizz)  // 3
            println(foo.buzz)  // Calculating... 42
            println(foo.buzz)  // Calculating... 42
        }

        // Can override val with var
        run {
            class ActualElement: Element {
                override var active: Boolean = false
            }
        }

        // fullName was not smart casted.
        // fullName2 was smart casted.
        run {
            class Person {
                val name: String? = "David"
                val surname: String = "Sun"
                val fullName: String?
                    get() = name?.let { "$it $surname" }
                val fullName2: String? = name?.let { "$it $surname" }
            }
            val person = Person()
            if (person.fullName != null) {
                println(person.fullName!!.length)  // ERROR
            }
            if (person.fullName2 != null) {
                println(person.fullName2.length)  // David Sun
            }
        }
    }

    //////////////////////////////////////////////////
    // ..Separation between mutable and read-only collections
    run {
        run {
            // This is Iterable.map implementation in stdlib
            // Return immutable collection (List<R>).
            // But is using mutable Collection (ArrayList<R>).
            // We don't need to use immutable collection inside
            // to return immutable collection. It's ok
            // to use platform specific collection.
            fun <T, R> Iterable<T>.map(
                transformation: (T) -> R
            ): List<R> {
                val list = ArrayList<R>()
                for (elem in this) {
                    list.add(transformation(elem))
                }
                return list
            }
        }
        run {
            // We can creates a copy of mutable collection
            val list = listOf(1, 2, 3)
            val mutableList = list.toMutableList()
            mutableList.add(4)
        }

    }

    //////////////////////////////////////////////////
    // ..copy in data classes
    run {
        // Immutable objects has these advantages.
        //
        // * Easy to understand because the state is not changed.
        // * Easy to parallelize those.
        // * Easy to cache.
        // * Do not make defensive copies on immutable objects???
        // * Perfect material to construct other objects???
        // * Easy to add or use them as keys in maps.
        data class FullName(var sname: String, var fname: String)
        val names: SortedSet<FullName> = TreeSet()
        val person = FullName("AAA", "AAA")
        names.add(person)
        names.add(FullName("David", "Sun"))
        names.add(FullName("John", "Doe"))
        println(names)  // [AAA AAA, David Sun, John Doe]
        println(person in names)  // true
        person.fname = "ZZZ"
        println(names)  // [ZZZ AAA, David Sun, John Doe]
        print(person in names)  // false

        // When we use immutable objects It's good to make
        // a function to return a immutable copy such as
        // withSurname().
        // But if there are many properties that method makes
        // boiler plates.
        // AsIs:
        run {
            class User(
                val name: String,
                val surname: String
            ) {
                fun withSurname(surname: String) = User(name, surname)
            }

            var user = User("David", "Sun")
            user = user.withSurname("Tim")
            println(user)  // User(name=David, surname=Tim)
        }
        // Use copy () function .
        // ToBe:
        run {
            data class User(
                val name: String,
                val surname: String
            )
            var user = User("David", "Sun")
            user = user.copy(surname = "Tim")
            println(user)  // User(name=David, surname=Tim)
        }
    }

    //////////////////////////////////////////////////
    // Different kinds of mutation points
    //
    // Do not use mutable property of mutable collection.
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
