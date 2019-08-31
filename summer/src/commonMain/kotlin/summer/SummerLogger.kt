package summer

import kotlin.reflect.KClass

interface SummerLogger {
    fun info(formatMessage: () -> String)
    fun error(e: Throwable)

    interface Factory {
        fun get(forClass: KClass<*>): SummerLogger
    }
}