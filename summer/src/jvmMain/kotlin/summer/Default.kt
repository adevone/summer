package summer

import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext
import kotlin.reflect.KClass

actual val defaultUiContext: CoroutineContext = Dispatchers.Main
actual val defaultWorkContext: CoroutineContext = Dispatchers.Default

actual object DefaultLoggerFactory : SummerLogger.Factory {

    override fun get(forClass: KClass<*>): SummerLogger {
        val tag = forClass.java.simpleName
        return object : SummerLogger {

            override fun info(formatMessage: () -> String) {
                println("$tag: ${formatMessage()}")
            }

            override fun error(e: Throwable) {
                e.printStackTrace()
            }
        }
    }
}