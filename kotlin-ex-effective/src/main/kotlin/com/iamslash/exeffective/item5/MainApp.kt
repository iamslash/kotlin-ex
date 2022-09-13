package com.iamslash.exeffective.item5

// Item 5: Specify your expectations on arguments and state
//
// * Make them more visible.
// * Protect your application stability.
// * Protect your code correctness.
// * Smart cast variables.
// * require block - a universal way to specify expectations on arguments.
// * check block - a universal way to specify expectations on the state.
// * assert block - a universal way to test in testing mode if something is true.
// * Elvis operator with return or throw.
fun main() {

//    // example for require, check, assert
//    run {
//        fun pop(num: Int = 1): List<T> {
//            require(num <= 10) {
//                "Cannot remove more elements than current size"
//            }
//            check(isOpen) {
//                "Cannot pop from closed stack"
//            }
//            val ret = collection.take(num)
//            collection = collection.drop(num)
//            assert(ret.size == num)
//            return ret
//        }
//    }

    //////////////////////////////////////////////////
    // Arguments
    // ToBe
    run {
        fun factorial(n: Int): Long {
            // default exception message
            require(n >= 0)
            return if (n <= 1) 1 else factorial(n - 1) * n
        }
        data class Point(val x: Int, val y: Int)
        data class Cluster(val name: String)
        fun findCluters(points: List<Point>): List<Cluster> {
            require(points.isNotEmpty())
            return emptyList()
        }
        data class User(val email: String)
        fun isValidEmail(email: String): Boolean {
            return true
        }
        fun sendEmail(user: User, msg: String) {
            requireNotNull(user.email)
            require(isValidEmail(user.email))
        }
    }
    // ToBe
    run {
        fun factorial(n: Int): Long {
            // customized exception message
            require(n >= 0) {
                "Cannot calculate factorial of $n " +
                "because it is smaller than 0"
            }
            return if (n <= 1) 1 else factorial(n - 1) * n
        }
    }

    //////////////////////////////////////////////////
    // State

    // ToBe
    run {
        fun speak(text: String) {
            check(isInitialized)
        }
        fun getUserInfo(): UserInfo {
            checkNotNull(token)
        }
        fun next(): T {
            check(isOpen)
        }
    }

    //////////////////////////////////////////////////
    // Assertions

    // ToBe
    run {
        class StackTest {
            @Test
            fun `Stack pops correct number of elements`() {
                val stack = Stack(20) { it }
                val ret = stack.pop(10)
                assertEquals(10, ret.size)
            }

            // You should enable -ea JVM option
            // to enable assert()
            fun pop(num: Int = 1): List<T> {
                assert(ret.size == num)
                return ret
            }
        }
    }

    //////////////////////////////////////////////////
    // Nullability and smart casting

    // ToBe
    // What if require or check condition is true,
    // require or check condition is true afterward.
    run {
        public inline fun require(val: Boolean): Unit {
            contract {
                returns() implies val
            }
            require(val) { "Failed requirement." }
        }
        fun changeDress(person: Person) {
            // If person.outfit is Dress is true
            // person.outfit is smart casted to Dress type
            require(person.outfit is Dress)
            val dress: Dress = person.outfit
        }
    }
    run {
        class Person(val email: String?)
        fun sendEmail(person: Person, message: String) {
            // If person.email is not null,
            // person.email is not null afterward
            require(person.email != null)
            val email: String = person.email
        }
    }
    // We can use requireNotNull, checkNotNull instead of
    // require, check
    run {
        class Person(val email: String?)
        fun validateEmail(email: String) { }
//        // AsIs:
//        fun sendEmail(person: Person, text: String) {
//            val email = requireNotNull(person.email)
//            validateEmail(email)
//        }
        // ToBe:
        fun sendEmail(person: Person, text: String) {
            requireNotNull(person.email)
            validateEmail(person.email)
        }
    }

    // ToBe:
    // Elvis operator makes code simple
    run {
        fun sendEmail(person: Person, text: String) {
            val email: String = person.email ?: return
        }
    }
    // ToBe:
    // We can add scope function such as run() after Elvis operator
    run {
        fun sendEmail(person: Person, text: String) {
            val email: String = person.email ?: run {
                log("Email not sent, no email address")
                return
            }
        }
    }

}
