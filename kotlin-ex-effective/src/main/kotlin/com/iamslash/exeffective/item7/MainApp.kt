package com.iamslash.exeffective.item7

// Item 7: Prefer null or Failure result when the lack of result
//         is possible
//
// * we should prefer returning null or Failure when an error is expected,
//   and throwing an exception when an error is not expected

// ToBe:
// Prefer returning null or Failure
inline fun <reified T> String.readObjectOrNull(): T? {
    if (incorrectSign) {
        return null
    }
    return result
}
inline fun <reified T> String.readObject(): Result<T> {
    if (incorrectSign) {
        return Failure(JsonParsingException())
    }
    return Success(result)
}
sealed class Result<out T>
class Success<out T>(val result: T): Result<T>()
class Failure(val throwable: Throwable): Result<Nothing>()
class JsonParsingException: Exception()

fun main() {
    // ToBe:
    // When we return null or Failure object,
    // We can use safe-call or Elvis operator
    run {
        val age = userText.readObjectOrNull < Person()?.age ?: -1
    }
    // ToBe:
    // When we return a union type like Result,
    // we can handle it using the when-expression.
    run {
        val personResult = userText.readObject<Person>()
        val age = when(personResult) {
            is Success -> personResult.value.age
            is Failure -> -1
        }
    }
}


