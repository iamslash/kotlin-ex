package com.iamslash.exeffective.item3

// Item 3: Eliminate platform types as soon as possible
//
// * Platform type - a type that comes from another language and
//   has unknown nullability. We represent it with suffix "!".
//   It means null or not null.
// * we should assign it to a Nullable or Not Nullable variable
//   to prohibit error propagations.
fun main() {
    // Java method returns platform types which has unknown
    // nullability.
    // Java:
    // giveName() returns Nullable object
    //    public class JavaTest {
    //        public String giveName() {
    //        }
    //    }
    // getUsers() returns Nullable object
    //     public class UserRepo {
    //         public List<User> getUser() {
    //         }
    //     }
    // Kotlin:
    // We need to use !! for safety. It looks complicated.
    // val users: List<User> = UserRepo().users!!.filterNotNull()
    // val users: List<List<User>> = UserRepo().groupedUsers!!
    //     .map { it!!.filterNotNull() }

    // We can assign platform type value implicitly or explicitly.
    // Java:
    //    public class UserRepo {
    //        public User getUser() {
    //        }
    //    }
    // Kotlin:
    //    val repo = UserRepo()
    //    val user1 = repo.user           // Type of user1 is User! - Platform Type
    //    val user2: User = repo.user     // Type of user2 is User  - Not Nullable
    //    val user3: User? = repo.user    // Type of user3 is User? - Nullable

    // Please use @NotNull @Nullable explicitly for Kotlin developers
    // Java:
    //    import org.jetbrains.annotations.NotNull;
    //    public class UserRepo {
    //        public @NotNull User getUser() {
    //        }
    //    }

    // Platform Type is more dangerous because it propagates errors.
    // Java:
    //    public class JavaClass {
    //        public String getValue() {
    //            return null;
    //        }
    //    }
    // Kotlin:
    // AsIs:
    //    fun platformType() {
    //        val value = JavaClass().value  // Type of value is JavaClass!
    //        println(value.length)  // ERROR: NPE
    //    }
    // ToBe:
    //    fun statedType() {
    //        // Type of value is String (Not Nullable)
    //        val value: String = JavaClass().value // ERROR: NPE
    //        println(value.length)
    //    }
}
