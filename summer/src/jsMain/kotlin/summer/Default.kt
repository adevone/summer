package summer

import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext
import kotlin.reflect.KClass

actual val defaultUiContext: CoroutineContext = Dispatchers.Unconfined
actual val defaultWorkContext: CoroutineContext = Dispatchers.Unconfined

actual object DefaultLoggerFactory : SummerLogger.Factory {

    override fun get(forClass: KClass<*>): SummerLogger {
        val tag = forClass.simpleName
        return object : SummerLogger {

            override fun info(formatMessage: () -> String) {
                println("$tag: ${formatMessage()}")
            }

            override fun error(e: Throwable) {
                println(e)
            }
        }
    }
}