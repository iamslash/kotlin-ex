package com.iamslash.exlogging.logging.internal

import java.lang.reflect.Modifier

internal object KLoggerNameResolver {

    internal fun name(block: () -> Unit): String {
        val name = block.javaClass.name

        return when {
            name.contains("Kt$") -> name.substringBefore("Kt$")
            name.contains("$")   -> name.substringBefore("$")
            else                 -> name
        }
    }

    internal fun <T: Any> name(clazz: Class<T>): String = unwrapCompanionClass(clazz).name

    private fun <T: Any> unwrapCompanionClass(clazz: Class<T>): Class<*> {
        if (clazz.enclosingClass != null) {
            runCatching {
                val field = clazz.enclosingClass.getField(clazz.simpleName)
                if (Modifier.isStatic(field.modifiers) && field.type == clazz) {
                    return clazz.enclosingClass
                }
            }
        }
        return clazz
    }
}
