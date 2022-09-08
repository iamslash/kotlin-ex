package com.iamslash.exeffective.item2

import java.awt.Color

// Item 2: Minimize the scope of variables
//
// * define variables for the closest possible scope.
// * prefer val over var also for local variables.
fun main() {
    // We can see a but not b, c
    run {
        val a = 1
        fun fizz() {
            val b = 2
            println(a + b)
        }

        val buzz = {
            val c = 3
            println(a + c)
        }
    }

    // limiting variable scope
    run {
        data class User(val name: String)

        val users: Array<User> = arrayOf()
        // AsIs:
        var user: User
        run {
            for (i in users.indices) {
                user = users[i]
                print("User at ${i} is ${user}")
            }
        }
        // ToBe:
        run {
            for (i in users.indices) {
                val user = users[i]
                print("User at ${i} is ${user}")
            }
        }
        // Same variables scope, nicer syntax
        run {
            for ((i, user) in users.withIndex()) {
                print("User at ${i} is ${user}")
            }
        }
    }

    // Whether a variable is read-only or read-write, we always prefer a
    // variable to be initialized when it is defined.
    run {
        data class User(val name: String)

        fun getValue(): User {
            return User("David")
        }

        val hasValue = true
        // AsIs
        run {
            val user: User
            if (hasValue) {
                user = getValue()
            } else {
                user = User("Tom")
            }
        }
        // ToBe
        run {
            val user: User = if (hasValue) {
                getValue()
            } else {
                User("tom")
            }
        }
    }

    // If we need to set-up multiple properties, destructuring
    // declarations can help us
    run {
        // AsIs:
        run {
            fun updateWeather(degrees: Int) {
                val description: String
                val color: Color
                if (degrees < 5) {
                    description = "cold"
                    color = Color.BLUE
                } else if (degrees < 23) {
                    description = "mild"
                    color = Color.CYAN
                } else {
                    description = "hot"
                    color = Color.RED
                }
            }
        }
        // ToBe:
        run {
            fun updateWeather(degrees: Int) {
                val (description, color) = when {
                    degrees < 5  -> "cold" to Color.BLUE
                    degrees < 23 -> "mild" to Color.YELLOW
                    else         -> "hot" to Color.RED
                }
            }
        }
    }

    //////////////////////////////////////////////////
    // Capturing

    // Eratosthenes
    run {
        var numbers = (2..100).toList()
        val primes = mutableListOf<Int>()
        while (numbers.isNotEmpty()) {
            val prime = numbers.first()
            primes.add(prime)
            numbers = numbers.filter { it % prime != 0 }
        }
        println(primes)
        // Output:
        // [2,3,5,7,11,13,17,19,23,29,31,
        // 37,41,43,47,53,59,61,67,71,73,79,83,89,97]
    }
    println("==================================================")
    // infinite sequence of prime numbers
    run {
        val primes: Sequence<Int> = sequence {
            var numbers = generateSequence(2) { it + 1 }
            while (true) {
                val prime = numbers.first()
                yield(prime)
                numbers = numbers.drop(1)
                    .filter { println("it: ${it}, prime: ${prime}"); it % prime != 0}
            }
            println(numbers)
        }
        println(primes.take(10).toList())
        // [2,3,5,7,11,13,17,19,23,29]
    }
    println("==================================================")
    // Optimized version. prime as a mutable variable.
    // But it does not work. Why???
    run {
        val primes: Sequence<Int> = sequence {
            var numbers = generateSequence(2) { it + 1 }
            var prime: Int
            while (true) {
                prime = numbers.first()
                yield(prime)
                numbers = numbers.drop(1)
                    .filter { println("it: ${it}, prime: ${prime}"); it % prime != 0 }
            }
            println(numbers)
        }
        println(primes.take(10).toList())
        // [2,3,5,6,7,8,9,10,11,12]
    }
}
