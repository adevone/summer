package summer

import kotlin.reflect.KClass

val loggersFactory = object : SummerLogger.Factory {
    override fun get(forClass: KClass<*>): SummerLogger {
        return object : SummerLogger {

            override fun info(formatMessage: () -> String) {
                println(formatMessage())
            }

            override fun error(e: Throwable) {
                println(e)
            }
        }
    }
}